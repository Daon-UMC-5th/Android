package com.example.mypage2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mypage2.databinding.ActivityAccessBinding
import com.example.mypage2.databinding.ActivityEditProfileBinding

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
            val intent = Intent(this,PasswordchangeActivity::class.java)
            startActivity(intent)
        }
    }
}