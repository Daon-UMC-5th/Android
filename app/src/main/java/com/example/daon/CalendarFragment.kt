package com.example.daon

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daon.community.ApiClient.retrofit
import com.example.daon.community.token.*
import com.example.daon.databinding.FragmentCalendarBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val preferenceUtil = PreferenceUtil(requireContext())
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)

        val signUpRequestDto = SignUpRequestDto(
            user_name = "권혁찬",
            email = "a7117944711@gmail.com",
            password = "kwon123",
            phone_number = "010-7115-9447",
            birth_date = "990820",
            gender = 3,
            user_nickname = "chan1",
            introduction = "안녕하세요. 잘부탁드려요.",
            role = "user",
            agree = "1"
        )

        val service = retrofit.create(DaonService::class.java)
        val call = service.signUp(signUpRequestDto)

        call.enqueue(object : Callback<SignUpResponseDto> {
            override fun onResponse(call: Call<SignUpResponseDto>, response: Response<SignUpResponseDto>) {
                if (response.isSuccessful) {
                    val signUpResponse = response.body()
                    // 회원가입 성공 시 토큰 발행 코드 구현
                    val loginRequestDto = LoginRequestDto(
                        email = signUpRequestDto.email,
                        password = signUpRequestDto.password
                    )
                    val loginCall = service.login(loginRequestDto)
                    loginCall.enqueue(object : Callback<LoginResponseDto> {
                        override fun onResponse(call: Call<LoginResponseDto>, response: Response<LoginResponseDto>) {
                            if (response.isSuccessful) {
                                val loginResponse = response.body()
                                val token = loginResponse?.result
                                preferenceUtil.saveToken(token?:"")
                                Log.d("토큰",token.toString())
                            } else {
                                // 로그인 실패 처리
                            }
                        }

                        override fun onFailure(call: Call<LoginResponseDto>, t: Throwable) {
                            // 통신 실패 처리
                        }
                    })
                } else {
                    // 서버 응답이 실패일 때 처리
                    val errorBody = response.errorBody()?.string()
                    // 에러 처리 코드 작성
                }
            }

            override fun onFailure(call: Call<SignUpResponseDto>, t: Throwable) {
                // 통신 실패 시 처리
            }
        })

        return binding.root
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}