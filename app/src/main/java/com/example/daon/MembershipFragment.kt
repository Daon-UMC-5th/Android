package com.example.daon

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.daon.R
import com.example.daon.conect.ApiClient
import com.example.daon.conect.user.SignUpRequestDto
import com.example.daon.conect.user.SignUpResponseDto
import com.example.daon.conect.user.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MembershipFragment : Fragment() {

    private lateinit var basicBtn: ImageView
    private lateinit var medicalBtn: ImageView
    private lateinit var nextBtn: Button
    private lateinit var closeBtn: ImageView
    private lateinit var backBtn: ImageView
    private var toast: Toast? = null

    private var isBasicSelected = false
    private var isMedicalSelected = false

    fun showToast(msg: String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_membership, container, false)

        basicBtn = view.findViewById(R.id.membership_basic_btn)
        medicalBtn = view.findViewById(R.id.membership_medical_btn)
        nextBtn = view.findViewById(R.id.membership_next_btn)
        closeBtn = view.findViewById(R.id.membership_close_btn)
        backBtn = view.findViewById(R.id.membership_back_btn)

        closeBtn.setOnClickListener {
            val intent = Intent(context, StartActivity::class.java)
            startActivity(intent)

            requireActivity().finish()
        }

        backBtn.setOnClickListener {
            val agreeFragment = AgreeFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_membership, agreeFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        basicBtn.setOnClickListener {
            selectMembership("basic")
        }

        medicalBtn.setOnClickListener {
            selectMembership("medical")
        }

        nextBtn.setOnClickListener {
            if (isBasicSelected) {
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
                    role = "user",
                    agree = ""
                )
                val call = service.signUp(signUpRequest)
                call.enqueue(object : Callback<SignUpResponseDto> {
                    override fun onResponse(call: Call<SignUpResponseDto>, response: Response<SignUpResponseDto>) {
                        if (response.isSuccessful) {
                            val signUpResponse = response.body()
                            if (signUpResponse?.isSuccess == true) {
                                showToast("user가 성공적으로 전송되었습니다.")
                            } else {
                                showToast(signUpResponse?.message ?: "Unknown error")
                            }
                        } else {
                            // 서버와 통신 실패
                            showToast("서버와의 통신에 실패하였습니다.")
                        }
                    }

                    override fun onFailure(call: Call<SignUpResponseDto>, t: Throwable) {
                        showToast("통신 오류: ${t.message}")
                    }
                })

                val joinFragment = JoinFragment()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_membership, joinFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            else{
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
                    role = "doctor",
                    agree = ""
                )
                val call = service.signUp(signUpRequest)
                call.enqueue(object : Callback<SignUpResponseDto> {
                    override fun onResponse(call: Call<SignUpResponseDto>, response: Response<SignUpResponseDto>) {
                        if (response.isSuccessful) {
                            val signUpResponse = response.body()
                            if (signUpResponse?.isSuccess == true) {
                                showToast("doctor가 성공적으로 전송되었습니다.")
                            } else {
                                showToast(signUpResponse?.message ?: "Unknown error")
                            }
                        } else {
                            showToast("서버와의 통신에 실패하였습니다.")
                        }
                    }

                    override fun onFailure(call: Call<SignUpResponseDto>, t: Throwable) {
                        // 통신 오류
                        showToast("통신 오류: ${t.message}")
                    }
                })


                val medicalCheckFragment = MedicalCheckFragment()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_membership, medicalCheckFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }

        return view
    }

    private fun selectMembership(selectedMembership: String) {
        basicBtn.setImageResource(R.drawable.basic_mem)
        medicalBtn.setImageResource(R.drawable.medical_mem)


        when (selectedMembership) {
            "basic" -> {
                basicBtn.setImageResource(R.drawable.check_basic)
                nextBtn.setBackgroundResource(R.drawable.btnstyle)
                nextBtn.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
                isBasicSelected = true
                isMedicalSelected = false
            }
            "medical" -> {
                medicalBtn.setImageResource(R.drawable.check_medical)
                nextBtn.setBackgroundResource(R.drawable.btnstyle)
                nextBtn.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
                isBasicSelected = false
                isMedicalSelected = true
            }
        }
    }
}