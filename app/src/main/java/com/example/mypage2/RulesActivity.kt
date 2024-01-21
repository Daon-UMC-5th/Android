package com.example.mypage2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mypage2.databinding.ActivityAccessBinding
import com.example.mypage2.databinding.ActivityRulesBinding

class RulesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRulesBinding

    private var isCommunityBtnSelected = false
    private var isDiaryBtnSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRulesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rulesBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        updateButtonState(binding.communityBtn, isCommunityBtnSelected)
        updateButtonState(binding.diaryBtn, isDiaryBtnSelected)

        binding.communityBtn.setOnClickListener {
            handleButtonClick(binding.communityBtn, isCommunityBtnSelected)
        }

        binding.diaryBtn.setOnClickListener {
            handleButtonClick(binding.diaryBtn, isDiaryBtnSelected)
        }

    }
    private fun handleButtonClick(button: Button, isSelected: Boolean) {

        updateButtonState(binding.communityBtn, false)
        updateButtonState(binding.diaryBtn, false)

        updateButtonState(button, true)

        button.setBackgroundResource(R.drawable.button_pressed)

        val textColorResId = if (isSelected) "#AAAAAA" else "#1C734D"
        button.setTextColor(Color.parseColor(textColorResId))

        val fragment = when (button.id) {
            R.id.community_btn -> Rule_commuFragment()
            R.id.diary_btn -> Rule_diaryFragment()
            else -> null
        }
        fragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.btn_frame, it)
                .commit()
        }
    }

    private fun updateButtonState(button: Button, isSelected: Boolean) {
        button.setBackgroundResource(if (!isSelected) R.drawable.button_default else R.drawable.button_pressed)
        val textColorResId = if (!isSelected) "#AAAAAA" else "#1C734D"
        button.setTextColor(Color.parseColor(textColorResId))
    }

}