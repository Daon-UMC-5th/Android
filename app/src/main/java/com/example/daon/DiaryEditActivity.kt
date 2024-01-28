package com.example.daon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.daon.databinding.ActivityDiaryEditBinding

class DiaryEditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDiaryEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}