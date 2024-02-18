package com.example.daon

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import com.example.daon.R
import com.example.daon.conect.ApiClient
import com.example.daon.conect.user.LoginRequestDto
import com.example.daon.conect.user.LoginResponseDto
import com.example.daon.conect.user.SignUpRequestDto
import com.example.daon.conect.user.SignUpResponseDto
import com.example.daon.conect.user.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var loginEmailEditText: EditText
    private lateinit var loginSecretEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var nextButton: Button
    private lateinit var findImageView: TextView
    private lateinit var notImageView: ImageView
    private lateinit var kakaoBtn: ImageView
    private var toast: Toast? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        loginEmailEditText = view.findViewById(R.id.login_email_et)
        loginSecretEditText = view.findViewById(R.id.login_secret_et)
        loginButton = view.findViewById(R.id.login_btn)
        findImageView = view.findViewById(R.id.login_find_secret)
        nextButton = view.findViewById(R.id.login_btn)
        notImageView = view.findViewById(R.id.profile_not_equal_text)
        kakaoBtn = view.findViewById(R.id.login_kakao_btn)

        // 이메일 형식 체크
        loginEmailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()
                checkLoginButtonState(isValidEmail, isPasswordValid(loginSecretEditText.text.toString()))
            }
        })

        kakaoBtn.setOnClickListener {
            val url = "http://15.164.2.250/login/kakao"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        // 비밀번호 형식 체크
        loginSecretEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val isValidPassword = isPasswordValid(s.toString())
                checkLoginButtonState(android.util.Patterns.EMAIL_ADDRESS.matcher(loginEmailEditText.text).matches(), isValidPassword)
            }
        })

        findImageView.setOnClickListener {
            val findFragment = FindFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_login, findFragment)
            transaction.addToBackStack(null)
            transaction.commit()

        }

        return view
    }


    private fun isPasswordValid(password: String): Boolean {
        return password.length in 8..20 && password.matches(Regex(".*[A-Z].*")) &&
                password.matches(Regex(".*[!@#\$%^&*()_+\\-=\\[\\]{};':\",.<>/?].*")) && password.matches(Regex(".*\\d.*"))
    }

    private fun checkLoginButtonState(isValidEmail: Boolean, isValidPassword: Boolean) {
        if (isValidEmail && isValidPassword) {
            loginButton.setBackgroundResource(R.drawable.btnstyle)
            loginButton.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            loginButton.isEnabled = true
        } else {
            loginButton.setBackgroundResource(R.drawable.uncheck_btnstyle)
            loginButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            loginButton.isEnabled = false
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
                val loginRequest = LoginRequestDto(
                    email = loginEmailEditText.text.toString(),
                    password = loginSecretEditText.text.toString()
                )
                val call = service.login(loginRequest)
                call.enqueue(object : Callback<LoginResponseDto> {
                    override fun onResponse(call: Call<LoginResponseDto>, response: Response<LoginResponseDto>) {
                        if (response.isSuccessful) {
                            val signUpResponse = response.body()
                            if (signUpResponse?.isSuccess == true) {
                                showToast("로그인 성공")
                                //메인으로 이동하는 코드 추가하기
                            } else {
                                loginEmailEditText.backgroundTintList =
                                    ContextCompat.getColorStateList(requireContext(), R.color.et_red)
                                loginSecretEditText.backgroundTintList =
                                    ContextCompat.getColorStateList(requireContext(), R.color.et_red)
                                notImageView.visibility = View.VISIBLE
                            }
                        } else {
                            showToast("서버와의 통신에 실패하였습니다.")
                        }
                    }

                    override fun onFailure(call: Call<LoginResponseDto>, t: Throwable) {
                        showToast("통신 오류: ${t.message}")
                    }
                })
            }
        }
    }

}