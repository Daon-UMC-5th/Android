package com.example.daon.fab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import com.example.daon.MainActivity
import com.example.daon.R
import com.example.daon.conect.ApiClient
import com.example.daon.conect.calendar.ClinicListInsertRequestDto
import com.example.daon.conect.calendar.ClinicListInsertResponseDto
import com.example.daon.databinding.ActivityClinicBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class ClinicActivity : AppCompatActivity(),TimePicker.OnTimeChangedListener {
    private lateinit var binding: ActivityClinicBinding
    private var jwt: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMywiaWF0IjoxNzA3NzIwMDY2LCJleHAiOjE3MDgzMjQ4NjYsInN1YiI6InVzZXJJbmZvIn0.8oDPW4Z_Mifj7NwEbO517W9xprRGKbNSU5TUl6sjnc4"

    private var selectTime: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClinicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeListener()
        clickListener()
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
            // 병원
            clinicHospital.addTextChangedListener(watcher)
            // 내용
            clinicContent.addTextChangedListener(watcher)
        }
    }
    private fun activateRegisterBtn(){
        if (hospital()||content()){
            binding.saveBtn.setImageResource(R.drawable.check_true)
            binding.saveBtn.isEnabled = true
        }else{
            binding.saveBtn.setImageResource(R.drawable.check_false)
            binding.saveBtn.isEnabled = false
        }
    }
    private fun hospital(): Boolean {
        return binding.clinicHospital.text.toString().trim().isNotEmpty()
    }
    private fun content(): Boolean{
        return binding.clinicContent.text.toString().trim().isNotEmpty()
    }
    private fun clickListener() {
        binding.backBtn.setOnClickListener{onBackPressed()}
        binding.saveBtn.setOnClickListener{save()}
        binding.time.setOnClickListener{
            binding.tpTimepicker.visibility = View.VISIBLE
            var cal = Calendar.getInstance()
            val hour = cal.get(Calendar.HOUR_OF_DAY)
            val minute = cal.get(Calendar.MINUTE)
//            Log.i("time","$hour:$minute")
//            binding.notiTime.setTextColor(0X1C734D)
//            binding.notiTime.text = "$hour:$minute"
            binding.notiTime.text = "취소"
        }
        binding.notiTime.setOnClickListener{
            binding.notiTime.text = "없음"
            selectTime=""
            binding.tpTimepicker.visibility = View.GONE
        }
    }
    private fun save(){
        saveClinic()
        onBackPressed()
    }
    private fun saveClinic(){
        Log.i("saveClinic","------------")
        val date = intent.getStringExtra("selectedDate")!!
        Log.i("selectedDate",date)
        val clinicListInsertRequestDto = ClinicListInsertRequestDto(
            hospital = binding.clinicHospital.text.toString(),
            content = binding.clinicContent.text.toString(),
            alarmed_at = selectTime
        )
        val call = ApiClient.calendarService.clinicListInsert(jwt,date,clinicListInsertRequestDto)
        call.enqueue(object : Callback<ClinicListInsertResponseDto> {
            override fun onResponse(call: Call<ClinicListInsertResponseDto>, response: Response<ClinicListInsertResponseDto>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    // 서버로부터 받은 응답을 처리합니다.
                    // 예를 들어, 로그인 성공 여부에 따라 처리할 수 있습니다.
                    if (body != null) {
                        Log.i("saveClinicSuccess", body.toString())
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
                    Log.i("saveClinicNot",response.toString())
                }
            }
            override fun onFailure(call: Call<ClinicListInsertResponseDto>, t: Throwable) {
                showToast("Network request failed. Error: ${t.message}")
                Log.i("saveClinicFail",t.message.toString())
            }
        })
        Log.i("saveClinic","------------")
    }
    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
//        binding.notiTime.text = "$hourOfDay:$minute"
        selectTime = "$hourOfDay:$minute"
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