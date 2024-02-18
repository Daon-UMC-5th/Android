package com.example.daon.fab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.example.daon.MainActivity
import com.example.daon.R
import com.example.daon.conect.ApiClient
import com.example.daon.conect.calendar.BodyListInsertRequestDto
import com.example.daon.conect.calendar.BodyListInsertResponseDto
import com.example.daon.conect.calendar.ClinicListInsertResponseDto
import com.example.daon.databinding.ActivityBodyBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BodyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBodyBinding
    private var jwt: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMywiaWF0IjoxNzA3NzIwMDY2LCJleHAiOjE3MDgzMjQ4NjYsInN1YiI6InVzZXJJbmZvIn0.8oDPW4Z_Mifj7NwEbO517W9xprRGKbNSU5TUl6sjnc4"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBodyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickListener()
        changeListener()
    }
    inner class MyEditWatcher: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){
        }
        // 값 변경 시 실행되는 함수
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // 버튼 활성화 여부
        }
        override fun afterTextChanged(s: Editable?) {
            activateRegisterBtn()
        }
    }
    private fun changeListener(){
        with(binding) {
            val watcher = MyEditWatcher()
            // 체온
            bodyTemperature.addTextChangedListener(watcher)
            // 몸무게
            bodyWeight.addTextChangedListener(watcher)
            // 공복혈당
            bodyBloodSugar.addTextChangedListener(watcher)
            // 특이사항
            content.addTextChangedListener(watcher)
        }
    }
    private fun activateRegisterBtn(){
        //액티비티 <-> 프래그먼트 이벤트 처리가 안되는 느낌
        if (temperature()||weight()||bloodSugar()||content()){
            binding.saveBtn.setImageResource(R.drawable.check_true)
            binding.saveBtn.isEnabled = true
        }else{
            binding.saveBtn.setImageResource(R.drawable.check_false)
            binding.saveBtn.isEnabled = false
        }
    }
    private fun temperature(): Boolean {
        return binding.bodyTemperature.text.toString().trim().isNotEmpty()
    }
    private fun weight(): Boolean{
        return binding.bodyWeight.text.toString().trim().isNotEmpty()
    }
    private fun bloodSugar(): Boolean{
        return binding.bodyBloodSugar.toString().trim().isNotEmpty()
    }
    private fun content(): Boolean{
        return binding.content.text.toString().trim().isNotEmpty()
    }
    private fun clickListener() {
        binding.backBtn.setOnClickListener{onBackPressed()}
        binding.saveBtn.setOnClickListener{save()}
    }
    private fun save(){
        saveBody()
        onBackPressed()
    }
    private fun saveBody(){
        Log.i("saveClinic","------------")
        val date = intent.getStringExtra("selectedDate").toString()
        Log.i("selectedDate",date)
        val bodyListInsertRequestDto = BodyListInsertRequestDto(
            temperature = if(binding.bodyTemperature.text.isEmpty()) 0.0 else binding.bodyTemperature.text.toString().toDouble(),
            weight = if(binding.bodyWeight.text.isEmpty()) 0.0 else binding.bodyWeight.text.toString().toDouble(),
            fastingBloodSugar = if(binding.bodyBloodSugar.text.isEmpty()) 0 else binding.bodyBloodSugar.text.toString().toInt(),
            note = binding.content.text.toString()
        )
        val call = ApiClient.calendarService.bodyListInsert(jwt,date, bodyListInsertRequestDto)
        call.enqueue(object : Callback<BodyListInsertResponseDto> {
            override fun onResponse(call: Call<BodyListInsertResponseDto>, response: Response<BodyListInsertResponseDto>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    // 서버로부터 받은 응답을 처리합니다.
                    // 예를 들어, 로그인 성공 여부에 따라 처리할 수 있습니다.
                    if (body != null) {
                        Log.i("saveBodySuccess", body.toString())
                        when (body.code) {
                            200 -> {
                                onBackPressed()
                            }
                            400 -> {
                                showToast(body.message)
                            }
                            500 -> {
                                showToast(body.message)
                            }
                        }
                    }
                } else {
                    showToast("Failed to communicate with the server.")
                    Log.i("saveBodyNot",response.toString())
                }
            }
            override fun onFailure(call: Call<BodyListInsertResponseDto>, t: Throwable) {
                showToast("Network request failed. Error: ${t.message}")
                Log.i("saveBodyFail",t.message.toString())
            }
        })
        Log.i("saveClinic","------------")
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("title","calendar")
        startActivity(intent)
        finish()
    }

}