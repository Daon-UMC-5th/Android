package com.example.daon

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.daon.R
import com.example.daon.conect.ApiClient
import com.example.daon.conect.authorization.AuthorizationService
import com.example.daon.conect.authorization.EmailCodeCheckRequestDto
import com.example.daon.conect.authorization.EmailCodeCheckResponseDto
import com.example.daon.conect.authorization.SmsCodeBodyRequestDto
import com.example.daon.conect.authorization.SmsCodeBodyResponseDto
import com.example.daon.conect.authorization.SmsCodeListCallRequestDto
import com.example.daon.conect.authorization.SmsCodeListCallResponseDto
import com.example.daon.conect.user.SignUpRequestDto
import com.example.daon.conect.user.SignUpResponseDto
import com.example.daon.conect.user.UserService
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class SelfCertifyFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var birthEditText: EditText
    private lateinit var sexEditText: EditText
    private lateinit var tellEditText: EditText
    private lateinit var numEditText: EditText
    private lateinit var nextButton: Button
    private lateinit var selfCertifyBtn: ImageView
    private lateinit var selfCertifyLayout: ConstraintLayout
    private lateinit var timerTextView: TextView
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var selfCheckImageView: ImageView
    private lateinit var textNum: ImageView
    private lateinit var numCheckButton: ImageView
    private lateinit var numCheckYet: ImageView
    private lateinit var numCheckNot: ImageView
    private var toast: Toast? = null

    private var isTimerRunning = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_self_certify, container, false)

        nameEditText = view.findViewById(R.id.self_name_et)
        birthEditText = view.findViewById(R.id.self_birth_et)
        sexEditText = view.findViewById(R.id.self_sex_et)
        tellEditText = view.findViewById(R.id.self_tell_et)
        numEditText = view.findViewById(R.id.self_num_et)
        nextButton = view.findViewById(R.id.self_next_btn)
        selfCertifyBtn = view.findViewById(R.id.self_certify_btn)
        selfCertifyLayout = view.findViewById(R.id.fragment_self_certify)
        timerTextView = view.findViewById(R.id.self_timer)
        selfCheckImageView = view.findViewById(R.id.self_check)
        textNum = view.findViewById(R.id.self_text_num)
        numCheckButton = view.findViewById(R.id.self_num_check_btn)
        numCheckYet = view.findViewById(R.id.self_num_yet)
        numCheckNot = view.findViewById(R.id.num_check_not)

        fun showToast(msg: String) {
            toast?.cancel()
            toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
            toast?.show()
        }

        numEditText.isEnabled = false
        nextButton.isEnabled = false

        tellEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                // 숫자 11자리 인지 확인
                val isTellValid = editable?.length ?: 0 == 11

                if (isTellValid) {
                    selfCertifyBtn.setOnClickListener {
                        if (!isTimerRunning) {
                            startTimer()
                        }
                        //전화번호
                        val authService = ApiClient.retrofit.create(AuthorizationService::class.java)
                        val smsRequest = SmsCodeListCallRequestDto(inputPhone= tellEditText.text.toString())
                        val smsCall = authService.smsCode(smsRequest)
                        smsCall.enqueue(object : Callback<SmsCodeListCallResponseDto> {
                            override fun onResponse(call: Call<SmsCodeListCallResponseDto>, response: Response<SmsCodeListCallResponseDto>) {
                                if (response.isSuccessful) {
                                    val smsResponse = response.body()
                                    if (smsResponse?.isSuccess == true) {
                                        showToast("전화번호 인증번호를 성공적으로 요청했습니다.")
                                    } else {
                                        // 서버에서 실패 응답을 받음
                                        showToast(smsResponse?.message ?: "전화번호 인증번호 요청 실패")
                                    }
                                } else {
                                    // 서버와의 통신 실패
                                    showToast("전화번호 인증번호 요청에 실패했습니다.")
                                }
                            }

                            override fun onFailure(call: Call<SmsCodeListCallResponseDto>, t: Throwable) {
                                // 통신 오류
                                showToast("통신 오류: ${t.message}")
                            }
                        })
                        selfCertifyBtn.setImageResource(R.drawable.self_done_btn)
                        numEditText.isEnabled = true
                        textNum.visibility = View.VISIBLE
                        numCheckYet.visibility = View.VISIBLE
                    }
                    tellEditText.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.et_green)
                } else {
                    tellEditText.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.et_gray)
                }
            }
        })

        numEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {

                val isNumValid = editable?.length ?: 0 == 6

                if (isNumValid) {
                    numCheckButton.setOnClickListener {
                        //인증번호 확인
                        val service = ApiClient.retrofit.create(AuthorizationService::class.java)
                        val smsCodeCheckRequest = SmsCodeBodyRequestDto(
                            inputCode = numEditText.text.toString(),
                            queryPhone = tellEditText.text.toString())
                        val call = service.smsCodeCheck(smsCodeCheckRequest)
                        call.enqueue(object : Callback<SmsCodeBodyResponseDto> {
                            override fun onResponse(call: Call<SmsCodeBodyResponseDto>, response: Response<SmsCodeBodyResponseDto>) {
                                if (response.isSuccessful) {
                                    val smsCodeCheckResponse = response.body()
                                    if (smsCodeCheckResponse?.isSuccess == true) {
                                        // 인증번호가 일치하는 경우
                                        selfCheckImageView.visibility = View.VISIBLE
                                        timerTextView.visibility = View.INVISIBLE
                                        numCheckButton.setImageResource(R.drawable.self_done_num_check_)
                                        numCheckYet.visibility = View.INVISIBLE
                                        updateNextButtonState()
                                        showToast("인증번호 확인 성공")
                                    } else {
                                        // 인증번호가 일치하지 않는 경우
                                        numCheckYet.visibility = View.INVISIBLE
                                        numCheckNot.visibility = View.VISIBLE
                                        numEditText.backgroundTintList =
                                            ContextCompat.getColorStateList(requireContext(), R.color.et_red)
                                        showToast("인증번호가 일치하지 않습니다.")
                                    }
                                } else {
                                    // 서버와의 통신 실패
                                    showToast("이메일 인증번호 확인에 실패했습니다.")
                                }
                            }

                            override fun onFailure(call: Call<SmsCodeBodyResponseDto>, t: Throwable) {
                                // 통신 오류
                                showToast("통신 오류: ${t.message}")
                            }
                        })
                    }
                    numEditText.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.et_green)
                } else {
                    numEditText.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.et_gray)
                    selfCheckImageView.visibility = View.INVISIBLE
                    timerTextView.visibility = View.VISIBLE
                }
            }
        })

        return view
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(180000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                val timerText = String.format("%02d:%02d", minutes, seconds)
                timerTextView.text = timerText
            }

            override fun onFinish() {
                timerTextView.text = "00:00"
            }
        }

        countDownTimer.start()
        isTimerRunning = true
    }

    private fun updateNextButtonState() {
        val isTellValid = isEditTextValid(tellEditText)
        val isNumValid = (selfCheckImageView.visibility == View.VISIBLE)

        if (isTellValid && isNumValid) {
            nextButton.setBackgroundResource(R.drawable.btnstyle)
            nextButton.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            nextButton.isEnabled = true

        } else {
            nextButton.setBackgroundResource(R.drawable.uncheck_btnstyle)
            nextButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            nextButton.isEnabled = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun showToast(msg: String) {
            toast?.cancel()
            toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
            toast?.show()
        }

        nextButton.setOnClickListener {
            if (nextButton.isEnabled) {
                //이름 & 생년월일 & 성별
                val service = ApiClient.retrofit.create(UserService::class.java)
                val signUpRequest = SignUpRequestDto(
                    user_name = nameEditText.text.toString(),
                    email = "",
                    password = "",
                    phone_number = "",
                    birth_date = nameEditText.text.toString(),
                    gender = sexEditText.text.toString().toIntOrNull() ?: 0,
                    user_nickname = "",
                    introduction = "",
                    role = "",
                    agree = ""
                )
                val call = service.signUp(signUpRequest)
                call.enqueue(object : Callback<SignUpResponseDto> {
                    override fun onResponse(call: Call<SignUpResponseDto>, response: Response<SignUpResponseDto>) {
                        if (response.isSuccessful) {
                            val signUpResponse = response.body()
                            if (signUpResponse?.isSuccess == true) {
                                showToast("성공적으로 전송되었습니다.")
                            } else {
                                showToast(signUpResponse?.message ?: "Unknown error")
                            }
                        } else {
                            showToast("서버와의 통신에 실패하였습니다.")
                        }
                    }

                    override fun onFailure(call: Call<SignUpResponseDto>, t: Throwable) {
                        showToast("통신 오류: ${t.message}")
                    }
                })

                val setProfileFragment = SetProfileFragment()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_self_certify, setProfileFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }

    private fun isEditTextValid(editText: EditText): Boolean {
        return editText.backgroundTintList?.defaultColor == ContextCompat.getColorStateList(
            requireContext(),
            R.color.et_green
        )?.defaultColor
    }
}