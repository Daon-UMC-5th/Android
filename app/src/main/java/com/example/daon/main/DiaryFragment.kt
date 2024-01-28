package com.example.daon.main

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daon.DiaryEditActivity
import com.example.daon.MyApplication
import com.example.daon.R
import com.example.daon.databinding.FragmentDiaryBinding
import com.example.daon.fab.FabControllerActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.properties.Delegates

class DiaryFragment : Fragment() {
    private lateinit var _binding: FragmentDiaryBinding
    private val binding get() = _binding!!
    private var noticeCnt: Int = 0
    private var diaryStatement : String = PRIVATE
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiaryBinding.inflate(inflater,container,false)
        initNotice()
        initDiaryStatement()
        clickListener()
        return binding.root
    }
    private fun initNotice(){
        if (noticeCnt==0) binding.notice.setImageResource(R.drawable.notice)
        else binding.notice.setImageResource(R.drawable.notice_true)
    }
    private fun initDiaryStatement(){
        when(MyApplication.preferences.getDiaryStatement("diaryStatement", PRIVATE)){
            PRIVATE ->{
                diaryStatement= PRIVATE
                movingSwitch()
                initPrivateCalendar()
            }
            SHARE ->{
                diaryStatement= SHARE
                movingSwitch()
                initShareCalendar()
            }
        }
    }
    private fun initPrivateCalendar(){
        year = MyApplication.preferences.getDate(DIARY_PRIVATE_YEAR,CalendarDay.today().year)
        month = MyApplication.preferences.getDate(DIARY_PRIVATE_MONTH,CalendarDay.today().month+2)
        day = MyApplication.preferences.getDate(DIARY_PRIVATE_DAY,CalendarDay.today().month-3)
        var dateString: String = "$year-$month-$day"//MyApplication.preferences.getDate(CALENDAR_DATE,CalendarDay.today().toString())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date = dateFormat.parse(dateString)
        binding.calendarView.selectedDate = CalendarDay.from(date)
        binding.calendarView.setCurrentDate(CalendarDay.from(date), true)
//            binding.calendarView.selectedDate = CalendarDay.today()
//            binding.calendarView.addDecorators(SaturdayDecorator(), SundayDecorator())
    }
    private fun initShareCalendar(){
        year = MyApplication.preferences.getDate(DIARY_SHARE_YEAR,CalendarDay.today().year)
        month = MyApplication.preferences.getDate(DIARY_SHARE_MONTH,CalendarDay.today().month+2)
        day = MyApplication.preferences.getDate(DIARY_SHARE_DAY,CalendarDay.today().month-3)
        var dateString: String = "$year-$month-$day"//MyApplication.preferences.getDate(CALENDAR_DATE,CalendarDay.today().toString())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date = dateFormat.parse(dateString)
        binding.calendarView.selectedDate = CalendarDay.from(date)
        binding.calendarView.setCurrentDate(CalendarDay.from(date), true)
//            binding.calendarView.selectedDate = CalendarDay.today()
//            binding.calendarView.addDecorators(SaturdayDecorator(), SundayDecorator())
    }
    private fun clickListener(){
        binding.diarySwitch.setOnClickListener{
            if(diaryStatement== PRIVATE){
                diaryStatement = SHARE
                MyApplication.preferences.setDiaryStatement(DIARY_STATEMENT, SHARE)
                ObjectAnimator.ofFloat(binding.switchSelected, "translationX", 240f).apply { start() }
                binding.switchSelected.text = "공유"
                binding.unselectedShare.visibility = View.INVISIBLE
                binding.unselectedPrivate.visibility = View.VISIBLE
                initShareCalendar()
            }else if(diaryStatement== SHARE){
                diaryStatement = PRIVATE
                MyApplication.preferences.setDiaryStatement(DIARY_STATEMENT, PRIVATE)
                ObjectAnimator.ofFloat(binding.switchSelected, "translationX", 0f).apply { start() }
                binding.switchSelected.text = "개인"
                binding.unselectedPrivate.visibility = View.INVISIBLE
                binding.unselectedShare.visibility = View.VISIBLE
                initPrivateCalendar()
            }
        }
        binding.searchBtn.setOnClickListener{

        }
        binding.notice.setOnClickListener{
            MyApplication.preferences.removeDate(DIARY_STATEMENT)
            MyApplication.preferences.removeDate(DIARY_PRIVATE_YEAR)
            MyApplication.preferences.removeDate(DIARY_PRIVATE_MONTH)
            MyApplication.preferences.removeDate(DIARY_PRIVATE_DAY)
            MyApplication.preferences.removeDate(DIARY_SHARE_YEAR)
            MyApplication.preferences.removeDate(DIARY_SHARE_MONTH)
            MyApplication.preferences.removeDate(DIARY_SHARE_DAY)
        }
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
    private fun movingSwitch(){
        if(diaryStatement== SHARE){
            diaryStatement = SHARE
            MyApplication.preferences.setDiaryStatement(DIARY_STATEMENT, SHARE)
            ObjectAnimator.ofFloat(binding.switchSelected, "translationX", 240f).apply { start() }
            binding.switchSelected.text = "공유"
            binding.unselectedShare.visibility = View.INVISIBLE
            binding.unselectedPrivate.visibility = View.VISIBLE
        }else if(diaryStatement== PRIVATE){
            diaryStatement = PRIVATE
            MyApplication.preferences.setDiaryStatement(DIARY_STATEMENT, PRIVATE)
            ObjectAnimator.ofFloat(binding.switchSelected, "translationX", 0f).apply { start() }
            binding.switchSelected.text = "개인"
            binding.unselectedPrivate.visibility = View.INVISIBLE
            binding.unselectedShare.visibility = View.VISIBLE
        }
    }
    private fun calendarStatementSave(year:Int,month: Int,day: Int){
        if(diaryStatement== PRIVATE){
            MyApplication.preferences.setDate(DIARY_PRIVATE_YEAR,year)
            MyApplication.preferences.setDate(DIARY_PRIVATE_MONTH,month)
            MyApplication.preferences.setDate(DIARY_PRIVATE_DAY,day)
        }else if(diaryStatement==SHARE){
            MyApplication.preferences.setDate(DIARY_SHARE_YEAR,year)
            MyApplication.preferences.setDate(DIARY_SHARE_MONTH,month)
            MyApplication.preferences.setDate(DIARY_SHARE_DAY,day)
        }

    }
    private fun callList(year:Int,month:Int,day:Int){
        //백엔드에 날짜 보내주고 일정 리스트 받아오기
    }
    companion object{
        const val TAG = "DiaryFragment"
        const val DIARY_STATEMENT = "diaryStatement"
        const val DIARY_PRIVATE_YEAR = "diaryPrivateYear"
        const val DIARY_PRIVATE_MONTH = "diaryPrivateMonth"
        const val DIARY_PRIVATE_DAY = "diaryPrivateDay"
        const val DIARY_SHARE_YEAR = "diaryShareYear"
        const val DIARY_SHARE_MONTH = "diaryShareMonth"
        const val DIARY_SHARE_DAY = "diaryShareDay"
        const val PRIVATE = "private"
        const val SHARE = "share"
    }

}