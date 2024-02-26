package com.example.mvvm_kotlin.features.auth.data.repository
import com.example.mvvm_kotlin.data.remote.ApiService
import com.example.mvvm_kotlin.features.auth.model.AuthModel
import com.example.mvvm_kotlin.features.auth.model.ErrorResponseModel
import com.example.mvvm_kotlin.util.constants.Constants
import com.google.gson.Gson

import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: ApiService){


    suspend fun login(email: String, password: String): RepositoryResource<AuthModel?> {
        return try {
            val response = apiService.login(email, password)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.status) {
                    RepositoryResource.Success(body.data)
                } else {
                    RepositoryResource.Error("Data tidak ditemukan")
                }
            } else {
                val gson = Gson()
                val errorResponseModel: ErrorResponseModel? =
                    gson.fromJson(response.errorBody()?.charStream(), ErrorResponseModel::class.java)
                val errorMessage = errorResponseModel?.message ?: "Akses tidak diotorisasi"
                RepositoryResource.Error(errorMessage)
            }
        } catch (e: Exception) {
            RepositoryResource.Error("Kesalahan jaringan: ${e.message}")
        }
    }

    suspend fun register(map: Map<String, Any>): RepositoryResource<AuthModel?> {
        return try {
            val response = apiService.register(map)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.status) {
                    RepositoryResource.Success(body.data)
                }else {
                    RepositoryResource.Error(Constants.ERROR_MESSAGE)

                }

            }else {
                val gson = Gson()
                val errorResponseModel: ErrorResponseModel? =
                    gson.fromJson(response.errorBody()?.charStream(), ErrorResponseModel::class.java)
                val errorMessage = errorResponseModel?.message ?: "Akses tidak diotorisasi"
                RepositoryResource.Error(errorMessage)
            }

        }catch (e: Exception) {
            RepositoryResource.Error("Network error: ${e.message}")
        }
    }

    sealed class RepositoryResource<out T> {
        data class Success<out T>(val data: T?) : RepositoryResource<T>()
        data class Error(val message: String) : RepositoryResource<Nothing>()
        object Loading : RepositoryResource<Nothing>()
    }

}