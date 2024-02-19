package com.example.daon.diary

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.daon.EventDecorator
import com.example.daon.MyApplication
import com.example.daon.conect.ApiClient
import com.example.daon.conect.diary.DiaryGetOneResponseDto
import com.example.daon.conect.diary.DiaryGetResponseDto
import com.example.daon.conect.diary.data.DiaryGetOne
import com.example.daon.conect.diary.data.DiaryGetPrivate
import com.example.daon.databinding.FragmentDiaryCalendarBinding
import com.example.daon.main.CalendarFragment
import com.example.daon.main.DiaryFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date
import java.util.Locale

class DiaryCalendarFragment : Fragment() {
    private var jwt: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMywiaWF0IjoxNzA3NzIwMDY2LCJleHAiOjE3MDgzMjQ4NjYsInN1YiI6InVzZXJJbmZvIn0.8oDPW4Z_Mifj7NwEbO517W9xprRGKbNSU5TUl6sjnc4"
    private var _binding: FragmentDiaryCalendarBinding? = null
    private lateinit var data: List<DiaryGetPrivate>
    private val binding get() = _binding!!
    private var selectedDate : String = ""

    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiaryCalendarBinding.inflate(inflater,container,false)
        clickListener()
        initCalendar()
        return binding.root
    }
    private fun clickListener(){
        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            year = date.year
            month = date.month + 1
            day = date.day
            val today: String =
                CalendarDay.today().year.toString()+ "-"+
                        (CalendarDay.today().month+1).toString()+ "-" +
                        CalendarDay.today().day.toString()
            Log.i(CalendarFragment.TAG,today)
            if (month<10){
                selectedDate =
                    year.toString()+ "-0"+
                            (month).toString()+ "-" +
                            day.toString()
            }else{
                selectedDate =
                    year.toString()+ "-"+
                            month.toString()+ "-" +
                            day.toString()
            }
            callList(selectedDate)
            calendarStatementSave(year,month,day)
        }
        binding.fabPlus.setOnClickListener{
            val intent = Intent(activity, DiaryEditActivity::class.java)
            intent.putExtra("year",year)//CalendarDay.today().year)//year)
            intent.putExtra("month",month)//CalendarDay.today().month)
            intent.putExtra("day",day)//CalendarDay.today().day)
            startActivity(intent)
            activity?.finish()
        }
    }
    private fun calendarStatementSave(year:Int,month: Int,day: Int){
        MyApplication.preferences.setDate(DiaryFragment.DIARY_PRIVATE_YEAR,year)
        MyApplication.preferences.setDate(DiaryFragment.DIARY_PRIVATE_MONTH,month)
        MyApplication.preferences.setDate(DiaryFragment.DIARY_PRIVATE_DAY,day)
    }
    private fun initCalendar(){
        year = MyApplication.preferences.getDate(DiaryFragment.DIARY_PRIVATE_YEAR, CalendarDay.today().year)
        month = MyApplication.preferences.getDate(DiaryFragment.DIARY_PRIVATE_MONTH, CalendarDay.today().month+1)
        day = MyApplication.preferences.getDate(DiaryFragment.DIARY_PRIVATE_DAY, CalendarDay.today().day)
        var dateString: String = "$year-$month-$day"//MyApplication.preferences.getDate(CALENDAR_DATE,CalendarDay.today().toString())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date = dateFormat.parse(dateString)
        binding.calendarView.selectedDate = CalendarDay.from(date)
        binding.calendarView.setCurrentDate(CalendarDay.from(date), true)
        if (month<10){
            selectedDate =
                year.toString()+ "-0"+
                        (month).toString()+ "-"
        }else{
            selectedDate =
                year.toString()+ "-"+
                        month.toString()+ "-"
        }
        callAllList(selectedDate)
        calendarStatementSave(year,month,day)
    }
    private fun callAllList(date:String){
        val call = ApiClient.diaryService.diaryGetPrivate(jwt)
        call.enqueue(object : Callback<DiaryGetResponseDto> {
            override fun onResponse(call: Call<DiaryGetResponseDto>, response: Response<DiaryGetResponseDto>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    // 서버로부터 받은 응답을 처리합니다.
                    // 예를 들어, 로그인 성공 여부에 따라 처리할 수 있습니다.
                    if (body != null) {
                        Log.i("dairyAllSuccess", body.toString())
                        when (body.code) {
                            200 -> {
                                data = body.result
                                initCalendarDate(body.result,date)
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
                    Log.i("dairyAllNot",response.body().toString())
                }
            }
            override fun onFailure(call: Call<DiaryGetResponseDto>, t: Throwable) {
                showToast("Network request failed. Error: ${t.message}")
                Log.i("dairyAllFail",t.message.toString())
            }
        })
    }
    private fun initCalendarDate(result: List<DiaryGetPrivate>,date:String){
        for(i in result){
            if(i.createdAt.contains(date)){
                binding.calendarView
                    .addDecorator(
                        EventDecorator(
                            Color.parseColor("#1C734D"),
                            Collections.singleton(CalendarDay.from(year, month-1, day)))
                    )
            }
        }
    }
    private fun callList(date:String){
        val call = ApiClient.diaryService.diaryGetOne(jwt,date)
        call.enqueue(object : Callback<DiaryGetOneResponseDto> {
            override fun onResponse(call: Call<DiaryGetOneResponseDto>, response: Response<DiaryGetOneResponseDto>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    // 서버로부터 받은 응답을 처리합니다.
                    // 예를 들어, 로그인 성공 여부에 따라 처리할 수 있습니다.
                    if (body != null) {
                        Log.i("dairyAllSuccess", body.toString())
                        when (body.code) {
                            200 -> {
                                diaryInfoIntent(body.result,date)
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
                    Log.i("dairyAllNot",response.body().toString())
                }
            }
            override fun onFailure(call: Call<DiaryGetOneResponseDto>, t: Throwable) {
                showToast("Network request failed. Error: ${t.message}")
                Log.i("dairyAllFail",t.message.toString())
            }
        })
    }
    private fun diaryInfoIntent(data:DiaryGetOne,date:String) {
        val intent = Intent(requireContext(), DiaryInfoActivity::class.java)
        intent.putExtra("data", data)
        intent.putExtra("date", date)
        startActivity(intent)
        activity?.finish()
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}