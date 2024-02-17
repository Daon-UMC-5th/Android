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
            email = "a71144722211@gmail.com",
            password = "kwon1231",
            phone_number = "010-3135-9442",
            birth_date = "990820",
            gender = 3,
            user_nickname = "chan7",
            introduction = "안녕하세요. 잘부탁드려요.",
            role = "user",
            agree = "1",
            profile_url = R.drawable.calendar.toString(),
            doctor_url = R.drawable.calendar.toString()
        )

        val service = retrofit.create(DaonService::class.java)
        val call = service.signUp(signUpRequestDto)
        val call2 = service.getAllUsers()

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

                                val userCall = service.getAllUsers()
                                userCall.enqueue(object : Callback<UsersResponseDto> {
                                    override fun onResponse(call: Call<UsersResponseDto>, response: Response<UsersResponseDto>) {
                                        if (response.isSuccessful) {
                                            val userResponse = response.body()
                                            val nickname = userResponse?.result?.get(0)?.user_nickname
                                            preferenceUtil.saveUserNickname(nickname ?: "")
                                            Log.d("닉네임", nickname.toString())
                                        } else {
                                            // 사용자 정보 조회 실패 처리
                                        }
                                    }

                                    override fun onFailure(call: Call<UsersResponseDto>, t: Throwable) {
                                        // 통신 실패 처리
                                    }
                                })
                            } else {
                                // 로그인 실패 처리
                            }
                        }

                        override fun onFailure(call: Call<LoginResponseDto>, t: Throwable) {
                            // 통신 실패 처리
                        }
                    })
                } else {
                    // 회원가입 실패 처리
                }
            }

            override fun onFailure(call: Call<SignUpResponseDto>, t: Throwable) {
                // 통신 실패 처리
            }
        })
        return binding.root
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}