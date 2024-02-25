package com.example.mvvm_kotlin.data.remote

import com.example.mvvm_kotlin.features.auth.model.AuthModel
import com.example.mvvm_kotlin.features.auth.model.ResponseModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    companion object{
        const val IP_ADDRESS = "192.168.100.54:8000"
        const val END_POINT = "http://$IP_ADDRESS/api/"
    }

    @POST("auth/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<ResponseModel<AuthModel>>


}