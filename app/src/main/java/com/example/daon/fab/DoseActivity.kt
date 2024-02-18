package com.example.daon.fab

import android.content.Intent
import android.graphics.Color
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
import com.example.daon.conect.calendar.BodyListInsertRequestDto
import com.example.daon.conect.calendar.BodyListInsertResponseDto
import com.example.daon.conect.calendar.DoseListInsertRequestDto
import com.example.daon.conect.calendar.DoseListInsertResponseDto
import com.example.daon.databinding.ActivityDoseBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoseActivity : AppCompatActivity(),TimePicker.OnTimeChangedListener {
    private lateinit var binding: ActivityDoseBinding
    private var jwt: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMywiaWF0IjoxNzA3NzIwMDY2LCJleHAiOjE3MDgzMjQ4NjYsInN1YiI6InVzZXJJbmZvIn0.8oDPW4Z_Mifj7NwEbO517W9xprRGKbNSU5TUl6sjnc4"

    private var selectTime : String = ""

    private var selectedBreakfast: Boolean = false
    private var selectedLunch: Boolean = false
    private var selectedDinner: Boolean = false
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
                if(selectedBreakfast){
                    binding.breakfast.setBackgroundResource(R.drawable.unselect_btn)
                    binding.breakfast.setTextColor(Color.parseColor(unselectedColor))
                    selectedBreakfast = false
                }
                else if(!selectedBreakfast){
                    binding.breakfast.setBackgroundResource(R.drawable.select_btn)
                    binding.breakfast.setTextColor(Color.parseColor(selectedColor))
                    selectedBreakfast = true
                }
            }
            binding.lunch.id -> {
                if(selectedLunch){
                    binding.lunch.setBackgroundResource(R.drawable.unselect_btn)
                    binding.lunch.setTextColor(Color.parseColor(unselectedColor))
                    selectedLunch = false
                }
                else if(!selectedLunch){
                    binding.lunch.setBackgroundResource(R.drawable.select_btn)
                    binding.lunch.setTextColor(Color.parseColor(selectedColor))
                    selectedLunch = true
                }
            }
            binding.dinner.id ->{
                if(selectedDinner){
                    binding.dinner.setBackgroundResource(R.drawable.unselect_btn)
                    binding.dinner.setTextColor(Color.parseColor(unselectedColor))
                    selectedDinner = false
                }
                else if(!selectedDinner){
                    binding.dinner.setBackgroundResource(R.drawable.select_btn)
                    binding.dinner.setTextColor(Color.parseColor(selectedColor))
                    selectedDinner = true
                }
            }
            binding.notification.id ->{
                binding.tpTimepicker.visibility = View.VISIBLE
                binding.notiTime.text = "취소"
            }
            binding.notiTime.id ->{
                binding.tpTimepicker.visibility = View.GONE
                binding.notiTime.text = "없음"
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
        if(selectedBreakfast) saveDoseTime("morning")
        if(selectedLunch) saveDoseTime("noon")
        if(selectedDinner) saveDoseTime("evening")
    }
    private fun saveDoseTime(time: String){
        Log.i("saveDoseTime","in")
        val date = intent.getStringExtra("selectedDate")!!
        Log.i("selectedDate",date)
        val doseListInsertRequestDto = DoseListInsertRequestDto(
            medicine = binding.medicine.text.toString(),
            alarmed_at = selectTime,
            alarm_days = getDate()
        )
        val call = ApiClient.calendarService.doseListInsert(jwt,time,date, doseListInsertRequestDto)
        call.enqueue(object : Callback<DoseListInsertResponseDto> {
            override fun onResponse(call: Call<DoseListInsertResponseDto>, response: Response<DoseListInsertResponseDto>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    // 서버로부터 받은 응답을 처리합니다.
                    // 예를 들어, 로그인 성공 여부에 따라 처리할 수 있습니다.
                    if (body != null) {
                        Log.i("saveDoseTimeSuccess", body.toString())
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
                    Log.i("saveDoseTimeNot","fail")
                }
            }
            override fun onFailure(call: Call<DoseListInsertResponseDto>, t: Throwable) {
                showToast("Network request failed. Error: ${t.message}")
                Log.i("saveDoseTimeFail",t.message.toString())
            }
        })
    }
    private fun getDate() : String{
        var date : String = ""
        if(binding.sunday.isChecked){
            date=checkDateString(date,"일")
        }
        if(binding.monday.isChecked){
            date=checkDateString(date,"월")
        }
        if(binding.tuesday.isChecked){
            date=checkDateString(date,"화")
        }
        if(binding.wednesday.isChecked){
            date=checkDateString(date,"수")
        }
        if(binding.thursday.isChecked){
            date=checkDateString(date,"목")
        }
        if(binding.friday.isChecked){
            date=checkDateString(date,"금")
        }
        if(binding.saturday.isChecked){
            date=checkDateString(date,"토")
        }
        return date
    }
    private fun checkDateString(first: String,date: String) : String{
        return if(first == "") date
        else "$first, $date"
    }
    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
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