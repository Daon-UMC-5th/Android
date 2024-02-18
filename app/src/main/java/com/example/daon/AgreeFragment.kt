package com.example.daon

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.daon.conect.user.SignUpRequestDto
import com.example.daon.conect.user.SignUpResponseDto
import com.example.daon.conect.user.UserService
import com.example.daon.data.community.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AgreeFragment : Fragment() {

    private lateinit var agreeAllCheckBox: CheckBox
    private lateinit var agreeCheck1: CheckBox
    private lateinit var agreeCheck2: CheckBox
    private lateinit var agreeCheck3: CheckBox
    private lateinit var agreeNextBtn: Button
    private lateinit var backBtn: ImageView
    private lateinit var closeBtn: ImageView
    private var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agree, container, false)


        agreeAllCheckBox = view.findViewById(R.id.agree_all_check)
        agreeCheck1 = view.findViewById(R.id.agree_check1)
        agreeCheck2 = view.findViewById(R.id.agree_check2)
        agreeCheck3 = view.findViewById(R.id.agree_check3)
        agreeNextBtn = view.findViewById(R.id.agree_next_btn)
        backBtn = view.findViewById(R.id.agree_back_btn)
        closeBtn = view.findViewById(R.id.agree_close_btn)

        fun showToast(msg: String) {
            toast?.cancel()
            toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
            toast?.show()
        }

        agreeAllCheckBox.setOnClickListener {
            val isChecked = agreeAllCheckBox.isChecked

            agreeCheck1.isChecked = isChecked
            agreeCheck2.isChecked = isChecked
            agreeCheck3.isChecked = isChecked
        }

        backBtn.setOnClickListener {
            val intent = Intent(context, StartActivity::class.java)
            startActivity(intent)

            requireActivity().finish()
        }

        closeBtn.setOnClickListener {
            val intent = Intent(context, StartActivity::class.java)
            startActivity(intent)

            requireActivity().finish()
        }

        val checkedChangeListener = CompoundButton.OnCheckedChangeListener { _, _ ->
            updateNextButtonState()
        }

        agreeCheck1.setOnCheckedChangeListener(checkedChangeListener)
        agreeCheck2.setOnCheckedChangeListener(checkedChangeListener)
        updateNextButtonState()

        agreeNextBtn.setOnClickListener {
            if (agreeCheck1.isChecked && agreeCheck2.isChecked) {

                if(agreeCheck3.isChecked){
                    val service = ApiClient.retrofit.create(UserService::class.java)
                    val signUpRequest = SignUpRequestDto(
                        user_name = "",
                        email = "",
                        password = "",
                        phone_number = "",
                        birth_date = "",
                        gender = 0,
                        user_nickname = "",
                        introduction = "",
                        role = "",
                        agree = "1"
                    )
                    val call = service.signUp(signUpRequest)
                    call.enqueue(object : Callback<SignUpResponseDto> {
                        override fun onResponse(call: Call<SignUpResponseDto>, response: Response<SignUpResponseDto>) {
                            if (response.isSuccessful) {
                                val signUpResponse = response.body()
                                if (signUpResponse?.isSuccess == true) {
                                    // 서버에 이메일 전송 성공
                                    showToast("동의가 성공적으로 전송되었습니다.")
                                } else {
                                    // 서버에서 실패 응답을 받음
                                    showToast(signUpResponse?.message ?: "Unknown error")
                                }
                            } else {
                                // 서버와 통신 실패
                                showToast("서버와의 통신에 실패하였습니다.")
                            }
                        }
                        override fun onFailure(call: Call<SignUpResponseDto>, t: Throwable) {
                            // 통신 오류
                            showToast("통신 오류: ${t.message}")
                        }
                    })
                } else{
                    val service = ApiClient.retrofit.create(UserService::class.java)
                    val signUpRequest = SignUpRequestDto(
                        user_name = "",
                        email = "",
                        password = "",
                        phone_number = "",
                        birth_date = "",
                        gender = 0,
                        user_nickname = "",
                        introduction = "",
                        role = "",
                        agree = "0"
                    )
                    val call = service.signUp(signUpRequest)
                    call.enqueue(object : Callback<SignUpResponseDto> {
                        override fun onResponse(call: Call<SignUpResponseDto>, response: Response<SignUpResponseDto>) {
                            if (response.isSuccessful) {
                                val signUpResponse = response.body()
                                if (signUpResponse?.isSuccess == true) {
                                    // 서버에 이메일 전송 성공
                                    showToast("동의가 성공적으로 전송되었습니다.")
                                } else {
                                    // 서버에서 실패 응답을 받음
                                    showToast(signUpResponse?.message ?: "Unknown error")
                                }
                            } else {
                                // 서버와 통신 실패
                                showToast("서버와의 통신에 실패하였습니다.")
                            }
                        }
                        override fun onFailure(call: Call<SignUpResponseDto>, t: Throwable) {
                            // 통신 오류
                            showToast("통신 오류: ${t.message}")
                        }
                    })
                }

                val membershipFragment = MembershipFragment()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_agree, membershipFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }

        return view
    }

    private fun updateNextButtonState() {
        val isButtonEnabled = agreeCheck1.isChecked && agreeCheck2.isChecked

        if (isButtonEnabled) {
            agreeNextBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.btnstyle)
            agreeNextBtn.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
        } else {
            agreeNextBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.uncheck_btnstyle)
            agreeNextBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }
    }

}