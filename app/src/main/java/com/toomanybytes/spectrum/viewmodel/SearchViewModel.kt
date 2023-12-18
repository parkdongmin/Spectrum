package com.toomanybytes.spectrum.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    // 드롭다운 리스트의 아이템 목록
    val searchText = MutableLiveData<String>()
    val isVisibility = MutableLiveData<Boolean>()

    init {
        // 아이템 목록 초기화
        searchText.value = ""
    }

    fun onTextChanged(text: String) {
        // 텍스트가 입력되면 백스페이스 버튼 활성화(보이게)
        isVisibility.value = text.isNotEmpty()
        searchText.value = text
    }

}