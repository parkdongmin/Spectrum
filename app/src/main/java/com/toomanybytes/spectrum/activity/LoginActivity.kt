package com.toomanybytes.spectrum.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.googleBtn.setOnClickListener {
            val intent= Intent( this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}