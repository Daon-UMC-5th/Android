package com.example.daon.fab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TimePicker
import com.example.daon.MainActivity
import com.example.daon.R
import com.example.daon.databinding.ActivityClinicBinding
import java.util.Calendar

class ClinicActivity : AppCompatActivity(),TimePicker.OnTimeChangedListener {
    private lateinit var binding: ActivityClinicBinding

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
            binding.tpTimepicker.visibility = View.GONE
        }
    }
    private fun save(){
        saveClinic()
        onBackPressed()
    }
    private fun saveClinic(){
        //서버에게 전달
    }
    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
//        binding.notiTime.text = "$hourOfDay:$minute"
        selectTime = "$hourOfDay:$minute"
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("title","calendar")
        startActivity(intent)
        finish()
    }
}