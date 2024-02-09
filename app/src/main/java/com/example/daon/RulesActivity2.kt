package com.example.daon

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mypage2.databinding.ActivityAccessBinding
import com.example.mypage2.databinding.ActivityRules2Binding

class RulesActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityRules2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRules2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rulesBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.btn_frame2, Rule_diaryFragment())
            .commit()
    }
}
