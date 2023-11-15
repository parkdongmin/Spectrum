package com.toomanybytes.spectrum.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.databinding.ActivityRegisterHandleBinding
import com.toomanybytes.spectrum.databinding.ActivityRegisterJobBinding
import com.toomanybytes.spectrum.viewmodel.RegisterViewModel

class RegisterJobActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val registerIntent = intent
        var name = registerIntent.getStringExtra("name")
        var handle = registerIntent.getStringExtra("handle")
        var keyword = registerIntent.getStringExtra("keyword")

        // 뷰 모델 초기화
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        // 뷰 바인딩 설정
        val binding: ActivityRegisterJobBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_job)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // 버튼 상태 감시
        viewModel.isButtonEnabled.observe(this, Observer { isEnabled ->
            binding.registerJobBottomNext.isEnabled = isEnabled
        })

        binding.registerJobBottomNext.setOnClickListener {
            if(viewModel.isButtonEnabled.hasObservers()){
                val intent= Intent( this,RegisterInterestActivity::class.java)
                intent.putExtra("name","$name")
                intent.putExtra("handle","$handle")
                intent.putExtra("keyword","$keyword")
                intent.putExtra("job","${viewModel.radioText}")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
            }
        }

        binding.registerJobTopNext.setOnClickListener {
            if(viewModel.isButtonEnabled.hasObservers()){
                val intent= Intent( this,RegisterInterestActivity::class.java)
                intent.putExtra("name","$name")
                intent.putExtra("handle","$handle")
                intent.putExtra("keyword","$keyword")
                intent.putExtra("job","${viewModel.radioText}")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
            }
        }

        binding.registerJobBack.setOnClickListener {
            val intent= Intent( this,RegisterInterestActivity::class.java)
            intent.putExtra("name","$name")
            intent.putExtra("handle","$handle")
            intent.putExtra("keyword","$keyword")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
        }

        binding.jobBtnYes.setOnClickListener {
            viewModel.onButtonChanged("true")
            binding.jobBtnNo.isChecked = false
        }

        binding.jobBtnNo.setOnClickListener {
            viewModel.onButtonChanged("false")
            binding.jobBtnYes.isChecked = false
        }

    }
}