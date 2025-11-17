package com.example.poupai

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_app)

        // Navega para a tela LoginOrNewActivity após 3 segundos
        Handler(Looper.getMainLooper()).postDelayed({

            // Cria a "intenção" de ir para a próxima tela
            val intent = Intent(this, LoginOrNewActivity::class.java)
            startActivity(intent)

            // Fecha a tela de loading para que o usuário não possa voltar
            finish()

        }, 5000) // 3000 milissegundos = 3 segundos
    }
}