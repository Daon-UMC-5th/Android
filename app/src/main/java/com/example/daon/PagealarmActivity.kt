package com.example.mypage2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mypage2.databinding.ActivityPagealarmBinding


class PagealarmActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPagealarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagealarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.alarmBack.setOnClickListener {
          onBackPressedDispatcher.onBackPressed()
        }
    }
}