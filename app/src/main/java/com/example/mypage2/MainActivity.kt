package com.example.mypage2

import android.content.Context
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.example.mypage2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation()

        Mypage()
    }

    private fun Mypage() {
        val nick = intent.getStringExtra("nickname")
        val intr = intent.getStringExtra("intro")
        val bundle = Bundle()
        bundle.putString("nick", nick)
        bundle.putString("intr", intr)

        val fragment = MypageFragment()
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, fragment)
            .commit()
    }

    private fun initBottomNavigation() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, CalendarFragment())
            .commit()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.calendarFragment -> {
                    val homeFragment = CalendarFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frm, homeFragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }

                R.id.diaryFragment -> {
                    val lookFragment = DiaryFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frm, lookFragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.communityFragment -> {
                    val searchFragment = CommunityFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, searchFragment)
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.mypageFragment -> {
                    val lockerFragment = MypageFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, lockerFragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}