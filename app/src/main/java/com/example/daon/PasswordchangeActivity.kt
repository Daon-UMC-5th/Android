package com.example.daon

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.daon.databinding.ActivityPasswordchangeBinding

class PasswordchangeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPasswordchangeBinding

    val colorConditionMet = Color.parseColor("#4CAF50")
    val colorConditionNotMet = Color.parseColor("#CCCCCC")
    val imageConditionNotMet = R.drawable.check_off
    val imageConditionMet = R.drawable.check_on

    private var passwordInput1: String = ""
    private var passwordInput2: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordchangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.passwordBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        passwordInput1 = binding.passwordAgain.text.toString()
        passwordInput2 = binding.editPassword.text.toString()

        binding.upper.setTextColor(colorConditionNotMet)
        binding.special.setTextColor(colorConditionNotMet)
        binding.length.setTextColor(colorConditionNotMet)
        binding.number.setTextColor(colorConditionNotMet)

        binding.upperIm.setImageResource(imageConditionNotMet)
        binding.specialIm.setImageResource(imageConditionNotMet)
        binding.lengthIm.setImageResource(imageConditionNotMet)
        binding.numberIm.setImageResource(imageConditionNotMet)

        binding.textSame.setTextColor(colorConditionNotMet)
        binding.textSameCheck.setImageResource(imageConditionNotMet)

        binding.editPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // 입력 전에 수행할 작업
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 변경될 때 수행할 작업
            }

            override fun afterTextChanged(editable: Editable?) {
                // 입력 후에 수행할 작업
                val userInput = editable.toString()
                passwordInput2 = editable.toString()

                // 대문자 입력 여부 확인
                val hasUpperCase = userInput.any { it.isUpperCase() }

                // 특수문자 입력 여부 확인
                val hasSpecialCharacter = userInput.any { !it.isLetterOrDigit() }

                // 길이 확인 (8에서 20글자 사이)
                val isLengthValid = userInput.length in 8..20

                // 숫자 입력 여부 확인
                val hasNumber = userInput.any { it.isDigit() }

                binding.upper.setTextColor(if (hasUpperCase) colorConditionMet else colorConditionNotMet)
                binding.special.setTextColor(if (hasSpecialCharacter) colorConditionMet else colorConditionNotMet)
                binding.length.setTextColor(if (isLengthValid) colorConditionMet else colorConditionNotMet)
                binding.number.setTextColor(if (hasNumber) colorConditionMet else colorConditionNotMet)

                binding.upperIm.setImageResource(if (hasUpperCase) imageConditionMet else imageConditionNotMet)
                binding.specialIm.setImageResource(if (hasSpecialCharacter) imageConditionMet else imageConditionNotMet)
                binding.lengthIm.setImageResource(if (isLengthValid) imageConditionMet else imageConditionNotMet)
                binding.numberIm.setImageResource(if (hasNumber) imageConditionMet else imageConditionNotMet)

            }
        })
        binding.passwordAgain.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // 입력 전에 수행할 작업
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 변경될 때 수행할 작업
            }

            override fun afterTextChanged(editable: Editable?) {
                // 입력 후에 수행할 작업
                passwordInput1 = editable.toString() // passwordInput2에 텍스트 저장

                // 비밀번호 일치 여부 확인
                checkPasswordMatch()
            }
        })
    }

    private fun checkPasswordMatch() {
        if (passwordInput1 == passwordInput2) {
            binding.textSame.setTextColor(colorConditionMet)
            binding.textSameCheck.setImageResource(imageConditionMet)
        } else {
            binding.textSame.setTextColor(colorConditionNotMet)
            binding.textSameCheck.setImageResource(imageConditionNotMet)
        }
    }
}