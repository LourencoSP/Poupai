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


        Handler(Looper.getMainLooper()).postDelayed({


            val intent = Intent(this, LoginOrNewActivity::class.java)
            startActivity(intent)


            finish()

        }, 5000)
    }
}