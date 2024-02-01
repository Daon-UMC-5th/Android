package com.example.mypage2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mypage2.databinding.ActivityAccessBinding
import com.example.mypage2.databinding.ActivityRules2Binding

class RulesActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityRules2Binding

    private var isrule1BtnSelected = false
    private var isrule2BtnSelected = false
    private var isrule3BtnSelected = false
    private var isrule4BtnSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRules2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rulesBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        updateButtonState(binding.rule1,isrule1BtnSelected)
        updateButtonState(binding.rule2,isrule2BtnSelected)
        updateButtonState(binding.rule3,isrule3BtnSelected)
        updateButtonState(binding.rule4,isrule4BtnSelected)

        binding.rule1.setOnClickListener {
            handleButtonClick(binding.rule1,isrule1BtnSelected)
        }
        binding.rule2.setOnClickListener {
            handleButtonClick(binding.rule2,isrule2BtnSelected)
        }
        binding.rule3.setOnClickListener {
            handleButtonClick(binding.rule3,isrule3BtnSelected)
        }
        binding.rule4.setOnClickListener {
            handleButtonClick(binding.rule4,isrule4BtnSelected)
        }

    }

    private fun handleButtonClick(button: Button, isSelected: Boolean) {

        updateButtonState(binding.rule1, false)
        updateButtonState(binding.rule2, false)
        updateButtonState(binding.rule3, false)
        updateButtonState(binding.rule4, false)

        updateButtonState(button, true)
        button.setBackgroundResource(R.drawable.button_pressed)

        val textColorResId = if (isSelected) "#AAAAAA" else "#1C734D"
        button.setTextColor(Color.parseColor(textColorResId))

        val fragment = when (button.id) {
            R.id.rule_1 -> UseFragment()
            R.id.rule_2 -> Rule_diaryFragment()
            R.id.rule_3 -> Rule_commuFragment()
            R.id.rule_4 -> Rule_diaryFragment()
            else -> null
        }
        fragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.btn_frame2, it)
                .commit()
        }
    }

    private fun updateButtonState(button: Button, isSelected: Boolean) {
        button.setBackgroundResource(if (!isSelected) R.drawable.button_default else R.drawable.button_pressed)
        val textColorResId = if (!isSelected) "#AAAAAA" else "#1C734D"
        button.setTextColor(Color.parseColor(textColorResId))
    }

}
