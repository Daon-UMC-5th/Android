package com.example.daon

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.community.ApiClient
import com.example.daon.community.BoardService
import com.example.daon.community.PostListCallResponseDto
import com.example.daon.databinding.FragmentYeeBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class YeeFragment : Fragment() {

    private var _binding: FragmentYeeBinding? = null
    private val binding get() = _binding!!

    private lateinit var yeeRVAdapter: YeeRVAdapter
    private lateinit var recyclerView: RecyclerView
    private var yeeitem: ArrayList<YeeData> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYeeBinding.inflate(inflater, container, false)

        recyclerView = binding.yeeRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        yeeRVAdapter = YeeRVAdapter(yeeitem)
        binding.yeeRv.adapter = yeeRVAdapter

        getWiangPosts()

        yeeitem.add(YeeData("권혁찬","줄바꿈이 되는지 확인해야지 후후","줄바꿈이 되는지 확인해야지 후후","10분전",R.drawable.calendar,R.drawable.calendar,"02",R.drawable.calendar,"01",R.drawable.calendar,"05"))
        yeeRVAdapter.notifyDataSetChanged()

        binding.fab01.setOnClickListener {
            activity?.let{
                val intent = Intent(context, WriteActivity::class.java)
                intent.putExtra("boardType", "liver")
                startActivity(intent)
            }
        }

        return binding.root
    }

    private fun getWiangPosts() {
        val boardService = ApiClient.retrofit.create(BoardService::class.java)
        val call = boardService.getAllPosts("위암", 0) // 위암 게시판의 종류를 나타내는 값입니다.
        call.enqueue(object : retrofit2.Callback<PostListCallResponseDto> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<PostListCallResponseDto>, response: Response<PostListCallResponseDto>) {
                if (response.isSuccessful) {
                    val postListCallResponseDto = response.body()
                    if (postListCallResponseDto != null && postListCallResponseDto.isSuccess) {
                        val post = postListCallResponseDto.result
                            val yeeData2 = YeeData(
                                nickname = "권혁찬", // 닉네임 설정 필요
                                title = post.title,
                                detail = post.content,
                                timeAgo = post.created_at,
                                profileImage = R.drawable.calendar, // 프로필 이미지 설정 필요
                                favorIcon = R.drawable.calendar, // 좋아요 아이콘 설정 필요
                                favorCount = post.likecount.toString(),
                                commentIcon = R.drawable.calendar, // 댓글 아이콘 설정 필요
                                commentCount = post.commentcount.toString(),
                                bookmarkIcon = R.drawable.calendar, // 북마크 아이콘 설정 필요
                                bookmarkCount = post.scrapecount.toString()
                            )
                        yeeitem.add(yeeData2)
                        yeeRVAdapter.notifyDataSetChanged()
                    }else {
                        Log.e(TAG, "Failed to get Wiang posts: ${postListCallResponseDto?.message}")
                    }
                } else {
                    Log.e(TAG, "Failed to get Wiang posts: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<PostListCallResponseDto>, t: Throwable) {
                Log.e(TAG, "Failed to fetch data: ${t.message}")
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewPost(newPost: YeeData) {
        yeeitem.add(0, newPost) // 새로운 게시글을 리스트의 맨 위에 추가
        yeeRVAdapter.notifyItemInserted(0) // RecyclerView에 새로운 아이템이 추가됨을 알림
        recyclerView.scrollToPosition(0) // RecyclerView를 최상단으로 스크롤
    }

}