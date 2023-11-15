package com.toomanybytes.spectrum.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.databinding.ActivityLoadBinding

class LoadActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 일정 시간 지연 이후 실행하기 위한 코드
        Handler(Looper.getMainLooper()).postDelayed({
            // 일정 시간이 지나면 LoginView로 이동
            val intent= Intent( this,LoginActivity::class.java)
            startActivity(intent)
            finish()

        }, 1000) // 시간 2초 이후 실행

    }
}