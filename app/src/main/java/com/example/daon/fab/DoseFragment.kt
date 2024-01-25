package com.example.daon.fab

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import com.example.daon.MainActivity
import com.example.daon.OnEventListener
import com.example.daon.R
import com.example.daon.databinding.FragmentDoseBinding

class DoseFragment : Fragment(), TimePicker.OnTimeChangedListener,FabControllerActivity.OnSaveEventListener {
    private var _binding: FragmentDoseBinding? = null
    private val binding get() = _binding!!
    private var eventListener: OnEventListener? = null

    private var selectedBtn: String = "breakfast"
    private var selectedColor: String = "#FFFFFF"
    private var unselectedColor: String = "#757575"

    private lateinit var selectDate : String
    private lateinit var selectTime : String
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEventListener) {
            eventListener = context
        } else {
            throw RuntimeException("$context must implement OnEventListener")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoseBinding.inflate(inflater, container, false)
        clickListener()
//        changeListener()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? FabControllerActivity)?.setOnSomeEventListener(this)
    }
    inner class MyEditWatcher: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){
        }
        // 값 변경 시 실행되는 함수
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // 버튼 활성화 여부
//            activateRegisterBtn()
        }
        override fun afterTextChanged(s: Editable?) {
        }
    }
//    private fun changeListener(){
//        with(binding) {
//            val watcher = MyEditWatcher()
//            // 삼시세끼 버튼
//            breakfast.setOnClickListener{
//                breakfast.setBackgroundResource()
//                activateRegisterBtn()
//            }
//            lunch.setOnClickListener()
//            dinner.setOnClickListener()
//            // 약
//            medicine.addTextChangedListener(watcher)
//            // 시간
//            notification.setOnClickListener()
//            // 반복
//
//        }
//    }
//    private fun activateRegisterBtn(){
//        if (()||content()){
//            trueEvent()
//        }else{
//            falseEvent()
//        }
//    }
    private fun clickListener(){
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
    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        selectTime = "$hourOfDay:$minute"
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onSaveEventOccurred(data: String) {
        Log.i("clinic",binding.medicine.text.toString())
        val intent = Intent(activity, MainActivity::class.java)
        intent.putExtra("title","dose")
        intent.putExtra("date",selectDate)
        intent.putExtra("medicine",binding.medicine.text.toString())
        intent.putExtra("time",selectTime)
        startActivity(intent)
        activity?.finish()
    }


}