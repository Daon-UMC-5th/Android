package com.example.mypage2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.mypage2.databinding.ActivityEditProfileBinding
import com.example.mypage2.databinding.ActivityMainBinding

class Edit_ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editNickname.hint = "홍길동"
        binding.editIntro.hint = "안녕하세요. 의료인으로서 열심히 답해드릴게요!"

        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.save.setOnClickListener {
            val nickname = binding.editNickname.text.toString()
            val intro = binding.editIntro.text.toString()

            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("nickname", nickname)
                putExtra("intro",intro)
                Log.d("Edit", nickname)
            }
            startActivity(intent)
            finish()
        }
    }
}