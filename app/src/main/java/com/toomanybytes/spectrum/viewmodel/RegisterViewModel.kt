package com.toomanybytes.spectrum.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    private val _isButtonEnabled = MutableLiveData<Boolean>()

    val registerText = MutableLiveData<String>()
    val isButtonEnabled: LiveData<Boolean> get() = _isButtonEnabled
    val keyWord = ArrayList<String>()
    val radioText = MutableLiveData<String>()


    init {
        // 초기 상태 설정
        _isButtonEnabled.value = false
    }

    fun onTextChanged(text : String) {
        // 텍스트가 입력되면 버튼 활성화
        _isButtonEnabled.value = text.isNotEmpty()
        registerText.value = text
    }

    fun onEnabledChanged(text : String) {
        // intent 뒤로가기 확인시 버튼 활성화
        _isButtonEnabled.value = true
        registerText.value = text
    }

    fun interestList(text : String): Boolean {
        Log.d("keyWord.isEmpty()","${keyWord.isEmpty()}")
        if (keyWord.contains(text)) {
            keyWord.remove(text)
            _isButtonEnabled.value = true
        } else {
            if(keyWord.size < 5){
                keyWord.add(text)
                _isButtonEnabled.value = true
            }else{
                _isButtonEnabled.value = true
                return false
            }
        }

        if(keyWord.isEmpty()){
            _isButtonEnabled.value = false
            return true
        }else{
            _isButtonEnabled.value = true
            return true
        }
    }

    fun onButtonChanged(text : String) {
        // 라디오 버튼 선택하면 버튼 활성화
        if(radioText.value == text){
            _isButtonEnabled.value = false
            radioText.value = ""
        }else{
            _isButtonEnabled.value = true
            radioText.value = text
        }
    }

}