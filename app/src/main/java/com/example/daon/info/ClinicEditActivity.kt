package com.example.daon.info

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.daon.MainActivity
import com.example.daon.R
import com.example.daon.conect.ApiClient
import com.example.daon.conect.calendar.BodyListInsertRequestDto
import com.example.daon.conect.calendar.BodyListInsertResponseDto
import com.example.daon.conect.calendar.ClinicListInsertRequestDto
import com.example.daon.conect.calendar.ClinicListInsertResponseDto
import com.example.daon.conect.calendar.data.BodyListCall
import com.example.daon.conect.calendar.data.ClinicListCall
import com.example.daon.databinding.ActivityClinicEditBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClinicEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClinicEditBinding
    private lateinit var data : ClinicListCall
    private lateinit var date : String
    private var jwt: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMywiaWF0IjoxNzA3NzIwMDY2LCJleHAiOjE3MDgzMjQ4NjYsInN1YiI6InVzZXJJbmZvIn0.8oDPW4Z_Mifj7NwEbO517W9xprRGKbNSU5TUl6sjnc4"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClinicEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initInfo()
        clickListener()
    }
    private fun clickListener(){
        binding.backBtn.setOnClickListener{ onBackPressed() }
        binding.deleteBtn.setOnClickListener { openDialog()}
        binding.dialogNo.setOnClickListener { closeDialog() }
        binding.dialogYes.setOnClickListener { deleteRecord() }
        binding.saveBtn.setOnClickListener { editInfo() }
    }
    private fun editInfo(){
        val clinicListInsertRequestDto = ClinicListInsertRequestDto(
            hospital = binding.clinicHospital.text.toString(),
            content = binding.clinicContent.text.toString(),
            alarmed_at = binding.notiTime.text.toString()
        )
        val call = ApiClient.calendarService.clinicListEdit(jwt,data.consultation_id,clinicListInsertRequestDto)
        call.enqueue(object : Callback<ClinicListInsertResponseDto> {
            override fun onResponse(call: Call<ClinicListInsertResponseDto>, response: Response<ClinicListInsertResponseDto>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    // 서버로부터 받은 응답을 처리합니다.
                    // 예를 들어, 로그인 성공 여부에 따라 처리할 수 있습니다.
                    if (body != null) {
                        Log.i("clinicEditSuccess", body.toString())
                        when (body.code) {
                            200 -> {
                                editIntent()
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
                    Log.i("clinicEditNot",response.body().toString())
                }
            }
            override fun onFailure(call: Call<ClinicListInsertResponseDto>, t: Throwable) {
                showToast("Network request failed. Error: ${t.message}")
                Log.i("clinicEditFail",t.message.toString())
                editIntent()
            }
        })
    }
    private fun editIntent(){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("title","calendar")
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
        val call = ApiClient.calendarService.clinicListDelete(jwt,data.consultation_id)
        call.enqueue(object : Callback<ClinicListInsertResponseDto> {
            override fun onResponse(call: Call<ClinicListInsertResponseDto>, response: Response<ClinicListInsertResponseDto>) {
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
            override fun onFailure(call: Call<ClinicListInsertResponseDto>, t: Throwable) {
                showToast("Network request failed. Error: ${t.message}")
                Log.i("clinicDeleteFail",t.message.toString())
                onBackPressed()
            }
        })
    }
    private fun initInfo(){
        data = intent.getSerializableExtra("data") as ClinicListCall
        binding.clinicHospital.setText(data.hospital)
        binding.clinicContent.setText(data.content)
        binding.notiTime.text = data.alarmed_date
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, ClinicInfoActivity::class.java)
        intent.putExtra("data",data)
        startActivity(intent)
        finish()
    }
}