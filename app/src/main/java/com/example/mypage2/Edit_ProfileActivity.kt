package com.example.mypage2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.mypage2.databinding.ActivityEditProfileBinding
import com.example.mypage2.databinding.FragmentUseBinding

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

        binding.editNickname.addTextChangedListener {
            updateButtonState(binding.save, binding.editNickname.text.isNotEmpty() && binding.editIntro.text.isNotEmpty())
        }

        binding.editIntro.addTextChangedListener {
            updateButtonState(binding.save, binding.editNickname.text.isNotEmpty() && binding.editIntro.text.isNotEmpty())
        }
        binding.save.setOnClickListener {
            val nickname = binding.editNickname.text.toString()
            val intro = binding.editIntro.text.toString()
            setDataFragment(MypageFragment(),nickname,intro)
            finish()
            Log.d("Edit", nickname)
        }

    }

    fun setDataFragment(fragment: Fragment, nickname:String, intro:String){
        val bundle = Bundle()
        bundle.putString("nickname",nickname)
        bundle.putString("intro",intro)
        fragment.arguments = bundle
    }
    private fun updateButtonState(button: Button, isEnabled: Boolean) {
        button.isEnabled = isEnabled
        button.setBackgroundResource(if (isEnabled) R.drawable.btn_02 else R.drawable.btn_01)

        val textColor = if (isEnabled) ContextCompat.getColor(this, R.color.white)
        else ContextCompat.getColor(this, R.color.btn_text1)
        button.setTextColor(textColor)
    }
}