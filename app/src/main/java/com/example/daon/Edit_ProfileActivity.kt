package com.example.daon

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.daon.databinding.ActivityEditProfileBinding
import com.example.daon.mypage_api.ProfilechangeRequestDto
import com.example.daon.mypage_api.ProfilechangeResponseDto
import com.example.daon.data.community.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class Edit_ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageBitmap = data?.extras?.get("data") as Bitmap?
            imageBitmap?.let {  binding.profileImage.setImageBitmap(imageBitmap) }
        } else {
            // 사용자가 카메라를 취소한 경우 또는 오류가 발생한 경우 처리
        }
    }
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            try {
                val inputStream = contentResolver.openInputStream(uri)
                val selectedBitmap = BitmapFactory.decodeStream(inputStream)
                binding.profileImage.setImageBitmap(selectedBitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    private lateinit var defaultBitmap: Bitmap
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        defaultBitmap = BitmapFactory.decodeResource(resources, R.drawable.profile)
        binding.profileImage.setImageBitmap(defaultBitmap)

        binding.editNickname.hint = "홍길동"
        binding.editIntro.hint = "안녕하세요. 의료인으로서 열심히 답해드릴게요!"

        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.juung.setOnClickListener {
            binding.juung.setTextColor(R.color.gray)
            binding.juung.text = "확인완료"
            binding.nickFree.visibility = View.VISIBLE
        }

        binding.editNickname.addTextChangedListener {
            updateSaveButtonState()
        }

        binding.editIntro.addTextChangedListener {
            updateSaveButtonState()
        }

        binding.save.setOnClickListener {
            val nickname = binding.editNickname.text.toString()
            val intro = binding.editIntro.text.toString()

            val profileChangeRequestDto = ProfilechangeRequestDto(nickname, intro)

            val call = com.example.daon.data.community.ApiClient.Mypageservice.changeProfile(profileChangeRequestDto)
            call.enqueue(object : Callback<ProfilechangeResponseDto> {
                override fun onResponse(call: Call<ProfilechangeResponseDto>, response: Response<ProfilechangeResponseDto>) {
                    if (response.isSuccessful) {
                        val intent = Intent(this@Edit_ProfileActivity, MainActivity::class.java)

                        intent.putExtra("nickname", nickname)
                        intent.putExtra("intro", intro)
                        startActivity(intent)
                        finish()
                    } else {
                        // Handle error response
                        // For example, show an error message to the user
                        Toast.makeText(this@Edit_ProfileActivity, "서버 에러, 관리자에게 문의 바랍니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ProfilechangeResponseDto>, t: Throwable) {
                    // Handle failure
                    // For example, show a network error message to the user
                    Toast.makeText(this@Edit_ProfileActivity, "Network error", Toast.LENGTH_SHORT).show()
                }
            })
        }


        binding.profileImage.setOnClickListener {
            Log.d("asdsda","aaa")
            val currentDrawable = (binding.profileImage.drawable as? BitmapDrawable)?.bitmap
            val defaultDrawable = BitmapFactory.decodeResource(resources, R.drawable.profile)
            if (currentDrawable != null && currentDrawable.sameAs(defaultDrawable)) {
                onsnackbar()
            }
            else{
                onsnackbar2()
            }
            binding.background.setOnClickListener {
                backsnackbar()
            }
        }
        binding.profileImage.isClickable = true
        binding.takePicture2.setOnClickListener {
            openCamera()
            backsnackbar()
        }
        binding.selectAlbum2.setOnClickListener {
            openGallery()
            backsnackbar()
        }
        binding.takePicture.setOnClickListener {
            openCamera()
            backsnackbar()
        }
        binding.selectAlbum.setOnClickListener {
            openGallery()
            backsnackbar()
        }
        binding.delectAlbum.setOnClickListener {
            binding.profileImage.setImageBitmap(defaultBitmap)
            backsnackbar()
        }
    }


    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun onsnackbar2() {
        binding.background.visibility = View.VISIBLE
        ObjectAnimator.ofFloat(binding.selectLockCardView, "translationY", -202f).apply { start() }
        binding.save.visibility = View.GONE
    }

    private fun onsnackbar() {
            binding.background.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(binding.selectPictureCardView, "translationY", -145f)
                .apply { start() }
            binding.save.visibility = View.GONE
        }

        private fun backsnackbar() {
            binding.background.visibility = View.GONE
            ObjectAnimator.ofFloat(binding.selectPictureCardView, "translationY", 0f)
                .apply { start() }
            binding.save.visibility = View.VISIBLE
        }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // 카메라 권한이 없을 경우 권한 요청 다이얼로그 표시
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA),
                Companion.CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                cameraLauncher.launch(takePictureIntent)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Companion.CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 사용자가 카메라 권한을 승인한 경우 카메라 앱 실행
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePictureIntent, Companion.REQUEST_IMAGE_CAPTURE)
                } else {
                    // 사용자가 권한을 거부한 경우 메시지 또는 다른 작업 수행
                    Toast.makeText(this, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
        @SuppressLint("ResourceAsColor")
        private fun updateSaveButtonState() {
            val isNicknameNotEmpty = binding.editNickname.text.isNotEmpty()
            val isIntroNotEmpty = binding.editIntro.text.isNotEmpty()
            val isNickFreeVisible = binding.nickFree.visibility == View.VISIBLE

            if (isNicknameNotEmpty && isIntroNotEmpty &&  isNickFreeVisible) {
                binding.save.setBackgroundResource(R.drawable.btn_023)
                binding.save.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.save.isEnabled = true
            } else {
                binding.save.setBackgroundResource(R.drawable.btn_01)
                binding.save.setTextColor(R.color.gray)
                binding.save.isEnabled = false
            }
        }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }
}