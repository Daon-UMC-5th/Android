package com.example.daon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class YesResultFragment : Fragment() {

    private lateinit var idTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var loginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_yes_result, container, false)

        idTextView = view.findViewById(R.id.id)
        dateTextView = view.findViewById(R.id.date)
        loginButton = view.findViewById(R.id.login_btn)

        val jsonResponse = """
            {
              "isSuccess": true,
              "code": 200,
              "message": "접근에 성공했습니다.",
              "result": [
                {
                  "email": "abc123@naver.com",
                  "created_at": "2024.02.02"
                }
              ]
            }
        """.trimIndent()

        val gson = Gson()
        val type = object : TypeToken<ResponseDto>() {}.type
        val response: ResponseDto = gson.fromJson(jsonResponse, type)

        response.result?.let { result ->
            idTextView.text = result.email
            dateTextView.text = result.created_at
        }

        loginButton.setOnClickListener {
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val fragment = LoginFragment()
            fragmentTransaction.replace(R.id.fragment_yes_result, fragment)
            fragmentTransaction.commit()
        }

        return view
    }

    data class ResponseDto(
        val isSuccess: Boolean,
        val code: Int,
        val message: String,
        val result: ResultDto?
    )

    data class ResultDto(
        val email: String,
        val created_at: String
    )
}
