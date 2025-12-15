package com.example.poupai

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginOrNewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_or_new) //

        // 1. Encontrar os botões na tela usando os IDs do XML
        val btnCriarConta = findViewById<Button>(R.id.btn_criar_conta)
        val btnJaTenhoConta = findViewById<Button>(R.id.btn_ja_tenho_conta)

        // 2. Ação do botão "Criar uma conta" -> Vai para o Registro
        btnCriarConta.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // 3. Ação do botão "Já tenho uma conta" -> Vai para o Login
        btnJaTenhoConta.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}