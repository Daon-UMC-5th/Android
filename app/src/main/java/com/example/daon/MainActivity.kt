package com.example.daon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.daon.databinding.ActivityMainBinding
import com.example.daon.main.CalendarFragment
import com.example.daon.main.CommunityFragment
import com.example.daon.main.DiaryFragment
import com.example.daon.main.MypageFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation()
    }
    private fun initBottomNavigation(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, CalendarFragment())
            .commit()
        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.calendarFragment -> {
                    val calendarFragment = CalendarFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frm, calendarFragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }

                R.id.diaryFragment -> {
                    val diaryFragment = DiaryFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frm, diaryFragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.communityFragment -> {
                    val communityFragment = CommunityFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, communityFragment)
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.mypageFragment -> {
                    val mypageFragment = MypageFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, mypageFragment)
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}