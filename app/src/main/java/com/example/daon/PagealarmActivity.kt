package com.example.daon

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
        binding.swtAll.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // switch1이 체크되면 나머지 스위치들도 체크
                binding.swt01.isChecked = true
                binding.swt02.isChecked = true
                binding.swt03.isChecked = true
                binding.swt04.isChecked = true
                binding.swt05.isChecked = true
            }
            else{
                binding.swt01.isChecked = false
                binding.swt02.isChecked = false
                binding.swt03.isChecked = false
                binding.swt04.isChecked = false
                binding.swt05.isChecked = false
            }
        }
    }
}