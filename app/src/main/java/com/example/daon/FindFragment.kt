package com.example.daon

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.daon.conect.authorization.*
import com.example.daon.conect.user.*
import com.example.daon.data.community.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FindFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var birthEditText: EditText
    private lateinit var sexEditText: EditText
    private lateinit var tellEditText: EditText
    private lateinit var numEditText: EditText
    private lateinit var nextButton: Button
    private lateinit var certifyButton: ImageView
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var timerTextView: TextView
    private lateinit var textNum: ImageView
    private lateinit var numCheckYet: ImageView
    private lateinit var numCheckNot: ImageView
    private lateinit var numCheckButton: ImageView
    private lateinit var findCheckImageView: ImageView
    private lateinit var emailEditText: EditText
    private lateinit var numCertifyButton: ImageView
    private lateinit var endText: ImageView
    private lateinit var certifyNumButton: ImageView
    private lateinit var certifyNumEditText: EditText
    private lateinit var secretEditText: EditText
    private lateinit var noEngImageView: ImageView
    private lateinit var noNumImageView: ImageView
    private lateinit var noSpecialImageView: ImageView
    private lateinit var noRangeImageView: ImageView
    private lateinit var checkEditText: EditText
    private lateinit var noSameImageView: ImageView
    private lateinit var nextBtn: Button
    private lateinit var timer2TextView: TextView
    private lateinit var closeBtn: ImageView
    private lateinit var backBtn: ImageView
    private var toast: Toast? = null

    private var isTimerRunning = false
    private var isTimerRun = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_find, container, false)

        nameEditText = view.findViewById(R.id.find_name_et)
        birthEditText = view.findViewById(R.id.find_birth_et)
        sexEditText = view.findViewById(R.id.find_sex_et)
        tellEditText = view.findViewById(R.id.find_tell_et)
        numEditText = view.findViewById(R.id.find_num_et)
        nextButton = view.findViewById(R.id.find_next_btn)
        certifyButton = view.findViewById(R.id.find_certify_btn)
        timerTextView = view.findViewById(R.id.find_timer)
        textNum = view.findViewById(R.id.find_text_num)
        numCheckYet = view.findViewById(R.id.find_num_yet)
        numCheckNot = view.findViewById(R.id.find_check_not)
        numCheckButton = view.findViewById(R.id.find_num_check_btn)
        findCheckImageView = view.findViewById(R.id.find_check)
        emailEditText = view.findViewById(R.id.find_email_et)
        numCertifyButton = view.findViewById(R.id.find_email_certify_btn)
        endText = view.findViewById(R.id.end_text)
        certifyNumButton = view.findViewById(R.id.find_certify_num_btn)
        certifyNumEditText = view.findViewById(R.id.find_certify_num_et)
        secretEditText = view.findViewById(R.id.find_secret_et)
        noSpecialImageView = view.findViewById(R.id.no_special)
        noRangeImageView = view.findViewById(R.id.no_range)
        noEngImageView = view.findViewById(R.id.no_eng)
        noNumImageView = view.findViewById(R.id.no_num)
        checkEditText = view.findViewById(R.id.find_check_et)
        noSameImageView = view.findViewById(R.id.no_same)
        nextBtn = view.findViewById(R.id.find_num_next_btn)
        timer2TextView = view.findViewById(R.id.find_num_timer)
        closeBtn = view.findViewById(R.id.find_close_btn)
        backBtn = view.findViewById(R.id.find_back_btn)

        fun showToast(msg: String) {
            toast?.cancel()
            toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
            toast?.show()
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

        numEditText.isEnabled = false
        nextButton.isEnabled = false
        nextBtn.isEnabled = false

        view.findViewById<ImageView>(R.id.find_id_tab_on).setImageResource(R.drawable.find_id_tab_on)
        view.findViewById<ImageView>(R.id.find_num_tab_off).setImageResource(R.drawable.find_num_tab_off)

        var isIdTabActive = true

        view.findViewById<ImageView>(R.id.find_id_tab_on).setOnClickListener {
            if (isIdTabActive) return@setOnClickListener

            view.findViewById<FrameLayout>(R.id.find_id_page).visibility = View.VISIBLE
            view.findViewById<FrameLayout>(R.id.find_num_page).visibility = View.INVISIBLE
            view.findViewById<ImageView>(R.id.find_id_tab_on).setImageResource(R.drawable.find_id_tab_on)
            view.findViewById<ImageView>(R.id.find_num_tab_off).setImageResource(R.drawable.find_num_tab_off)

            isIdTabActive = true
        }

        view.findViewById<ImageView>(R.id.find_num_tab_off).setOnClickListener {
            if (!isIdTabActive) return@setOnClickListener

            view.findViewById<FrameLayout>(R.id.find_id_page).visibility = View.INVISIBLE
            view.findViewById<FrameLayout>(R.id.find_num_page).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.find_id_tab_on).setImageResource(R.drawable.find_id_tab_off)
            view.findViewById<ImageView>(R.id.find_num_tab_off).setImageResource(R.drawable.find_num_tab_on)

            isIdTabActive = false
        }

        tellEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                // 숫자 11자리 인지 확인
                val isTellValid = editable?.length ?: 0 == 11

                if (isTellValid) {
                    certifyButton.setOnClickListener {
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
                        certifyButton.setImageResource(R.drawable.self_done_btn)
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
                            queryPhone = numEditText.text.toString()
                        )
                        val call = service.smsCodeCheck(smsCodeCheckRequest)
                        call.enqueue(object : Callback<SmsCodeBodyResponseDto> {
                            override fun onResponse(call: Call<SmsCodeBodyResponseDto>, response: Response<SmsCodeBodyResponseDto>) {
                                if (response.isSuccessful) {
                                    val smsCodeCheckResponse = response.body()
                                    if (smsCodeCheckResponse?.isSuccess == true) {
                                        // 인증번호가 일치하는 경우
                                        findCheckImageView.visibility = View.VISIBLE
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
                    findCheckImageView.visibility = View.INVISIBLE
                    timerTextView.visibility = View.VISIBLE
                }
            }
        })

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if (isValidEmail(editable.toString())) {
                    emailEditText.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.et_green)
                } else {
                    emailEditText.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.et_gray)
                }
            }
        })

        numCertifyButton.setOnClickListener {
            if (isValidEmail(emailEditText.text.toString())) {
                endText.visibility = View.VISIBLE
                numCertifyButton.setImageResource(R.drawable.done_certify)

                if (!isTimerRun) {
                    startTimer2()
                }

                val service = ApiClient.retrofit.create(UserService::class.java)
                val signUpRequest = SignUpRequestDto(
                    user_name = "",
                    email = emailEditText.text.toString(),
                    password = "",
                    phone_number = "",
                    birth_date = "",
                    gender = 0,
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
                                // 서버에 이메일 전송 성공
                                showToast("이메일이 성공적으로 전송되었습니다.")
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

            val authService = ApiClient.retrofit.create(AuthorizationService::class.java)
            val emailRequest = EmailCodeListCallRequestDto(inputEmail= emailEditText.text.toString())
            val emailCall = authService.emailCode(emailRequest)
            emailCall.enqueue(object : Callback<EmailCodeListCallResponseDto> {
                override fun onResponse(call: Call<EmailCodeListCallResponseDto>, response: Response<EmailCodeListCallResponseDto>) {
                    if (response.isSuccessful) {
                        val emailCodeResponse = response.body()
                        if (emailCodeResponse?.isSuccess == true) {
                            // 이메일 인증번호 요청 성공
                            // 서버에서 받은 응답을 처리할 수 있음
                            showToast("이메일 인증번호를 성공적으로 요청했습니다.")
                        } else {
                            // 서버에서 실패 응답을 받음
                            showToast(emailCodeResponse?.message ?: "이메일 인증번호 요청 실패")
                        }
                    } else {
                        // 서버와의 통신 실패
                        showToast("이메일 인증번호 요청에 실패했습니다.")
                    }
                }

                override fun onFailure(call: Call<EmailCodeListCallResponseDto>, t: Throwable) {
                    // 통신 오류
                    showToast("통신 오류: ${t.message}")
                }
            })
        }

        certifyNumButton.setOnClickListener {
            val service = ApiClient.retrofit.create(AuthorizationService::class.java)
            val emailCodeCheckRequest = EmailCodeCheckRequestDto(
                inputCode = certifyNumEditText.text.toString(),
                queryEmail = certifyNumEditText.text.toString())
            val call = service.emailCodeCheck(emailCodeCheckRequest)
            call.enqueue(object : Callback<EmailCodeCheckResponseDto> {
                override fun onResponse(call: Call<EmailCodeCheckResponseDto>, response: Response<EmailCodeCheckResponseDto>) {
                    if (response.isSuccessful) {
                        val emailCodeCheckResponse = response.body()
                        if (emailCodeCheckResponse?.isSuccess == true) {
                            // 인증번호가 일치하는 경우
                            view.findViewById<ImageView>(R.id.find_num_text).visibility = View.VISIBLE
                            view.findViewById<ImageView>(R.id.find_num_not_equal_text).visibility = View.INVISIBLE
                            certifyNumEditText.backgroundTintList =
                                ContextCompat.getColorStateList(requireContext(), R.color.et_green)
                            certifyNumButton.setImageResource(R.drawable.join_done_certify)
                            showToast("이메일 인증번호 확인 성공")
                        } else {
                            // 인증번호가 일치하지 않는 경우
                            view.findViewById<ImageView>(R.id.find_num_text).visibility = View.INVISIBLE
                            view.findViewById<ImageView>(R.id.find_num_not_equal_text).visibility = View.VISIBLE
                            certifyNumEditText.backgroundTintList =
                                ContextCompat.getColorStateList(requireContext(), R.color.et_red)
                            showToast("이메일 인증번호가 일치하지 않습니다.")
                        }
                    } else {
                        // 서버와의 통신 실패
                        showToast("이메일 인증번호 확인에 실패했습니다.")
                    }
                }

                override fun onFailure(call: Call<EmailCodeCheckResponseDto>, t: Throwable) {
                    // 통신 오류
                    showToast("통신 오류: ${t.message}")
                }
            })
        }

        secretEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if (view.findViewById<ImageView>(R.id.find_num_text).visibility != View.VISIBLE) { 
                    secretEditText.text.clear()
                    showToast("이메일 인증 먼저")
                } else {
                    val password = editable.toString()
                    updatePasswordIndicators(password)
                }

            }
        })

        checkEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                if (!isPasswordValid(secretEditText.text.toString())) {
                    checkEditText.text.clear()
                    showToast("비밀번호 설정 먼저")
                } else {
                    val confirmPassword = editable.toString()
                    checkPasswordEquality(confirmPassword)
                }

            }
        })

        return view
    }

    private fun isValidEmail(email: String): Boolean {
        // 간단한 이메일 형식 및 .com으로 끝나는지 검증
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.com"
        return email.matches(emailRegex.toRegex())
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

    private fun startTimer2() {
        countDownTimer = object : CountDownTimer(180000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                val timerText = String.format("%02d:%02d", minutes, seconds)
                timer2TextView.text = timerText
            }

            override fun onFinish() {
                timer2TextView.text = "00:00"
            }
        }

        countDownTimer.start()
        isTimerRunning = true
    }

    private fun updateNextButtonState() {
        val isTellValid = isEditTextValid(tellEditText)
        val isNumValid = (findCheckImageView.visibility == View.VISIBLE)

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

    private fun updateNextButtonState2() {
        val isEmailValid = (endText.visibility == View.VISIBLE)
        val isPasswordValid = isPasswordValid(secretEditText.text.toString())
        val isPasswordMatch = (secretEditText.text.toString() == checkEditText.text.toString())
        val isNumValid = (view?.findViewById<ImageView>(R.id.find_num_text)?.visibility == View.VISIBLE)

        if (isEmailValid && isPasswordValid && isPasswordMatch && isNumValid) {
            nextBtn.setBackgroundResource(R.drawable.btnstyle)
            nextBtn.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            nextBtn.isEnabled = true
        } else {
            nextBtn.setBackgroundResource(R.drawable.uncheck_btnstyle)
            nextBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            nextBtn.isEnabled = false
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

                val service = ApiClient.retrofit.create(UserService::class.java)
                val findIdRequest = FindIdListCallRequestDto(
                    user_name = nameEditText.text.toString(),
                    birth_date = nameEditText.text.toString(),
                    gender = sexEditText.text.toString().toIntOrNull() ?: 0,
                    phone_number = tellEditText.text.toString()
                )
                val call = service.findId(findIdRequest)
                call.enqueue(object : Callback<FindIdListCallResponseDto> {
                    override fun onResponse(call: Call<FindIdListCallResponseDto>, response: Response<FindIdListCallResponseDto>) {
                        if (response.isSuccessful) {
                            val findIdResponse = response.body()
                            if (findIdResponse?.isSuccess == true) {
                                val yesResultFragment = YesResultFragment()
                                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                                transaction.replace(R.id.find_id_page, yesResultFragment)
                                transaction.addToBackStack(null)
                                transaction.commit()
                            } else {
                                val noResultFragment = NoResultFragment()
                                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                                transaction.replace(R.id.find_id_page, noResultFragment)
                                transaction.addToBackStack(null)
                                transaction.commit()
                            }
                        } else {
                            showToast("서버와의 통신에 실패하였습니다.")
                        }
                    }
                    override fun onFailure(call: Call<FindIdListCallResponseDto>, t: Throwable) {
                        showToast("통신 오류: ${t.message}")
                    }
                })
            }
        }

        if(nextBtn.isEnabled){

            val service = ApiClient.retrofit.create(UserService::class.java)
            val findPwRequest = FindPwRequestDto(
                email = emailEditText.text.toString(),
                password = checkEditText.text.toString()
            )
            val call = service.findPw(findPwRequest)
            call.enqueue(object : Callback<FindPwResponseDto> {
                override fun onResponse(call: Call<FindPwResponseDto>, response: Response<FindPwResponseDto>) {
                    if (response.isSuccessful) {
                        val findPwResponse = response.body()
                        if (findPwResponse?.isSuccess == true) {
                            val dialogFragment = FindDialogFragment()
                            dialogFragment.show(requireActivity().supportFragmentManager, "find_dialog")
                        } else {
                            showToast("서버와의 통신에 실패하였습니다.")
                        }
                    } else {
                        showToast("서버와의 통신에 실패하였습니다.")
                    }
                }
                override fun onFailure(call: Call<FindPwResponseDto>, t: Throwable) {
                    showToast("통신 오류: ${t.message}")
                }
            })

        }

    }

    private fun isEditTextValid(editText: EditText): Boolean {
        return editText.backgroundTintList?.defaultColor == ContextCompat.getColorStateList(
            requireContext(),
            R.color.et_green
        )?.defaultColor
    }

    private fun updatePasswordIndicators(password: String) {
        val hasUpperCase = password.matches(".*[A-Z].*".toRegex())
        val hasNumber = password.matches(".*\\d.*".toRegex())
        val hasSpecialChar = password.matches(".*[!@#\$%^&*()_+\\-=\\[\\]{};':\",.<>/?].*".toRegex())
        val isLengthValid = (password.length in 8..20)
        var upper = false
        var number = false
        var special = false
        var length = false

        if (hasUpperCase) {
            noEngImageView.setImageResource(R.drawable.yes_eng)
            upper = true
        } else {
            noEngImageView.setImageResource(R.drawable.no_eng)
            upper = false
        }

        if (hasNumber) {
            noNumImageView.setImageResource(R.drawable.yes_num)
            number = true
        } else {
            noNumImageView.setImageResource(R.drawable.no_num)
            number = false
        }

        if (hasSpecialChar) {
            noSpecialImageView.setImageResource(R.drawable.yes_special)
            special = true
        } else {
            noSpecialImageView.setImageResource(R.drawable.no_special)
            special = false
        }

        if (isLengthValid) {
            noRangeImageView.setImageResource(R.drawable.yes_range)
            length = true
        } else {
            noRangeImageView.setImageResource(R.drawable.no_range)
            length = false
        }

        if(upper && number && special && length){
            secretEditText.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.et_green)
        }
        else{
            secretEditText.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.et_gray)
        }

    }

    private fun isPasswordValid(password: String): Boolean {
        val hasUpperCase = password.matches(".*[A-Z].*".toRegex())
        val hasNumber = password.matches(".*\\d.*".toRegex())
        val hasSpecialChar = password.matches(".*[!@#\$%^&*()_+\\-=\\[\\]{};':\",.<>/?].*".toRegex())
        val isLengthValid = (password.length in 8..20)

        return hasUpperCase && hasNumber && hasSpecialChar && isLengthValid
    }

    private fun checkPasswordEquality(confirmPassword: String) {
        val password = secretEditText.text.toString()

        if (password == confirmPassword) {
            noSameImageView.setImageResource(R.drawable.yes_same)
            checkEditText.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.et_green)
        } else {
            noSameImageView.setImageResource(R.drawable.no_same)
            checkEditText.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.et_gray)
        }
        updateNextButtonState2()
    }

}


