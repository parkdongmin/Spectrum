package com.toomanybytes.spectrum.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.toomanybytes.spectrum.model.PhotoModel

class WriteViewModel : ViewModel() {

    val photoList = MutableLiveData<List<PhotoModel>>()
    val contentsCount = MutableLiveData<Int>()
    val titleCount = MutableLiveData<Int>()
    var selectedImageCount = MutableLiveData<Int>()
    val categoryKeyWord = ArrayList<String>()
    val postCheck = MutableLiveData<Boolean>()

    init {
        photoList.value = mutableListOf()
        contentsCount.value = 0
        titleCount.value = 0
        postCheck.value = false
        selectedImageCount.value = (photoList.value as MutableList<PhotoModel>).size
    }

    fun addPhoto(photoUrl: String) {
        val currentList = photoList.value?.toMutableList() ?: mutableListOf()
        // 이미지 개수가 8개 미만일 때만 추가
        if (currentList.size < 8) {
            currentList.add(PhotoModel(photoUrl))
            photoList.value = currentList
            checkPosting()
        } else {
            Log.d("PhotoViewModel", "Cannot add more than 8 photos")
        }
    }

    fun updateContentsCount(text: CharSequence?) {
        contentsCount.value = text?.length ?: 0
        checkPosting()
    }

    fun updateTitleCount(text: CharSequence?) {
        titleCount.value = text?.length ?: 0
        checkPosting()
    }

    fun updateSelectedImageCount(count: Int) {
        selectedImageCount.value = count
    }

    fun clearViewItems() {
        photoList.value = emptyList()  // 또는 photoList.value = mutableListOf()
        selectedImageCount.value = 0
        contentsCount.value = 0
        titleCount.value = 0
        categoryKeyWord.clear()
        postCheck.value = false
    }

    fun removePhoto(position: Int) {
        if (position in photoList.value?.indices ?: emptyList()) {
            val currentList = photoList.value?.toMutableList() ?: mutableListOf()
            currentList.removeAt(position)
            photoList.value = currentList
        }
    }

    fun addCategory(text : String) : Boolean{
        if (categoryKeyWord.contains(text)) {
            categoryKeyWord.remove(text)
        } else {
            if(categoryKeyWord.size < 1){
                categoryKeyWord.add(text)
            }else{
                return false
            }
        }
        return if(categoryKeyWord.isEmpty()){
            checkPosting()
            true
        }else{
            checkPosting()
            true
        }
    }

    // 데이터가 게시 가능한 상태인지 확인해주는 메소드
    private fun checkPosting(){
        postCheck.value = contentsCount.value != 0 && titleCount.value != 0 && categoryKeyWord.isNotEmpty()
    }



}