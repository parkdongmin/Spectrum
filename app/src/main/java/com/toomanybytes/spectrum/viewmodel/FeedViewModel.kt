package com.toomanybytes.spectrum.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FeedViewModel : ViewModel() {
    // 드롭다운 리스트의 아이템 목록
    val dropDownItem = MutableLiveData<List<String>>()

    init {
        // 아이템 목록 초기화
        dropDownItem.value = listOf("최신순", "인기순")
    }

    // 아이템이 선택되었을 때의 동작
    fun onItemSelected(selectedItem: String) {
        Log.d("Selected Item", selectedItem)
    }
}