package com.example.daon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.daon.databinding.ActivityReadwriteBinding
import com.example.daon.databinding.ActivityWriteBinding

class ReadwriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadwriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadwriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.writeBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.commentBackground.setOnClickListener {
            val intent = Intent(this,CommentActivity::class.java)
            startActivity(intent)
        }
    }
}