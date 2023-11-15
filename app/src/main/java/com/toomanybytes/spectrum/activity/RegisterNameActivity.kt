package com.toomanybytes.spectrum.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.databinding.ActivityRegisterNameBinding
import com.toomanybytes.spectrum.viewmodel.RegisterViewModel

class RegisterNameActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val registerIntent = intent
        var name = registerIntent.getStringExtra("name")

        // 뷰 모델 초기화
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        // 뷰 바인딩 설정
        val binding: ActivityRegisterNameBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_name)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // 뒤로가기 해서 왔을때 이름 자동 입력
        if(name != null){
            binding.nicknameEdit.setText("$name")
            viewModel.onEnabledChanged("$name")
        }

        // EditText의 텍스트 변경 감지
        binding.nicknameEdit.addTextChangedListener {
            viewModel.onTextChanged(it.toString())
        }

        // 버튼 상태 감시
        viewModel.isButtonEnabled.observe(this, Observer { isEnabled ->
            binding.registerNameBottomNext.isEnabled = isEnabled
        })

        binding.registerNameTopNext.setOnClickListener {
            if(viewModel.isButtonEnabled.hasObservers()){
                val intent= Intent( this,RegisterHandleActivity::class.java)
                intent.putExtra("name","${viewModel.registerText.value}")
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
            }
        }

        binding.registerNameBottomNext.setOnClickListener {
            if(viewModel.isButtonEnabled.hasObservers()){
                val intent= Intent( this,RegisterHandleActivity::class.java)
                intent.putExtra("name","${viewModel.registerText.value}")
                startActivity(intent)
                finish()
                // 오른쪽에서 왼쪽으로 activity 화면이 이동하게 애니메이션 지정
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
            }
        }

        binding.registerNameBack.setOnClickListener {
            val intent= Intent( this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }





    }
}