package com.example.poupai

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.poupai.network.RegisterRequest
import com.example.poupai.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register) // Liga ao seu XML

        val editNome = findViewById<EditText>(R.id.edit_nome)
        val editEmail = findViewById<EditText>(R.id.edit_email)
        val editSenha = findViewById<EditText>(R.id.edit_nova_senha)
        val editConfirma = findViewById<EditText>(R.id.edit_confirmar_senha)
        val btnCriar = findViewById<Button>(R.id.btn_criar_conta)
        val btnAcessar = findViewById<TextView>(R.id.btn_acessar)

        // Botão de ir para o Login
        btnAcessar.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Botão de Cadastrar
        btnCriar.setOnClickListener {
            val nome = editNome.text.toString()
            val email = editEmail.text.toString()
            val senha = editSenha.text.toString()
            val confirma = editConfirma.text.toString()

            if (senha != confirma) {
                Toast.makeText(this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = RegisterRequest(nome, email, senha)

            RetrofitClient.instance.register(request).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegisterActivity, "Conta criada com sucesso!", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Erro: Email já existe", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Falha na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}