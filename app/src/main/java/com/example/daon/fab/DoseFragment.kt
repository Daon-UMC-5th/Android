package com.example.daon.fab

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daon.OnEventListener
import com.example.daon.databinding.FragmentDoseBinding

class DoseFragment : Fragment(), FabControllerActivity.OnSaveEventListener {
    private var _binding: FragmentDoseBinding? = null
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
        _binding = FragmentDoseBinding.inflate(inflater, container, false)
//        clickListener()
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
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onSaveEventOccurred(data: String) {
        TODO("Not yet implemented")
    }
}