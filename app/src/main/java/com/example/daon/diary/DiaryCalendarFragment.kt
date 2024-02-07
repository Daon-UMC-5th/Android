package com.example.daon.diary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daon.MyApplication
import com.example.daon.R
import com.example.daon.databinding.FragmentDiaryCalendarBinding
import com.example.daon.main.CalendarFragment
import com.example.daon.main.DiaryFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DiaryCalendarFragment : Fragment() {
    private var _binding: FragmentDiaryCalendarBinding? = null
    private val binding get() = _binding!!

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
            val selectedDate = "$year-$month-$day"

            if(selectedDate == today){
                // 오늘 색깔로 바꾸는 코드
            }else{
                // 다른 색깔로 바꾸는 코드
            }
            calendarStatementSave(year,month,day)
            callList(year,month,day)
        }
        binding.fabPlus.setOnClickListener{
            val intent = Intent(activity, DiaryEditActivity::class.java)
            intent.putExtra("year",year)
            intent.putExtra("month",month)
            intent.putExtra("day",day)
            startActivity(intent)
            activity?.finish()
        }
    }
    private fun calendarStatementSave(year:Int,month: Int,day: Int){
        MyApplication.preferences.setDate(DiaryFragment.DIARY_PRIVATE_YEAR,year)
        MyApplication.preferences.setDate(DiaryFragment.DIARY_PRIVATE_MONTH,month)
        MyApplication.preferences.setDate(DiaryFragment.DIARY_PRIVATE_DAY,day)
    }
    private fun callList(year:Int,month:Int,day:Int){
        //백엔드에 날짜 보내주고 일기 받아오기
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
//            binding.calendarView.selectedDate = CalendarDay.today()
//            binding.calendarView.addDecorators(SaturdayDecorator(), SundayDecorator())
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}