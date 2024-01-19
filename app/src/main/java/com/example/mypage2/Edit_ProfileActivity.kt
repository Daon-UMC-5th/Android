package com.example.mypage2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.example.mypage2.databinding.ActivityEditProfileBinding
import com.example.mypage2.databinding.ActivityMainBinding

class Edit_ProfileActivity : AppCompatActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()
    private val sharedViewModel2: SharedViewModel2 by viewModels()
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

            sharedViewModel.setUserInput(nickname)
            sharedViewModel2.setUserInput2(intro)
            finish()
        }
    }
    private fun navigateToMypageFragment() {
        val nextFragment = MypageFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frm, nextFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}