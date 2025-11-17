package com.example.poupai

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LoginOrNewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Simplesmente carrega o layout que acabamos de corrigir
        setContentView(R.layout.login_or_new)

        // Aqui você adicionaria a lógica dos botões
        // ex: btn_criar_conta.setOnClickListener { ... }
        // ex: btn_ja_tenho_conta.setOnClickListener { ... }
    }
}