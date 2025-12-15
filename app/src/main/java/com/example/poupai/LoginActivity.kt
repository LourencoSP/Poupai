package com.example.poupai

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.poupai.network.LoginRequest
import com.example.poupai.network.LoginResponse
import com.example.poupai.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        val editEmail = findViewById<EditText>(R.id.edit_email)
        val editSenha = findViewById<EditText>(R.id.edit_senha)
        val btnEntrar = findViewById<Button>(R.id.btn_entrar)

        btnEntrar.setOnClickListener {
            val email = editEmail.text.toString()
            val senha = editSenha.text.toString()

            val request = LoginRequest(email, senha)

            RetrofitClient.instance.login(request).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val userId = response.body()?.id

                        Toast.makeText(this@LoginActivity, "Bem-vindo! ID: $userId", Toast.LENGTH_LONG).show()

                        // AQUI VOCÊ NAVEGA PARA A TELA PRINCIPAL (Home)
                        // startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    } else {
                        Toast.makeText(this@LoginActivity, "Email ou senha incorretos", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Erro de conexão. O servidor está rodando?", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}