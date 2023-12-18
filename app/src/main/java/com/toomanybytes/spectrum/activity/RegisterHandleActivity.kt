package com.toomanybytes.spectrum.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.databinding.ActivityLoginBinding
import com.toomanybytes.spectrum.databinding.ActivityRegisterHandleBinding
import com.toomanybytes.spectrum.databinding.ActivityRegisterNameBinding
import com.toomanybytes.spectrum.viewmodel.RegisterViewModel

class RegisterHandleActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    lateinit var binding : ActivityRegisterHandleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterHandleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val registerIntent = intent
        var name = registerIntent.getStringExtra("name")
        var handle = registerIntent.getStringExtra("handle")

        // 뷰 모델 초기화
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        Log.d("name","$name")

        binding.handleTitle.text = "$name 님 만의 핸들네임을\n만들어주세요."

        // 뒤로가기 해서 왔을때 핸들네임 자동 입력
        if(handle != null){
            binding.handleEdit.setText("$handle")
            viewModel.onEnabledChanged("$handle")
        }

        // EditText의 텍스트 변경 감지
        binding.handleEdit.addTextChangedListener {
            viewModel.onTextChanged(it.toString())
        }

        // 버튼 상태 감시 및 백스페이스 버튼 전환
        viewModel.isButtonEnabled.observe(this, Observer { isEnabled ->
            binding.registerHandleBottomNext.isEnabled = isEnabled
            when(isEnabled){
                true -> binding.handleBackspace.visibility = View.VISIBLE
                false -> binding.handleBackspace.visibility = View.INVISIBLE
            }
        })

        // 백스페이스 클릭시 editText 초기화
        binding.handleBackspace.setOnClickListener {
            binding.handleEdit.text = null
        }

        binding.registerHandleTopNext.setOnClickListener {
            if(viewModel.isButtonEnabled.hasObservers()){
                val intent= Intent( this,RegisterInterestActivity::class.java)
                intent.putExtra("name","$name")
                intent.putExtra("handle","${viewModel.registerText.value}")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
            }
        }

        binding.registerHandleBottomNext.setOnClickListener {
            if(viewModel.isButtonEnabled.hasObservers()){
                val intent= Intent( this,RegisterInterestActivity::class.java)
                intent.putExtra("name","$name")
                intent.putExtra("handle","${viewModel.registerText.value}")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
            }
        }

        binding.registerHandleBack.setOnClickListener {
            val intent= Intent( this,RegisterNameActivity::class.java)
            intent.putExtra("name","$name")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
        }

    }
}