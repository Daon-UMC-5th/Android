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
import android.widget.EditText
import android.widget.TimePicker
import androidx.core.content.ContextCompat
import com.example.daon.MainActivity
import com.example.daon.OnEventListener
import com.example.daon.R
import com.example.daon.databinding.FragmentClinicBinding
import java.util.Calendar

class ClinicFragment : Fragment(), TimePicker.OnTimeChangedListener, FabControllerActivity.OnSaveEventListener {

    private var _binding: FragmentClinicBinding? = null
    private val binding get() = _binding!!

    private var eventListener: OnEventListener? = null
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
    ): View {
        _binding = FragmentClinicBinding.inflate(inflater, container, false)

        clickListener()
        changeListener()
        binding.tpTimepicker.setOnTimeChangedListener(this)
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
            activateRegisterBtn()
        }
        override fun afterTextChanged(s: Editable?) {
        }
    }
    private fun changeListener(){
        with(binding) {
            val watcher = MyEditWatcher()
            // 병원
            clinicHospital.addTextChangedListener(watcher)
            // 내용
            clinicContent.addTextChangedListener(watcher)
        }
    }
    private fun activateRegisterBtn(){
        if (hospital()||content()){
            trueEvent()
        }else{
            falseEvent()
        }
    }
    private fun hospital(): Boolean {
        return !binding.clinicHospital.text.equals("")
    }
    private fun content(): Boolean{
        return !binding.clinicContent.text.equals("")
    }
    private fun clickListener(){
        binding.time.setOnClickListener{
            binding.tpTimepicker.visibility = View.VISIBLE
            var cal = Calendar.getInstance()
            val hour = cal.get(Calendar.HOUR_OF_DAY)
            val minute = cal.get(Calendar.MINUTE)
//            Log.i("time","$hour:$minute")
//            binding.notiTime.setTextColor(0X1C734D)
//            binding.notiTime.text = "$hour:$minute"
            binding.notiTime.text = "취소"
        }
        binding.notiTime.setOnClickListener{
            binding.notiTime.text = "없음"
            binding.tpTimepicker.visibility = View.GONE
        }
    }
    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
//        binding.notiTime.text = "$hourOfDay:$minute"
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    private fun trueEvent() {
        // 이벤트를 액티비티로 전달
        eventListener?.onEventOccurred("true")
    }
    private fun falseEvent(){
        eventListener?.onEventOccurred("false")
    }

    override fun onSaveEventOccurred(data: String) {
        Log.i("clinic",binding.clinicHospital.text.toString())
        val intent = Intent(activity, MainActivity::class.java)
        intent.putExtra("title","clinic")
        intent.putExtra("hospital",binding.clinicHospital.text.toString())
        intent.putExtra("context",binding.clinicContent.text.toString())
        startActivity(intent)
        activity?.finish()
    }

}