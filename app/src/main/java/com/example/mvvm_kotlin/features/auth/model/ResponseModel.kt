package com.example.mvvm_kotlin.features.auth.model

class ResponseModel <T>(
    var status : Boolean,
    var message: String,
    var data: T?
)