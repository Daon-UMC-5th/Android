package com.example.daon

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.daon.data.community.ApiClient
import com.example.daon.data.community.BoardService
import com.example.daon.data.community.PostOneListResponseDto
import com.example.daon.data.community.PostWriteResponseDto
import com.example.daon.data.community.token.PreferenceUtil
import com.example.daon.databinding.ActivityReadwriteBinding
import com.example.daon.databinding.ActivityWriteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadwriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadwriteBinding
    private lateinit var preferenceUtil: PreferenceUtil
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadwriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.writeBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        preferenceUtil = PreferenceUtil(this)

        val boardId = intent.getIntExtra("boardId", -1)

        binding.commentBackground.setOnClickListener {
            val intent = Intent(this,CommentActivity::class.java)
            startActivity(intent)
        }

        binding.writeMore.setOnClickListener {
            // 수정 및 삭제 버튼 보이기
            binding.selectWriteCardView.visibility = View.VISIBLE
        }
        binding.editButton.setOnClickListener {

        }
        binding.deleteButton.setOnClickListener {
            val boardService = ApiClient.retrofit.create(BoardService::class.java)
            val call = boardService.deletePost(boardId)
            call.enqueue(object : Callback<PostWriteResponseDto> {
                override fun onResponse(call: Call<PostWriteResponseDto>, response: Response<PostWriteResponseDto>) {
                    if (response.isSuccessful) {
                        val postWriteResponse = response.body()
                        if (postWriteResponse != null && postWriteResponse.isSuccess) {
                            val bundle = Bundle()
                            bundle.putInt("deleteboardId", postWriteResponse.result.board_id)
                            bundle.putString("deleteboardType", postWriteResponse.result.board_type)
                            finish() // 현재 액티비티를 종료하여 이전 화면으로 돌아감
                        } else {
                            Log.e(TAG, "Failed to delete post: ${postWriteResponse?.message}")
                            // 삭제 실패 메시지를 표시하거나 다른 처리를 수행할 수 있습니다.
                        }
                    } else {
                        Log.e(TAG, "Failed to delete post: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<PostWriteResponseDto>, t: Throwable) {
                    Log.e(TAG, "Failed to delete post: ${t.message}")
                }
            })
        }
        binding.root.setOnTouchListener { _, _ ->
            // 수정 및 삭제 버튼 숨기기
            binding.selectWriteCardView.visibility = View.GONE
            // 이벤트 소비 여부를 반환합니다.
            true
        }

        val boardService = ApiClient.retrofit.create(BoardService::class.java)
        val call = boardService.getPost(boardId)
        call.enqueue(object : Callback<PostOneListResponseDto> {
            override fun onResponse(
                call: Call<PostOneListResponseDto>,
                response: Response<PostOneListResponseDto>
            ) {
                if (response.isSuccessful) {
                    val boardDetailsResponse = response.body()
                    if (boardDetailsResponse != null && boardDetailsResponse.isSuccess) {
                        val boardDetails = boardDetailsResponse.result
                        val dateString = boardDetails.created_at.substring(0, 10)
                        // 게시글 정보를 화면에 표시
                        binding.writeTitle.text = boardDetails.title
                        binding.writeDetail.text = boardDetails.content
                        binding.writeNickname.text = preferenceUtil.getUserNickname().toString()
                        binding.writeTime.text = dateString
                    } else {
                        Log.e(TAG, "Failed to get board details: ${boardDetailsResponse?.message}")
                    }
                } else {
                    Log.e(TAG, "Failed to get board details: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PostOneListResponseDto>, t: Throwable) {
                Log.e(TAG, "Failed to fetch board details: ${t.message}")
            }
        })
    }
}