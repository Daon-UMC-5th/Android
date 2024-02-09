package com.example.daon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daon.databinding.ActivityRules2Binding

class RulesActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityRules2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRules2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rulesBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.btn_frame2, Rule_diaryFragment())
            .commit()
    }
}
