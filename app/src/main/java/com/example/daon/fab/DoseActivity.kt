package com.example.daon.fab

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TimePicker
import com.example.daon.MainActivity
import com.example.daon.R
import com.example.daon.databinding.ActivityDoseBinding

class DoseActivity : AppCompatActivity(),TimePicker.OnTimeChangedListener {
    private lateinit var binding: ActivityDoseBinding

    private lateinit var selectDate : String
    private lateinit var selectTime : String

    private var selectedBtn: String = "breakfast"
    private var selectedColor: String = "#FFFFFF"
    private var unselectedColor: String = "#757575"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoseBinding.inflate(layoutInflater)
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

            medicine.addTextChangedListener(watcher)
        }
    }
    private fun activateRegisterBtn(){
        if (medicine()){
            binding.saveBtn.setImageResource(R.drawable.check_true)
            binding.saveBtn.isEnabled = true
        }else{
            binding.saveBtn.setImageResource(R.drawable.check_false)
            binding.saveBtn.isEnabled = false
        }
    }
    private fun medicine(): Boolean{
        return binding.medicine.text.toString().trim().isNotEmpty()
    }
    private fun clickListener(){
        binding.backBtn.setOnClickListener{onBackPressed()}
        binding.saveBtn.setOnClickListener{save()}
        binding.breakfast.setOnClickListener(onClickListener)
        binding.lunch.setOnClickListener(onClickListener)
        binding.dinner.setOnClickListener(onClickListener)
        binding.notification.setOnClickListener(onClickListener)
        binding.notiTime.setOnClickListener(onClickListener)
        binding.cycle.setOnClickListener(onClickListener)
        binding.day.setOnClickListener(onClickListener)
    }
    private val onClickListener = View.OnClickListener {
        when (it.id) {
            binding.breakfast.id -> {
                binding.breakfast.setBackgroundResource(R.drawable.select_btn)
                binding.lunch.setBackgroundResource(R.drawable.unselect_btn)
                binding.dinner.setBackgroundResource(R.drawable.unselect_btn)
                selectedBtn = "breakfast"
                binding.breakfast.setTextColor(Color.parseColor(selectedColor))
                binding.lunch.setTextColor(Color.parseColor(unselectedColor))
                binding.dinner.setTextColor(Color.parseColor(unselectedColor))
            }
            binding.lunch.id -> {
                binding.breakfast.setBackgroundResource(R.drawable.unselect_btn)
                binding.lunch.setBackgroundResource(R.drawable.select_btn)
                binding.dinner.setBackgroundResource(R.drawable.unselect_btn)
                selectedBtn = "lunch"
                binding.breakfast.setTextColor(Color.parseColor(unselectedColor))
                binding.lunch.setTextColor(Color.parseColor(selectedColor))
                binding.dinner.setTextColor(Color.parseColor(unselectedColor))
            }
            binding.dinner.id ->{
                binding.breakfast.setBackgroundResource(R.drawable.unselect_btn)
                binding.lunch.setBackgroundResource(R.drawable.unselect_btn)
                binding.dinner.setBackgroundResource(R.drawable.select_btn)
                selectedBtn = "dinner"
                binding.breakfast.setTextColor(Color.parseColor(unselectedColor))
                binding.lunch.setTextColor(Color.parseColor(unselectedColor))
                binding.dinner.setTextColor(Color.parseColor(selectedColor))
            }
            binding.notification.id ->{
                binding.tpTimepicker.visibility = View.VISIBLE
            }
            binding.notiTime.id ->{
                binding.tpTimepicker.visibility = View.GONE
            }
            binding.cycle.id ->{
                binding.cycleLayout.visibility = View.VISIBLE
            }
            binding.day.id->{
                binding.cycleLayout.visibility = View.GONE
            }
        }
    }
    private fun save(){
        saveDose()
        onBackPressed()
    }
    private fun saveDose(){
        //서버에게 전달
    }
    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
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