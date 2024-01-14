package com.example.daon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.daon.databinding.ActivityFabControllerBinding
import com.example.daon.fab.BodyFragment
import com.example.daon.fab.ClinicFragment
import com.example.daon.fab.DoseFragment

class FabControllerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFabControllerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFabControllerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFragment()
    }
    private fun initFragment(){
        when (intent.getStringExtra("fragment")) {
            "진료" -> {
                selectFragment(ClinicFragment())
            }
            "복용" -> {
                selectFragment(DoseFragment())
            }
            "신체" -> {
                selectFragment(BodyFragment())
            }
        }
    }
    private fun selectFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, fragment)
            .commit()
    }
}