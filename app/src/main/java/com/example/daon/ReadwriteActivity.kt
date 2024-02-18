package com.example.daon

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.daon.data.community.*
import com.example.daon.data.community.token.PreferenceUtil
import com.example.daon.databinding.ActivityReadwriteBinding
import com.example.daon.databinding.ActivityWriteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadwriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadwriteBinding
    private lateinit var preferenceUtil: PreferenceUtil
    private var isLiked = false
    private var isLiked_book = false
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
        binding.favorBackground.setOnClickListener {
            if (isLiked) {
                // 이미 좋아요를 누른 상태이면 좋아요 취소 API 호출
                likeDown()
            } else {
                // 좋아요를 누른 상태가 아니면 좋아요 API 호출
                likeUp()
            }
        }
        binding.bookmarkBackground.setOnClickListener {
            if (isLiked_book) {
                // 이미 북마크를 누른 상태이면 북마크 취소 API 호출
                subScrape()
            } else {
                // 북마크를 누른 상태가 아니면 북마크 API 호출
                addScrape()
            }
        }

        binding.writeMore.setOnClickListener {
            // 수정 및 삭제 버튼 보이기
            binding.selectWriteCardView.visibility = View.VISIBLE
        }
        binding.editButton.setOnClickListener {
        //수정
        }
        binding.deleteButton.setOnClickListener {
            deletePost()
        }
        binding.root.setOnTouchListener { _, _ ->
            // 수정 및 삭제 버튼 숨기기
            binding.selectWriteCardView.visibility = View.GONE
            // 이벤트 소비 여부를 반환합니다.
            true
        }
        getPostDetails(boardId)
        getLikesForPost(boardId)
    }

    private fun getLikesForPost(boardId: Int) {
        val boardService = ApiClient.retrofit.create(BoardService::class.java)
        val call = boardService.getLikesForPost(boardId)
        call.enqueue(object : Callback<LikeResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                if (response.isSuccessful) {
                    val likeResponse = response.body()
                    if (likeResponse != null && likeResponse.isSuccess) {
                        val likeCount = likeResponse.result.count
                        binding.favorCount.text = "$likeCount"
                    } else {
                        Log.e(TAG, "Failed to get likes for post: ${likeResponse?.message}")
                    }
                } else {
                    Log.e(TAG, "Failed to get likes for post: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                Log.e(TAG, "Failed to get likes for post: ${t.message}")
            }
        })
    }

    private fun getPostDetails(boardId: Int) {
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
                        binding.favorCount.text =boardDetails.likecount.toString()
                        binding.bookmarkCount.text = boardDetails.scrapecount.toString()
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
    private fun addScrape() {
        val boardId = intent.getIntExtra("boardId", -1)
        val boardService = ApiClient.retrofit.create(BoardService::class.java)
        val call = boardService.addScrape(boardId)
        call.enqueue(object : Callback<ScrapeResponse> {
            override fun onResponse(call: Call<ScrapeResponse>, response: Response<ScrapeResponse>) {
                if (response.isSuccessful) {
                    val scrapeResponse = response.body()
                    if (scrapeResponse != null && scrapeResponse.isSuccess) {
                        // 스크랩 추가 성공 시 화면에 반영
                        updateScrapeCount(1)
                    } else {
                        Log.e(TAG, "Failed to add scrape: ${scrapeResponse?.message}")
                    }
                } else {
                    Log.e(TAG, "Failed to add scrape: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ScrapeResponse>, t: Throwable) {
                Log.e(TAG, "Failed to add scrape: ${t.message}")
            }
        })
    }

    private fun subScrape() {
        val boardId = intent.getIntExtra("boardId", -1)
        val boardService = ApiClient.retrofit.create(BoardService::class.java)
        val call = boardService.subScrape(boardId)
        call.enqueue(object : Callback<ScrapeResponse> {
            override fun onResponse(call: Call<ScrapeResponse>, response: Response<ScrapeResponse>) {
                if (response.isSuccessful) {
                    val scrapeResponse = response.body()
                    if (scrapeResponse != null && scrapeResponse.isSuccess) {
                        // 스크랩 삭제 성공 시 화면에 반영
                        updateScrapeCount(-1)
                    } else {
                        Log.e(TAG, "Failed to sub scrape: ${scrapeResponse?.message}")
                    }
                } else {
                    Log.e(TAG, "Failed to sub scrape: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ScrapeResponse>, t: Throwable) {
                Log.e(TAG, "Failed to sub scrape: ${t.message}")
            }
        })
    }
    private fun likeUp() {
        val boardId = intent.getIntExtra("boardId", -1)
        val boardService = ApiClient.retrofit.create(BoardService::class.java)
        val call = boardService.likeUp(boardId)
        call.enqueue(object : Callback<LikeResponse> {
            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                if (response.isSuccessful) {
                    // 좋아요 성공 시 좋아요 수를 업데이트하고 버튼의 상태를 변경
                    val likeResponse = response.body()
                    if (likeResponse != null && likeResponse.isSuccess) {
                        updateLikeCount(1)
                    } else {
                        Log.e(TAG, "Failed to like post: ${likeResponse?.message}")
                    }
                } else {
                    Log.e(TAG, "Failed to like post: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                Log.e(TAG, "Failed to like post: ${t.message}")
            }
        })
    }
    private fun likeDown() {
        val boardId = intent.getIntExtra("boardId", -1)
        val boardService = ApiClient.retrofit.create(BoardService::class.java)
        val call = boardService.likeDown(boardId)
        call.enqueue(object : Callback<LikeResponse> {
            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                if (response.isSuccessful) {
                    // 좋아요 취소 성공 시 좋아요 수를 업데이트하고 버튼의 상태를 변경
                    val likeResponse = response.body()
                    if (likeResponse != null && likeResponse.isSuccess) {
                        updateLikeCount(-1)
                    } else {
                        Log.e(TAG, "Failed to dislike post: ${likeResponse?.message}")
                    }
                } else {
                    Log.e(TAG, "Failed to dislike post: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                Log.e(TAG, "Failed to dislike post: ${t.message}")
            }
        })
    }
    private  fun deletePost() {
        val boardId = intent.getIntExtra("boardId", -1)
        val boardService = ApiClient.retrofit.create(BoardService::class.java)
        val call = boardService.deletePost(boardId)
        call.enqueue(object : Callback<PostWriteResponseDto> {
            override fun onResponse(
                call: Call<PostWriteResponseDto>,
                response: Response<PostWriteResponseDto>
            ) {
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
    @SuppressLint("SetTextI18n")
    private fun updateLikeCount(change: Int) {
        val currentLikeCount = binding.favorCount.text.toString().toInt()
        binding.favorCount.text =(currentLikeCount + change).toString()
        isLiked = !isLiked // 버튼의 상태 변경
    }
    @SuppressLint("SetTextI18n")
    private fun updateScrapeCount(change: Int) {
        val currentScrapeCount = binding.bookmarkCount.text.toString().toInt()
        binding.bookmarkCount.text = (currentScrapeCount + change).toString()
        isLiked_book = !isLiked_book
    }
}