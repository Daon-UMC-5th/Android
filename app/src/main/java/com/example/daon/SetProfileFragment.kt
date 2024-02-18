package com.example.daon

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.daon.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.daon.conect.ApiClient
import com.example.daon.conect.user.OverlapNicknameRequestDto
import com.example.daon.conect.user.OverlapNicknameResponseDto
import com.example.daon.conect.user.SignUpRequestDto
import com.example.daon.conect.user.SignUpResponseDto
import com.example.daon.conect.user.UserService

class SetProfileFragment : Fragment() {

    private lateinit var profileNameEditText: EditText
    private lateinit var profileNameCheckButton: ImageView
    private lateinit var profileNameIcon: ImageView
    private lateinit var profileNextButton: Button
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var profileImg: ImageView
    private lateinit var profileIntroEdittext: EditText
    private lateinit var profileBack: View
    private lateinit var profileDialog: FrameLayout
    private lateinit var profileNameDup: ImageView
    private var toast: Toast? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_set_profile, container, false)

        profileNameEditText = view.findViewById(R.id.profile_name_et)
        profileNameCheckButton = view.findViewById(R.id.profile_name_check)
        profileNameIcon = view.findViewById(R.id.profile_name_iv)
        profileNextButton = view.findViewById(R.id.profile_next_btn)
        profileImg = view.findViewById(R.id.profile_img)
        profileIntroEdittext = view.findViewById(R.id.profile_intro_et)
        profileBack = view.findViewById(R.id.profile_back)
        profileDialog = view.findViewById(R.id.profile_dialog)
        profileNameDup = view.findViewById(R.id.profile_name_duplication)

        fun showToast(msg: String) {
            toast?.cancel()
            toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
            toast?.show()
        }

        profileImg.setOnClickListener {
            profileBack.visibility = View.VISIBLE
            profileDialog.visibility = View.VISIBLE

            profileBack.setOnClickListener {
                profileBack.visibility = View.GONE
                profileDialog.visibility = View.GONE
            }

        }

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        var isNameValid = false
        profileNextButton.isEnabled = false
        var isNoneValid = false



        profileNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                // 이름 2글자 이상인지 확인
                isNameValid = editable?.length ?: 0 >= 2

                if (isNameValid) {
                    profileNameEditText.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.et_green)
                } else {
                    profileNameEditText.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.et_gray)
                }
            }
        })

        profileNameCheckButton.setOnClickListener{
            if(isNameValid){
                //중복확인
                val service = ApiClient.retrofit.create(UserService::class.java)
                val dupRequest = OverlapNicknameRequestDto(user_nickname = profileNameEditText.text.toString())
                val call = service.overlapNickname(dupRequest)
                call.enqueue(object : Callback<OverlapNicknameResponseDto> {
                    override fun onResponse(call: Call<OverlapNicknameResponseDto>, response: Response<OverlapNicknameResponseDto>) {
                        if (response.isSuccessful) {
                            val signUpResponse = response.body()
                            if (signUpResponse?.isSuccess == false) {
                                profileNameIcon.visibility = View.INVISIBLE
                                profileNameDup.visibility = View.VISIBLE
                                profileNameEditText.backgroundTintList =
                                    ContextCompat.getColorStateList(requireContext(), R.color.et_red)
                                showToast("중복.")
                            } else {
                                profileNameIcon.visibility = View.VISIBLE
                                profileNameDup.visibility = View.INVISIBLE
                                sharedViewModel.profileName = profileNameEditText.text.toString()
                                profileNameEditText.backgroundTintList =
                                    ContextCompat.getColorStateList(requireContext(), R.color.et_green)
                                updateNextButtonState()
                                showToast("중복 아님")

                                val service = ApiClient.retrofit.create(UserService::class.java)
                                val signUpRequest = SignUpRequestDto(
                                    user_name = "", // 사용자 이름
                                    email = "",
                                    password = "", // 비밀번호
                                    phone_number = "", // 전화번호
                                    birth_date = "", // 생년월일
                                    gender = 0, // 성별 (0 또는 1)
                                    user_nickname = profileNameEditText.text.toString(), // 사용자 닉네임
                                    introduction = "", // 자기 소개
                                    role = "", // 역할
                                    agree = "" // 동의 여부
                                )
                                val call = service.signUp(signUpRequest)
                                call.enqueue(object : Callback<SignUpResponseDto> {
                                    override fun onResponse(call: Call<SignUpResponseDto>, response: Response<SignUpResponseDto>) {
                                        if (response.isSuccessful) {
                                            val signUpResponse = response.body()
                                            if (signUpResponse?.isSuccess == true) {
                                                // 서버에 이메일 전송 성공
                                                showToast("닉네임이 성공적으로 전송되었습니다.")
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

                        } else {
                            showToast("서버와의 통신에 실패하였습니다.")
                        }
                    }

                    override fun onFailure(call: Call<OverlapNicknameResponseDto>, t: Throwable) {
                        // 통신 오류
                        showToast("통신 오류: ${t.message}")
                    }
                })
            }
        }


    profileIntroEdittext.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(editable: Editable?) {

            isNoneValid = profileIntroEdittext.text.isNotEmpty()

            if (isNoneValid) {
                profileIntroEdittext.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.et_green)
            } else {
                profileIntroEdittext.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.et_gray)
            }

        }
    })

    return view
}

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    fun showToast(msg: String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
        toast?.show()
    }

    profileNextButton.setOnClickListener {
        if (profileNextButton.isEnabled) {

            //자기소개
            val service = ApiClient.retrofit.create(UserService::class.java)
            val signUpRequest = SignUpRequestDto(
                user_name = "", // 사용자 이름
                email = "",
                password = "", // 비밀번호
                phone_number = "", // 전화번호
                birth_date = "", // 생년월일
                gender = 0, // 성별 (0 또는 1)
                user_nickname = "", // 사용자 닉네임
                introduction = profileIntroEdittext.text.toString(), // 자기 소개
                role = "", // 역할
                agree = "" // 동의 여부
            )
            val call = service.signUp(signUpRequest)
            call.enqueue(object : Callback<SignUpResponseDto> {
                override fun onResponse(call: Call<SignUpResponseDto>, response: Response<SignUpResponseDto>) {
                    if (response.isSuccessful) {
                        val signUpResponse = response.body()
                        if (signUpResponse?.isSuccess == true) {
                            showToast("자기소개가 성공적으로 전송되었습니다.")
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

            val fragmentComplete = CompleteFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_set_profile, fragmentComplete)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}

private fun updateNextButtonState() {
    val isNameValid = (profileNameIcon.visibility == View.VISIBLE)

    if (isNameValid) {
        profileNextButton.setBackgroundResource(R.drawable.btnstyle)
        profileNextButton.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
        profileNextButton.isEnabled = true
    } else {
        profileNextButton.setBackgroundResource(R.drawable.uncheck_btnstyle)
        profileNextButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        profileNextButton.isEnabled = false
    }
}


}
