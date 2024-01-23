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
    private lateinit var binding : ActivityMainBinding
    private val PREFS_NAME = "MyPrefsFile"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation()

    }

    override fun onResume() {
        super.onResume()

        val selectedItemId = getSavedSelectedItemId()

        // 이전에 선택된 아이템이 있으면 해당 프래그먼트로 교체
        if (selectedItemId != -1) {
            binding.mainBnv.selectedItemId = selectedItemId
        }
            val myPageFragment = MypageFragment()
            val dataFromEditProfile = intent.getStringExtra("nickname")
            val dataFromEditProfile2 = intent.getStringExtra("intro")

            if (dataFromEditProfile != null && dataFromEditProfile2 != null) {
                myPageFragment.arguments = Bundle().apply {
                    putString("nickname", dataFromEditProfile)
                    putString("intro", dataFromEditProfile2)
                }
                Log.d("frag",dataFromEditProfile)
        }
    }
    override fun onPause() {
        super.onPause()

        // 현재 선택된 BottomNavigationView의 아이템 ID를 저장
        saveSelectedItemId(binding.mainBnv.selectedItemId)
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        if(currentFocus is EditText) {
            currentFocus!!.clearFocus()
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, CalendarFragment())
            .commit()

        val selectedItemId = getSavedSelectedItemId()
        if (selectedItemId != -1) {
            binding.mainBnv.selectedItemId = selectedItemId
        }
        binding.mainBnv.setOnItemSelectedListener{ item ->
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
    private fun saveSelectedItemId(itemId: Int) {
        // SharedPreferences를 사용하여 선택된 아이템 ID 저장
        val editor = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.putInt("selectedItemId", itemId)
        editor.apply()
    }

    private fun getSavedSelectedItemId(): Int {
        // SharedPreferences를 사용하여 저장된 선택된 아이템 ID 가져오기
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt("selectedItemId", -1)
    }
}