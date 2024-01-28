package com.example.daon.main

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daon.MyApplication
import com.example.daon.R
import com.example.daon.databinding.FragmentDiaryBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlin.properties.Delegates

class DiaryFragment : Fragment() {
    private lateinit var _binding: FragmentDiaryBinding
    private val binding get() = _binding!!
    private var noticeCnt: Int = 0
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiaryBinding.inflate(inflater,container,false)
        initNotice()
        initStatement()
        initCalendar()
        return binding.root
    }
    private fun initNotice(){
        if (noticeCnt==0) binding.notice.setImageResource(R.drawable.notice)
        else binding.notice.setImageResource(R.drawable.notice_true)
    }
    private fun initStatement(){
        initDiaryStatement()
        initCalendar()
    }
    private fun initDiaryStatement(){
        when(MyApplication.preferences.getStatement("diaryStatement","개인")){
            "개인" ->{

            }
            "공유" ->{

            }
        }
    }
    private fun initCalendar(){
//        year = MyApplication.preferences.getDate("diaryPrivateYear",CalendarDay.today().year)
//        month = MyApplication.preferences.getDate("diaryPrivateMonth",CalendarDay.today().month)
//        day = MyApplication.preferences.getDate("diaryPrivateDay",CalendarDay.today().day)
    }

}