package com.example.daon

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daon.databinding.ActivityAccessBinding
import com.example.mypage2.PasswordchangeActivity

class AccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.accessBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.changePassword.setOnClickListener {
            val intent = Intent(this, PasswordchangeActivity::class.java)
            startActivity(intent)
        }
    }
}