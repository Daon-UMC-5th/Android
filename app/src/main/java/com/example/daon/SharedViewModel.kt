package com.example.mypage2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _userInput = MutableLiveData<String>()
    val userInput: LiveData<String> get() = _userInput

    fun setUserInput(text: String) {
        _userInput.value = text
    }
    fun getUserInput(): String? {
        return _userInput.value
    }
}
