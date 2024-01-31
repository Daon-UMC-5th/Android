package com.example.daon.fab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.daon.MainActivity
import com.example.daon.R
import com.example.daon.databinding.ActivityBodyBinding

class BodyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBodyBinding
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
        //서버에게 전달
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("title","calendar")
        startActivity(intent)
        finish()
    }

}