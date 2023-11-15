package com.toomanybytes.spectrum.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.databinding.ActivityRegisterHandleBinding
import com.toomanybytes.spectrum.databinding.ActivityRegisterInterestBinding
import com.toomanybytes.spectrum.viewmodel.RegisterViewModel

class RegisterInterestActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val registerIntent = intent
        var name = registerIntent.getStringExtra("name")
        var handle = registerIntent.getStringExtra("handle")

        // 뷰 모델 초기화
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        // 뷰 바인딩 설정
        val binding: ActivityRegisterInterestBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_interest)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        // 버튼 상태 감시
        viewModel.isButtonEnabled.observe(this, Observer { isEnabled ->
            binding.registerInterestBottomNext.isEnabled = isEnabled
        })

        binding.registerInterestBottomNext.setOnClickListener {
            if(viewModel.isButtonEnabled.hasObservers()){
                val intent= Intent( this,RegisterJobActivity::class.java)
                intent.putExtra("name","$name")
                intent.putExtra("handle","$handle")
                intent.putExtra("keyword","${viewModel.keyWord}")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
            }
        }

        binding.registerInterestTopNext.setOnClickListener {
            if(viewModel.isButtonEnabled.hasObservers()){
                val intent= Intent( this,RegisterJobActivity::class.java)
                intent.putExtra("name","$name")
                intent.putExtra("handle","$handle")
                intent.putExtra("keyword","${viewModel.keyWord}")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
            }
        }

        binding.registerInterestBack.setOnClickListener {
            val intent= Intent( this,RegisterHandleActivity::class.java)
            intent.putExtra("name","$name")
            intent.putExtra("handle","$handle")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
        }

    }

    fun onMyButtonClick(view: View) {
        if (view is ToggleButton) {
            val ToggleText = view.text.toString()
            var toggle = viewModel.interestList(ToggleText)
            if(!toggle){
                view.isChecked = false
                Toast.makeText(applicationContext,"최대 5개까지 고를 수 있어요",Toast.LENGTH_SHORT).show()
            }
        }
    }

}