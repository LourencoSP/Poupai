package com.example.poupai.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Classes de dados para enviar/receber JSON
data class RegisterRequest(val name: String, val email: String, val pass: String)
data class LoginRequest(val email: String, val pass: String)
data class LoginResponse(val message: String, val id: Long)

interface ApiService {
    @POST("api/auth/register")
    fun register(@Body request: RegisterRequest): Call<Any>

    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}