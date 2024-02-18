package com.example.daon.info

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.daon.MainActivity
import com.example.daon.R
import com.example.daon.conect.ApiClient
import com.example.daon.conect.calendar.BodyListInsertResponseDto
import com.example.daon.conect.calendar.DoseMonthListResponseDto
import com.example.daon.conect.calendar.data.BodyListCall
import com.example.daon.databinding.ActivityBodyInfoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BodyInfoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBodyInfoBinding
    private lateinit var data : BodyListCall
    private lateinit var date : String
    private var jwt: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMywiaWF0IjoxNzA3NzIwMDY2LCJleHAiOjE3MDgzMjQ4NjYsInN1YiI6InVzZXJJbmZvIn0.8oDPW4Z_Mifj7NwEbO517W9xprRGKbNSU5TUl6sjnc4"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBodyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initInfo()
        clickListener()
    }
    private fun clickListener(){
        binding.backBtn.setOnClickListener{ onBackPressed() }
        binding.deleteBtn.setOnClickListener { openDialog()}
        binding.dialogNo.setOnClickListener { closeDialog() }
        binding.dialogYes.setOnClickListener { deleteRecord() }
        binding.editBtn.setOnClickListener { editIntent() }
    }
    private fun editIntent(){
        val intent = Intent(this,BodyEditActivity::class.java)
        intent.putExtra("date",date)
        intent.putExtra("data",data)
        startActivity(intent)
        finish()
    }
    private fun closeDialog(){
        binding.background.visibility = View.GONE
        binding.cancelDialog.visibility = View.GONE
    }
    private fun openDialog(){
        binding.background.visibility= View.VISIBLE
        binding.cancelDialog.visibility= View.VISIBLE
    }
    private fun deleteRecord(){
        val call = ApiClient.calendarService.bodyListDelete(jwt,intent.getStringExtra("date")!!)
        call.enqueue(object : Callback<BodyListInsertResponseDto> {
            override fun onResponse(call: Call<BodyListInsertResponseDto>, response: Response<BodyListInsertResponseDto>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    // 서버로부터 받은 응답을 처리합니다.
                    // 예를 들어, 로그인 성공 여부에 따라 처리할 수 있습니다.
                    if (body != null) {
                        Log.i("bodyDeleteSuccess", body.toString())
                        when (body.code) {
                            200 -> {
                                onBackPressed()
                            }
                            404 -> {
                                showToast(body.message)
                            }
                            500 -> {
                                showToast(body.message)
                            }
                        }
                    }
                } else {
                    showToast("Failed to communicate with the server.")
                    Log.i("bodyDeleteNot",response.body().toString())
                }
            }
            override fun onFailure(call: Call<BodyListInsertResponseDto>, t: Throwable) {
                showToast("Network request failed. Error: ${t.message}")
                Log.i("bodyDeleteFail",t.message.toString())
            }
        })
    }
    private fun initInfo(){
        data = intent.getSerializableExtra("data") as BodyListCall
        date = intent.getStringExtra("date").toString()
        binding.bodyTemperature.text = data.temperature
        binding.bodyWeight.text = data.weight
        binding.bodyBloodSugar.text = data.fasting_blood_sugar.toString()
        binding.content.text = data.special_note
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