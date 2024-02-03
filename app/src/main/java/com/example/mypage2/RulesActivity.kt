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

        supportFragmentManager.beginTransaction()
            .replace(R.id.btn_frame, Rule_commuFragment())
            .commit()
    }
}