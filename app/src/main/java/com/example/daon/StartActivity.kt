package com.example.daon

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