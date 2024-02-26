package com.example.mvvm_kotlin.features.auth.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.mvvm_kotlin.R
import com.example.mvvm_kotlin.databinding.FragmentRegisterBinding
import com.example.mvvm_kotlin.features.auth.data.repository.AuthRepository
import com.example.mvvm_kotlin.features.auth.model.AuthModel
import com.example.mvvm_kotlin.features.auth.viewModel.AuthViewModel
import com.example.mvvm_kotlin.util.constants.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        listener()
        setUpObservers()

        return binding.root
    }

    private fun listener() {
        binding.btnRegister.setOnClickListener {
            inputValidate()
        }
    }

    private fun inputValidate() {
        var name = binding.etName.text.toString()
        var email = binding.etEmail.text.toString()
        var phoneNumber = binding.etPhone.text.toString()
        var password = binding.etPassword.text.toString()

        // default
        binding.tilEmail.isErrorEnabled = false
        binding.tilPassword.isErrorEnabled = false
        binding.tilName.isErrorEnabled = false
        binding.tilPhone.isErrorEnabled = false

        if (name.isEmpty()) {
            binding.tilName.isErrorEnabled = true
            binding.tilName.error = "Nama tidak boleh kosong"
            return
        }


        if (phoneNumber.isEmpty()) {
            binding.tilPhone.isErrorEnabled = true
            binding.tilPhone.error = "No hp tidak boleh kosong"
            return
        }

        if (email.isEmpty()) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = "Email tidak boleh kosong"
            return
        }

        if (password.isEmpty()) {
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = "Password tidak boleh kosong"
            return
        }




        if (!isValidPhoneNumber(phoneNumber)) {
            binding.tilPhone.isErrorEnabled = true
            binding.tilPhone.error = "Format no hp tidak sesuai"
            return
        }


        if (!isValidEmail(email)) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = "Format email tidak sesuai"
            return
        }


        if (!isValidPassword(password)) {
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = "Format password tidak sesuai"
            return
        }

        // save to map
        val map : Map<String, Any> = mutableMapOf(
            "name" to name,
            "phone_number" to phoneNumber,
            "email" to email,
            "password" to password
        )


        register(map)




    }

    private fun setUpObservers() {
        authViewModel.registerResult.observe(viewLifecycleOwner, { result ->
            handleRegister(result)
        })


    }

    private fun handleRegister(result: AuthRepository.RepositoryResource<AuthModel?>) {
        if (result != null) {
            if (result is AuthRepository.RepositoryResource.Success) {
                var data = result.data
                showToast(data?.email.toString())
            }else if (result is AuthRepository.RepositoryResource.Error) {
                var errorMessage = result?.message ?: Constants.ERROR_MESSAGE
                Log.d("KEVIN", "handleRegister: $errorMessage")
                showToast(errorMessage)
            }else if (result is AuthRepository.RepositoryResource.Loading) {
                showToast("Loading")
            }
        }else {
            showToast(Constants.ERROR_MESSAGE)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return email.matches(emailRegex)
    }


    private fun isValidPhoneNumber (phone: String) : Boolean {
        val regex = Regex("^[0-9]+$")
        return regex.matches(phone)
    }



    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    fun register(map : Map<String, Any>) {
        authViewModel.register(map)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}