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
            _loginResult.value = AuthRepository.RepositoryResource.Error("Invalid email or password format")  // Perbarui di sini
        }
    }




    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return email.matches(emailRegex)
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }
}