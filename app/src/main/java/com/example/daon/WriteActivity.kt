package com.example.daon

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.daon.databinding.ActivityWriteBinding

class WriteActivity : AppCompatActivity() {
    private val checkonResourceId = R.drawable.checkon
    private val checknoResourceId = R.drawable.checkno
    private var yeeFragment = YeeFragment()

    private lateinit var binding: ActivityWriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.writeBack.setOnClickListener {
            val currentResourceId = binding.accessCheck.tag as? Int ?: checknoResourceId

            if (currentResourceId == checkonResourceId) {
                // accessCheck 이미지가 checkon인 경우의 동작
                showConfirmationDialog()
            } else {
                // accessCheck 이미지가 checkon이 아닌 경우의 동작
                onBackPressedDispatcher.onBackPressed()
            }
        }

        binding.accessCheck.setOnClickListener {
            val title = binding.titleWr.text.toString()
            val detail = binding.detailWr.text.toString()

            val bundle = Bundle().apply {
                putString("title", title)
                putString("detail", detail)
            }
            yeeFragment.updateData(bundle)
            finish()
            Log.d("dsadad",title)
        }

        val maxCharacters = 500

        binding.titleWr.addTextChangedListener (object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (!p0.isNullOrEmpty()) {
                    binding.accessCheck.setImageResource(R.drawable.checkon)
                    binding.accessCheck.tag = R.drawable.checkon
                } else {
                    // 텍스트가 없다면 다시 기본 이미지로 변경
                    binding.accessCheck.setImageResource(R.drawable.checkno)
                    binding.accessCheck.tag = R.drawable.checkno
                }
            }
        })
        binding.detailWr.addTextChangedListener (object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(p0: Editable?) {
                val remainingCharacters = maxCharacters - p0.toString().length
                binding.maxtext.text = "$remainingCharacters/$maxCharacters"

                if (!p0.isNullOrEmpty()) {
                    binding.accessCheck.setImageResource(R.drawable.checkon)
                    binding.accessCheck.tag = R.drawable.checkon
                } else {
                    // 텍스트가 없다면 다시 기본 이미지로 변경
                    binding.accessCheck.setImageResource(R.drawable.checkno)
                    binding.accessCheck.tag = R.drawable.checkno
                }
            }
        })
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("")
            .setMessage("작성을 취소하시겠습니까?")
            .setPositiveButton("예") { _, _ ->
                onBackPressedDispatcher.onBackPressed()
            }
            .setNegativeButton("아니오") { _, _ ->
                // 아니오 버튼을 클릭했을 때의 동작
                // 예: 다이얼로그 닫기 또는 특정 동작 수행
            }
            .show()
    }
}