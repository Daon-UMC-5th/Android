package com.example.mypage2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel2 : ViewModel() {
    private val _userInput = MutableLiveData<String>()
    val userInput2: LiveData<String> get() = _userInput

    fun setUserInput2(text: String) {
        _userInput.value = text
    }
    fun getUserInput2(): String? {
        return _userInput.value
    }
}
