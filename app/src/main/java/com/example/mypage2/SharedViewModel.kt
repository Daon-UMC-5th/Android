package com.example.mypage2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _userInput = MutableLiveData<String>()
    val userInput: LiveData<String> get() = _userInput

    fun setUserInput(input: String) {
        _userInput.value = input
        Log.d("ViewModels","${_userInput.value}")
    }
}
