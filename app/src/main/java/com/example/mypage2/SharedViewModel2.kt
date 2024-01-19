package com.example.mypage2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel2 : ViewModel() {
    private val userInput = MutableLiveData<String>()

    fun setUserInput2(input: String) {
        userInput.value = input
    }

    fun getUserInput2(): LiveData<String> {
        return userInput
    }
}
