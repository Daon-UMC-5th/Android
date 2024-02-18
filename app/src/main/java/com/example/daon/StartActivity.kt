package com.example.daon

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.daon.AgreeFragment
import com.example.daon.LoginFragment
import com.example.daon.R
import com.example.daon.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentLogin = LoginFragment()
        val fragmentAgree = AgreeFragment()

        binding.startNaverBtn.setOnClickListener {
            val url = "http://15.164.2.250/login/kakao"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        binding.startLogin.setOnClickListener{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_start, fragmentLogin)
                .commit()
        }

        binding.startEmailBtn.setOnClickListener{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_start, fragmentAgree)
                .commit()
        }
    }
}