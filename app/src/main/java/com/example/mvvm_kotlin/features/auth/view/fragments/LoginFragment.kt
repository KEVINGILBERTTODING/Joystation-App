package com.example.mvvm_kotlin.features.auth.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_kotlin.R
import com.example.mvvm_kotlin.databinding.FragmentLoginBinding
import com.example.mvvm_kotlin.features.auth.data.repository.AuthRepository
import com.example.mvvm_kotlin.features.auth.model.AuthModel
import com.example.mvvm_kotlin.features.auth.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        setUpObservers()


        listener()
        // Inflate the layout for this fragment
        return binding.root
    }



    private fun listener() {
        binding.btnLogin.setOnClickListener {
            inputValidation()
        }
    }

    private fun setUpObservers() {

        authViewModel.loginResult.observe(viewLifecycleOwner, {result ->
            handleLoginResult(result)
        })


    }

    private fun handleLoginResult(result: AuthRepository.RepositoryResource<AuthModel?>) {
        if (result is AuthRepository.RepositoryResource.Success) {
            val authModel = result.data
            val erromessage = authModel?.email ?: "Jhon Doe"
            showToast(erromessage)
        }else if (result is AuthRepository.RepositoryResource.Error) {
            val errorMessage = result.message
            showToast(errorMessage)
        }else if (result is AuthRepository.RepositoryResource.Loading) {
            showToast("Loading")
        }
    }



    private fun inputValidation() {
        binding.tilEmail.isErrorEnabled = false
        binding.tilPassword.isErrorEnabled = false

        if (binding.etEmail.text.toString().isEmpty()) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = "Email tidak boleh kosong"
            return
        }

        if (!isValidEmail(binding.etEmail.text.toString())) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = "Email tidak valid"
            return
        }

        if (binding.etPassword.text.toString().isEmpty()) {
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = "Password tidak boleh kosong"
            return
        }

        if (!isValidPassword(binding.etPassword.text.toString())) {
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = "Password tidak boleh kurang dari 8 karakter"
            return
        }

        login(binding.etEmail.text.toString(), binding.etPassword.text.toString())


    }

    private fun login(email:String, password: String) {
        authViewModel.login(email, password)


    }

    private fun isValidEmail(email: String) : Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)
        return matcher.matches()

    }

    private fun isValidPassword(password: String) : Boolean{
        return password.length >= 8
    }

    private fun showToast(message : String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }



}