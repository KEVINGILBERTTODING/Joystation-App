package com.example.mvvm_kotlin.features.auth.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_kotlin.features.auth.data.repository.AuthRepository
import com.example.mvvm_kotlin.features.auth.model.AuthModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<AuthRepository.RepositoryResource<AuthModel?>>()
    val loginResult: LiveData<AuthRepository.RepositoryResource<AuthModel?>> get() = _loginResult

    fun login(email: String, password: String) {
        if (isValidEmail(email) && isValidPassword(password)) {
            viewModelScope.launch {
                _loginResult.value = AuthRepository.RepositoryResource.Loading  // Perbarui di sini
                _loginResult.value = authRepository.login(email, password)
            }
        } else {
            _loginResult.value = AuthRepository.RepositoryResource.Error("Email dan password tidak valid")  // Perbarui di sini
        }
    }



    private val _registerResult = MutableLiveData<AuthRepository.RepositoryResource<AuthModel?>> ()
    val registerResult : LiveData<AuthRepository.RepositoryResource<AuthModel?>> get() = _registerResult

    fun register(map: Map<String, Any>) {
        if (map != null ) {
            if (!isValidEmail(map.get("email").toString())) {
                _registerResult.value = AuthRepository.RepositoryResource.Error("Format email tidak valid")
            }



            if (!isValidPhoneNumber(map.get("phone_number").toString())) {
                _registerResult.value = AuthRepository.RepositoryResource.Error("Format no hp tidak valid")

            }

            if (!isValidPassword(map.get("password").toString())) {
                _registerResult.value = AuthRepository.RepositoryResource.Error("Format password tidak valid")
            }

            viewModelScope.launch {
                _registerResult.value = AuthRepository.RepositoryResource.Loading
                _registerResult.value = authRepository.register(map)
            }



        }else {
            _registerResult.value = AuthRepository.RepositoryResource.Error("Data tidak valid!")
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

}