package com.example.daon

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class FindDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_find_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 다이얼로그 내에서 버튼 클릭 등의 이벤트 처리
        view.findViewById<Button>(R.id.go_login).setOnClickListener {
            // 로그인 프래그먼트로 이동하는 코드 작성
            val loginFragment = LoginFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.find_dialog_fragment, loginFragment)
            transaction.addToBackStack(null)
            transaction.commit()

            // 다이얼로그를 닫음
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            // 다이얼로그 스타일 등을 설정할 수 있음
            setCancelable(false) // 백버튼으로 닫히지 않도록 설정
        }
    }
}
