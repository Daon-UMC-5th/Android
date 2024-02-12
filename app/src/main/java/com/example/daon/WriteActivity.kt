package com.example.daon

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.daon.community.ApiClient
import com.example.daon.community.BoardService
import com.example.daon.community.PostWriteRequestDto
import com.example.daon.community.PostWriteResponseDto
import com.example.daon.databinding.ActivityWriteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteActivity : AppCompatActivity() {
    private val checkonResourceId = R.drawable.checkon
    private val checknoResourceId = R.drawable.checkno
    private var yeeFragment = YeeFragment()

    private lateinit var binding: ActivityWriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val boardType = intent.getStringExtra("boardType")?:""

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

            writePost(boardType, title, detail)
            finish()
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
    private fun writePost(boardType: String, title: String, detail: String) {
        val requestDto = PostWriteRequestDto(title, detail)

        val call = ApiClient.boardService.writePost(boardType, requestDto)
        call.enqueue(object : Callback<PostWriteResponseDto> {
            override fun onResponse(
                call: Call<PostWriteResponseDto>,
                response: Response<PostWriteResponseDto>
            ) {
                if (response.isSuccessful) {
                    val postWriteResponse = response.body()
                    if (postWriteResponse != null && postWriteResponse.isSuccess) {
                        // 게시글 작성 성공
                        val newPost = postWriteResponse.result
                        val newPostData  = YeeData(
                            nickname = "권혁찬", // 닉네임 설정 필요
                             detail = newPost.content,
                            title = newPost.title,
                            timeAgo = newPost.created_at,
                            profileImage = R.drawable.calendar, // 프로필 이미지 설정 필요
                            favorIcon = R.drawable.calendar, // 좋아요 아이콘 설정 필요
                            favorCount = newPost.likecount.toString(),
                            commentIcon = R.drawable.calendar, // 댓글 아이콘 설정 필요
                            commentCount = newPost.commentcount.toString(),
                            bookmarkIcon = R.drawable.calendar, // 북마크 아이콘 설정 필요
                            bookmarkCount = newPost.scrapecount.toString()
                        )
                        yeeFragment.addNewPost(newPostData )
                    } else {
                        // 게시글 작성 실패
                        Log.e(TAG, "Failed to write post: ${postWriteResponse?.message}")
                    }
                } else {
                    Log.e(TAG, "Failed to write post: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PostWriteResponseDto>, t: Throwable) {
                Log.e(TAG, "Failed to write post: ${t.message}")
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