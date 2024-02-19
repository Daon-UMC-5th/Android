package com.example.daon.info

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.daon.MainActivity
import com.example.daon.R
import com.example.daon.conect.ApiClient
import com.example.daon.conect.calendar.ClinicListInsertResponseDto
import com.example.daon.conect.calendar.DoseListInsertResponseDto
import com.example.daon.conect.calendar.data.ClinicListCall
import com.example.daon.conect.calendar.data.DoseListCall
import com.example.daon.databinding.ActivityDoseInfoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoseInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoseInfoBinding
    private lateinit var data : DoseListCall
    private lateinit var date : String
    private var jwt: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMywiaWF0IjoxNzA3NzIwMDY2LCJleHAiOjE3MDgzMjQ4NjYsInN1YiI6InVzZXJJbmZvIn0.8oDPW4Z_Mifj7NwEbO517W9xprRGKbNSU5TUl6sjnc4"

    private var selectedColor: String = "#FFFFFF"
    private var unselectedColor: String = "#757575"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoseInfoBinding.inflate(layoutInflater)
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
    private fun deleteRecord(){
        val call = ApiClient.calendarService.doseListDelete(jwt,data.medicationId)
        call.enqueue(object : Callback<DoseListInsertResponseDto> {
            override fun onResponse(call: Call<DoseListInsertResponseDto>, response: Response<DoseListInsertResponseDto>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    // 서버로부터 받은 응답을 처리합니다.
                    // 예를 들어, 로그인 성공 여부에 따라 처리할 수 있습니다.
                    if (body != null) {
                        Log.i("clinicDeleteSuccess", body.toString())
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
                    Log.i("clinicDeleteNot",response.body().toString())
                }
            }
            override fun onFailure(call: Call<DoseListInsertResponseDto>, t: Throwable) {
                showToast("Network request failed. Error: ${t.message}")
                Log.i("clinicDeleteFail",t.message.toString())
            }
        })
    }
    private fun editIntent(){
        val intent = Intent(this,DoseEditActivity::class.java)
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
    private fun initInfo(){
        data = intent.getSerializableExtra("data") as DoseListCall
        if(data.timeOfDay.contains("아침")) {
            binding.breakfast.setBackgroundResource(R.drawable.select_btn)
            binding.breakfast.setTextColor(Color.parseColor(selectedColor))
        }else{
            binding.breakfast.setBackgroundResource(R.drawable.unselect_btn)
            binding.breakfast.setTextColor(Color.parseColor(unselectedColor))
        }
        if(data.timeOfDay.contains("점심")){
            binding.lunch.setBackgroundResource(R.drawable.select_btn)
            binding.lunch.setTextColor(Color.parseColor(selectedColor))
        }else{
            binding.lunch.setBackgroundResource(R.drawable.unselect_btn)
            binding.lunch.setTextColor(Color.parseColor(unselectedColor))
        }
        if(data.timeOfDay.contains("저녁")){
            binding.dinner.setBackgroundResource(R.drawable.select_btn)
            binding.dinner.setTextColor(Color.parseColor(selectedColor))
        }else{
            binding.dinner.setBackgroundResource(R.drawable.unselect_btn)
            binding.dinner.setTextColor(Color.parseColor(unselectedColor))
        }
        binding.medicine.text = data.medicine
        binding.notiTime.text = data.alarmedAt
        binding.day.text = data.alarmDays
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