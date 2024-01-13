package com.example.daon

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.daon.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.launch

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private var noticeCnt: Int = 0
    private lateinit var selectedDate: String
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        initNotice()
        initCalendar()
        clickListener()
        year = binding.calendarView.selectedDate!!.year
        month = binding.calendarView.selectedDate!!.month + 1
        day = binding.calendarView.selectedDate!!.day
        selectedDate = "$year-$month-$day"
        Log.i(TAG,selectedDate)

        return binding.root
    }
    //캘린더 UI 초기 설정 함수
    private fun initCalendar(){
            binding.calendarView.selectedDate = CalendarDay.today()
//            binding.calendarView.addDecorators(SaturdayDecorator(), SundayDecorator())
    }
    //알림 UI 초기 설정 함수
    private fun initNotice(){
        if (noticeCnt==0) binding.notice.setImageResource(R.drawable.notice)
        else binding.notice.setImageResource(R.drawable.notice_true)
    }
    //클릭 이벤트 종합 함수
    private fun clickListener(){
        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            year = date.year
            month = date.month + 1
            day = date.day
            val today: String =
                CalendarDay.today().year.toString()+ "-"+
                        (CalendarDay.today().month+1).toString()+ "-" +
                        CalendarDay.today().day.toString()
            Log.i(TAG,today)
            selectedDate = "$year-$month-$day"
            if(selectedDate == today){
                // 오늘 색깔로 바꾸는 코드
            }else{
                // 다른 색깔로 바꾸는 코드
            }
            callList(year,month,day)
        }
    }
    private fun callList(year:Int,month:Int,day:Int){
        //백엔드에 날짜 보내주고 일정 리스트 받아오기
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    companion object{
        const val TAG = "CalendarFragment"
    }
}