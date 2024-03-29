package com.example.daon

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.daon.conect.user.*
import com.example.daon.data.community.ApiClient
import com.example.daon.data.community.token.PreferenceUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SetProfileFragment : Fragment() {

    private lateinit var preferenceUtil: PreferenceUtil
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
    private lateinit var closeBtn: ImageView
    private lateinit var backBtn: ImageView
    private var toast: Toast? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_set_profile, container, false)

        preferenceUtil = PreferenceUtil(requireContext())

        profileNameEditText = view.findViewById(R.id.profile_name_et)
        profileNameCheckButton = view.findViewById(R.id.profile_name_check)
        profileNameIcon = view.findViewById(R.id.profile_name_iv)
        profileNextButton = view.findViewById(R.id.profile_next_btn)
        profileImg = view.findViewById(R.id.profile_img)
        profileIntroEdittext = view.findViewById(R.id.profile_intro_et)
        profileBack = view.findViewById(R.id.profile_back)
        profileDialog = view.findViewById(R.id.profile_dialog)
        profileNameDup = view.findViewById(R.id.profile_name_duplication)
        closeBtn = view.findViewById(R.id.profile_close_btn)
        backBtn = view.findViewById(R.id.profile_back_btn)

        fun showToast(msg: String) {
            toast?.cancel()
            toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
            toast?.show()
        }

        closeBtn.setOnClickListener {
            val intent = Intent(context, StartActivity::class.java)
            startActivity(intent)

            requireActivity().finish()
        }

        backBtn.setOnClickListener {
            val selfCertifyFragment = SelfCertifyFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_set_profile, selfCertifyFragment)
            transaction.addToBackStack(null)
            transaction.commit()
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
               // val service = ApiClient.retrofit.create(UserService::class.java)
                //val dupRequest = OverlapNicknameRequestDto(user_nickname = profileNameEditText.text.toString())
                //val call = service.overlapNickname(dupRequest)
                //call.enqueue(object : Callback<OverlapNicknameResponseDto> {
                    //override fun onResponse(call: Call<OverlapNicknameResponseDto>, response: Response<OverlapNicknameResponseDto>) {
                        //if (response.isSuccessful) {
                            //val signUpResponse = response.body()
                            //if (signUpResponse?.isSuccess == true) {
                                profileNameIcon.visibility = View.VISIBLE
                                profileNameDup.visibility = View.INVISIBLE
                                sharedViewModel.profileName = profileNameEditText.text.toString()
                                profileNameEditText.backgroundTintList =
                                    ContextCompat.getColorStateList(requireContext(), R.color.et_green)
                                updateNextButtonState()
                                //showToast("중복 아님")
                            //} else {
                                //profileNameIcon.visibility = View.INVISIBLE
                                //profileNameDup.visibility = View.VISIBLE
                                //profileNameEditText.backgroundTintList =
                                    //ContextCompat.getColorStateList(requireContext(), R.color.et_red)
                                //showToast("중복.")
                            //}

                        //} else {
                            //showToast("서버와의 통신에 실패하였습니다.")
                        //}
                    //}

                    //override fun onFailure(call: Call<OverlapNicknameResponseDto>, t: Throwable) {
                        // 통신 오류
                        //showToast("통신 오류: ${t.message}")
                    //}
                //})
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
                user_name = "eunjae",
                email = "emma_0407@naver.com",
                password = "Eunjae0407!",
                phone_number = "01039537851",
                birth_date = "030407",
                gender = 4,
                user_nickname = "eun",
                introduction = "hi",
                role = "user",
                agree = "1"
            )
            val call = service.signUp(signUpRequest)
            call.enqueue(object : Callback<SignUpResponseDto> {
                override fun onResponse(call: Call<SignUpResponseDto>, response: Response<SignUpResponseDto>) {
                    if (response.isSuccessful) {
                        val signUpResponse = response.body()
                        if (signUpResponse?.isSuccess == true) {
                            val nickname = profileNameEditText.text.toString()
                            val intro = profileIntroEdittext.text.toString()
                            preferenceUtil.saveUserNickname(nickname)
                            preferenceUtil.saveUserIntro(intro)
                        } else {
                            // 서버에서 실패 응답을 받음
                        }
                    } else {
                        // 서버와 통신 실패
                    }
                }

                override fun onFailure(call: Call<SignUpResponseDto>, t: Throwable) {
                    // 통신 오류
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
