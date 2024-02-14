package com.example.daon

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.community.ApiClient
import com.example.daon.community.BoardService
import com.example.daon.community.PostListCallResponseDto
import com.example.daon.databinding.FragmentCommuTotalBinding
import com.example.daon.databinding.FragmentCommudefBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommuTotalFragment : Fragment() {
    private var _binding: FragmentCommuTotalBinding? = null
    private val binding get() = _binding!!

    private lateinit var totalRVAdapter: TotalRVAdapter
    private lateinit var recyclerView: RecyclerView
    private var totalitem: ArrayList<TotalData> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCommuTotalBinding.inflate(inflater, container, false)

        recyclerView = binding.totalRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        totalRVAdapter = TotalRVAdapter(totalitem)
        binding.totalRv.adapter = totalRVAdapter

        val service = ApiClient.retrofit.create(BoardService::class.java)
        val call = service.getAllAllPosts(0)
        call.enqueue(object : Callback<PostListCallResponseDto> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<PostListCallResponseDto>, response: Response<PostListCallResponseDto>) {
                if (response.isSuccessful) {
                    val postListCallResponseDto = response.body()
                    if (postListCallResponseDto != null && postListCallResponseDto.isSuccess) {
                        // 성공적으로 응답을 받았을 때 처리할 작업 수행
                        val post = postListCallResponseDto.result
                        val yeeData2 = TotalData(
                            postname = post.board_type+"게시판",
                            nickname = "asd", // 닉네임 설정 필요
                            title = post.title,
                            detail = post.content,
                            timeAgo = post.created_at,
                            profileImage = post.image_url, // 프로필 이미지 설정 필요
                            favorIcon = R.drawable.favor1, // 좋아요 아이콘 설정 필요
                            favorCount = post.likecount.toString(),
                            commentIcon = R.drawable.comment, // 댓글 아이콘 설정 필요
                            commentCount = post.commentcount.toString(),
                            bookmarkIcon = R.drawable.bookmark, // 북마크 아이콘 설정 필요
                            bookmarkCount = post.scrapecount.toString()
                        )
                        totalitem.add(yeeData2)
                        totalRVAdapter.notifyDataSetChanged()

                    } else {
                        // 서버로부터의 응답이 실패일 때 처리
                    }
                } else {
                    // HTTP 요청이 실패한 경우 처리
                }
            }

            override fun onFailure(call: Call<PostListCallResponseDto>, t: Throwable) {
                // 네트워크 통신 자체가 실패했을 때 처리
            }
        })

        return binding.root
    }
}