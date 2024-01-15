package com.example.daon.main

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.example.daon.FabControllerActivity
import com.example.daon.R
import com.example.daon.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private var isFabOpen = false

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
        binding.fabPlus.setOnClickListener {
            toggleFab()
        }
        // 플로팅 버튼 클릭 이벤트 - 신체
        binding.fabBody.setOnClickListener {
            Toast.makeText(this.context, "신체 버튼 클릭!", Toast.LENGTH_SHORT).show()
            changeIntent("신체")
        }
        // 플로팅 버튼 클릭 이벤트 - 진료
        binding.fabClinic.setOnClickListener {
            Toast.makeText(this.context, "진료 버튼 클릭!", Toast.LENGTH_SHORT).show()
            changeIntent("진료")
        }
        // 플로팅 버튼 클릭 이벤트 - 복용
        binding.fabDose.setOnClickListener{
            Toast.makeText(this.context, "복용 버튼 클릭!", Toast.LENGTH_SHORT).show()
            changeIntent("복용")
        }
        // 회색 배경 클릭 이벤트
        binding.background.setOnClickListener{
            toggleFab()
        }

    }
    private fun changeIntent(selectFragment: String){
        val intent = Intent(activity, FabControllerActivity::class.java)
        intent.putExtra("fragment",selectFragment)
        startActivity(intent)
        activity?.finish()
    }
    private fun toggleFab() {
        Toast.makeText(this.context, "메인 버튼 클릭!", Toast.LENGTH_SHORT).show()
        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션
        if (isFabOpen) {
            binding.background.visibility = View.GONE
            ObjectAnimator.ofFloat(binding.fabClinic, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.clinicText, "translationY", 0f).apply { start() }
            binding.clinicText.visibility = View.GONE
            ObjectAnimator.ofFloat(binding.fabDose, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.doseText, "translationY", 0f).apply { start() }
            binding.doseText.visibility = View.GONE
            ObjectAnimator.ofFloat(binding.fabBody, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.bodyText, "translationY", 0f).apply { start() }
            binding.bodyText.visibility = View.GONE
            ObjectAnimator.ofFloat(binding.fabPlus, View.ROTATION, 45f, 0f).apply { start() }
        } else { // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션
            binding.background.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(binding.fabClinic, "translationY", -540f).apply { start() }
            ObjectAnimator.ofFloat(binding.clinicText, "translationY", -540f).apply { start() }
            binding.clinicText.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(binding.fabDose, "translationY", -360f).apply { start() }
            ObjectAnimator.ofFloat(binding.doseText, "translationY", -360f).apply { start() }
            binding.doseText.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(binding.fabBody, "translationY", -180f).apply { start() }
            ObjectAnimator.ofFloat(binding.bodyText, "translationY", -180f).apply { start() }
            binding.bodyText.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(binding.fabPlus, View.ROTATION, 0f, 45f).apply { start() }
        }
        isFabOpen = !isFabOpen

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