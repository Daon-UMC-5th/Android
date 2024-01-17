package com.example.mypage2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mypage2.databinding.ActivityEditProfileBinding
import com.example.mypage2.databinding.ActivityMainBinding

class Edit_ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editNickname.hint = "홍길동"
        binding.editIntro.hint = "안녕하세요. 의료인으로서 열심히 답해드릴게요!"
    }

}