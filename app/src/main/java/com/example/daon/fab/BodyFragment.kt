package com.example.daon.fab

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daon.MainActivity
import com.example.daon.OnEventListener
import com.example.daon.databinding.FragmentBodyBinding
class BodyFragment : Fragment(), FabControllerActivity.OnSaveEventListener {
    private var _binding: FragmentBodyBinding? = null
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
    ): View? {
        _binding = FragmentBodyBinding.inflate(inflater, container, false)
        changeListener()
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
            // 체온
            bodyTemperature.addTextChangedListener(watcher)
            // 몸무게
            bodyWeight.addTextChangedListener(watcher)
            // 공복혈당
            bodyBloodSugar.addTextChangedListener(watcher)
            // 특이사항
            content.addTextChangedListener(watcher)
        }
    }
    private fun activateRegisterBtn(){
        if (temperature()||weight()||bloodSugar()||content()){
            trueEvent()
        }else{
            falseEvent()
        }
    }
    private fun temperature(): Boolean {
        return !binding.bodyTemperature.text.equals("")
    }
    private fun weight(): Boolean{
        return !binding.bodyWeight.text.equals("")
    }
    private fun bloodSugar(): Boolean{
        return !binding.bodyBloodSugar.text.equals("")
    }
    private fun content(): Boolean{
        return !binding.content.text.equals("")
    }
    private fun trueEvent() {
        eventListener?.onEventOccurred("true")
    }
    private fun falseEvent(){
        eventListener?.onEventOccurred("false")
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    override fun onSaveEventOccurred(data: String) {
        Log.i("body",binding.bodyTemperature.text.toString())
        val intent = Intent(activity, MainActivity::class.java)
        intent.putExtra("title","body")
        intent.putExtra("temperature",binding.bodyTemperature.text.toString())
        intent.putExtra("weight",binding.bodyWeight.text.toString())
        intent.putExtra("bloodSugar",binding.bodyBloodSugar.text.toString())
        intent.putExtra("content",binding.content.text.toString())
        startActivity(intent)
        activity?.finish()
    }
}