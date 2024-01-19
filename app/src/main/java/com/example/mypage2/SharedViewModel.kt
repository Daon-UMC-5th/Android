package com.example.mypage2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val userInput = MutableLiveData<String>()

    fun setUserInput(input: String) {
        userInput.value = input
    }

    fun getUserInput(): LiveData<String> {
        return userInput
    }
}
