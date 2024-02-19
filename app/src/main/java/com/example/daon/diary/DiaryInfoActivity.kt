package com.example.daon.diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.daon.R
import com.example.daon.conect.ApiClient
import com.example.daon.conect.diary.DiaryGetOneResponseDto
import com.example.daon.conect.diary.data.DiaryGetOne
import com.example.daon.conect.diary.data.DiaryGetPrivate
import com.example.daon.databinding.ActivityDiaryInfoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiaryInfoBinding
    private var jwt: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMywiaWF0IjoxNzA3NzIwMDY2LCJleHAiOjE3MDgzMjQ4NjYsInN1YiI6InVzZXJJbmZvIn0.8oDPW4Z_Mifj7NwEbO517W9xprRGKbNSU5TUl6sjnc4"

    private lateinit var data: DiaryGetOne
    private lateinit var date: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryInfoBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_diary_info)
        initDiaryInfoData()
    }
    private fun initDiaryInfoData(){
        data = intent.getSerializableExtra("data") as DiaryGetOne
        date = intent.getStringExtra("date").toString()
        Log.i("data",data.toString())
        diaryInfo(data)
    }
    private fun diaryInfo(data: DiaryGetOne){
        binding.title.text = date
        binding.diaryTitle.text = data.title
        binding.diaryContent.text = data.content
        binding.diaryDate.text = data.createdAt
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}