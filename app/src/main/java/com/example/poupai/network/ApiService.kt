package com.example.poupai.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// --- Classes de Dados (Modelos) ---

data class RegisterRequest(val name: String, val email: String, val pass: String)
data class LoginRequest(val email: String, val pass: String)
data class LoginResponse(val message: String, val id: Long)

// Resposta do Dashboard (Gráfico e Nome)
data class DashboardData(
    val userName: String,
    val chartData: Map<String, Double>,
    val totalBalance: Double
)

// Pedido para adicionar despesa (Opção futura)
data class ExpenseRequest(val userId: Long, val category: String, val amount: Double)

// --- Interface da API ---

interface ApiService {
    @POST("api/auth/register")
    fun register(@Body request: RegisterRequest): Call<Any>

    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    // Novo: Pegar dados do Dashboard
    @GET("api/dashboard/{id}")
    fun getDashboard(@Path("id") userId: Long): Call<DashboardData>

    // Novo: Adicionar despesa
    @POST("api/dashboard/add")
    fun addExpense(@Body expense: ExpenseRequest): Call<Any>
}