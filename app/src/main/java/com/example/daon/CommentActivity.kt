package com.example.daon

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.Adapter.CommentData
import com.example.daon.Adapter.CommentRVAdapter
import com.example.daon.data.community.ApiClient
import com.example.daon.data.community.BoardService
import com.example.daon.data.community.CommentRequestDto
import com.example.daon.data.community.CommentResponseDto
import com.example.daon.data.community.token.PreferenceUtil
import com.example.daon.databinding.ActivityCommentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentActivity : AppCompatActivity(), CommentRVAdapter.OnMoreButtonClickListener {

    private lateinit var preferenceUtil: PreferenceUtil
    private lateinit var datas: ArrayList<CommentData>
    private lateinit var CommentRVAdapter: CommentRVAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<CommentData>

    private lateinit var binding : ActivityCommentBinding
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceUtil = PreferenceUtil(this)

        val boardId = intent.getIntExtra("boardid", -1)

        recyclerView = binding.commentRV
        datas = getYourDataList()
        CommentRVAdapter = CommentRVAdapter(datas)
        dataList = getYourDataList()
        binding.commentRV.layoutManager = LinearLayoutManager(this)
        binding.commentRV.adapter = CommentRVAdapter

        loadComments(boardId)
        //댓글 작성
        binding.commentEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val content = binding.commentEdit.text.toString().trim()
                if (content.isNotEmpty()) {
                    // 댓글 작성 API 호출
                    postComment(boardId, content)
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.commentBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.commentEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 텍스트 변경 전에 호출됩니다. 여기서는 사용하지 않습니다.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 변경될 때마다 호출됩니다. 여기서는 사용하지 않습니다.
            }

            override fun afterTextChanged(s: Editable?) {
                // 텍스트가 변경된 후에 호출됩니다.
                val text = s.toString()
                if (text.isNotEmpty()) {
                    binding.commentDel.visibility = View.VISIBLE
                } else {
                    binding.commentDel.visibility = View.GONE
                }
            }
        })
        binding.commentDel.setOnClickListener {
            binding.commentEdit.text = null
        }
    }

    private fun loadComments(boardId: Int) {
        val boardService = ApiClient.retrofit.create(BoardService::class.java)
        val call = boardService.getComments(boardId, 0)
        call.enqueue(object : Callback<List<CommentResponseDto>> {
            override fun onResponse(call: Call<List<CommentResponseDto>>, response: Response<List<CommentResponseDto>>) {
                if (response.isSuccessful) {
                    val commentResponseList = response.body()
                    if (commentResponseList != null) {
                        val commentDataList = ArrayList<CommentData>()
                        for (commentResponse in commentResponseList) {
                            val comments = commentResponse.result
                            for(comment in comments) {
                                val commentData = CommentData(
                                    comment.comment_id,
                                    preferenceUtil.getUserNickname().toString(),
                                    preferenceUtil.getUserIntro().toString(),
                                    R.drawable.light,
                                    "10분전",
                                    R.drawable.profile,
                                    R.drawable.morebtn,
                                    "Additional detail about the comment",
                                    R.drawable.favor1,
                                    comment.likecount.toString()
                                )
                                commentDataList.add(commentData)
                            }
                        }
                        // 어댑터에 데이터 설정
                        CommentRVAdapter.setData(commentDataList)
                    }
                } else {
                    // 댓글 가져오기 실패 시 사용자에게 메시지 표시
                    // 예시: Toast.makeText(this@CommentActivity, "댓글 가져오기에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CommentResponseDto>>, t: Throwable) {
                // 네트워크 오류 등 댓글 가져오기 실패 시 처리할 로직
            }
        })
    }
    private fun postComment(boardId: Int, content: String) {
        val commentRequest = CommentRequestDto(content)
        ApiClient.boardService.postComment(boardId, commentRequest).enqueue(object : Callback<List<CommentResponseDto>> {
            override fun onResponse(call: Call<List<CommentResponseDto>>, response: Response<List<CommentResponseDto>>) {
                if (response.isSuccessful) {
                    val commentList = response.body()
                    if (!commentList.isNullOrEmpty()) {
                        val commentId = commentList[0].result[0].comment_id
                        // 댓글 작성 성공 시 UI 업데이트 및 댓글 목록 다시 로드
                        binding.commentEdit.text = null
                        val newComment = CommentData(
                            commentId,
                            preferenceUtil.getUserNickname().toString(),
                            preferenceUtil.getUserIntro().toString(),
                            R.drawable.light,
                            "방금", // 작성 시간 설정 예시
                            R.drawable.profile,
                            R.drawable.morebtn,
                            content, // 작성된 댓글 내용
                            R.drawable.favor1,
                            "0" // 좋아요 수 초기값
                        )
                        CommentRVAdapter.addItem(newComment)
                        loadComments(boardId)
                    }
                } else {
                    // 댓글 작성 실패 시 사용자에게 메시지 표시
                 Toast.makeText(this@CommentActivity, "댓글 작성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<CommentResponseDto>>, t: Throwable) {
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getYourDataList(): ArrayList<CommentData> {
        return ArrayList()
    }

    override fun onEditClick(position: Int) {
        showEditCommentView(position)
    }

    override fun onDeleteClick(position: Int) {
        showDeleteCommentView(position)
    }
    private fun showEditCommentView(position: Int) {
        // 해당 position의 댓글을 수정하는 뷰를 보여줍니다.
    }
    private fun showDeleteCommentView(position: Int) {
        val commentId = dataList[position].comment_id
        deleteComment(commentId)
    }
    private fun deleteComment(commentId: Int) {
        ApiClient.boardService.deleteComment(commentId,0).enqueue(object : Callback<List<CommentResponseDto>> {
            override fun onResponse(call: Call<List<CommentResponseDto>>, response: Response<List<CommentResponseDto>>) {
                if (response.isSuccessful) {
                    // 댓글 삭제 성공 시 UI 업데이트
                    Toast.makeText(this@CommentActivity, "댓글이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    // 데이터 다시 로드
                    loadComments(commentId)
                } else {
                    // 댓글 삭제 실패 시 사용자에게 메시지 표시
                    Toast.makeText(this@CommentActivity, "댓글 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CommentResponseDto>>, t: Throwable) {
                // 네트워크 오류 등 댓글 삭제 실패 시 처리할 로직
            }
        })
    }
}