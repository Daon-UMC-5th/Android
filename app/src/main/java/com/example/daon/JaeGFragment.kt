package com.example.daon

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.Adapter.GanRVAdapter
import com.example.daon.Adapter.OnItemClickListener
import com.example.daon.Adapter.YeeData
import com.example.daon.data.community.BoardService
import com.example.daon.data.community.PostListCallResponseDto
import com.example.daon.data.community.token.PreferenceUtil
import com.example.daon.databinding.*
import retrofit2.Call
import retrofit2.Response

class JaeGFragment : Fragment(), OnItemClickListener {
    private lateinit var preferenceUtil: PreferenceUtil
    private var _binding: FragmentJaeGBinding? = null
    private val binding get() = _binding!!

    private lateinit var yeeRVAdapter: GanRVAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var yeeitem: ArrayList<YeeData>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJaeGBinding.inflate(inflater, container, false)

        recyclerView = binding.yeeRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        yeeitem = getYourDataList()

        yeeRVAdapter = GanRVAdapter(yeeitem,this)
        binding.yeeRv.adapter = yeeRVAdapter

        preferenceUtil = PreferenceUtil(requireContext())
        val isFavorite = preferenceUtil.getFavoriteState(R.id.item7)
        val imageView = binding.favoriteIcon
        Log.d("상태", isFavorite.toString())
        if (isFavorite) {
            imageView.setImageResource(R.drawable.favorite_on)
        } else {
            imageView.setImageResource(R.drawable.favorite_off)
        }

        binding.fab01.setOnClickListener {
            activity?.let{
                val intent = Intent(context, WriteActivity::class.java)
                intent.putExtra("boardType", "womb")
                startActivity(intent)
            }
        }
        getWiangPosts()
        return binding.root
    }

    private fun getWiangPosts() {
        Log.d("ghkrdls","asfgksd")
        val boardService = com.example.daon.data.community.ApiClient.retrofit.create(
            BoardService::class.java)
        val call = boardService.getAllPosts("stomach", 0) // 위암 게시판의 종류를 나타내는 값입니다.
        call.enqueue(object : retrofit2.Callback<PostListCallResponseDto> {
            @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
            override fun onResponse(call: Call<PostListCallResponseDto>, response: Response<PostListCallResponseDto>) {
                if (response.isSuccessful) {
                    val postListCallResponseDto = response.body()
                    if (postListCallResponseDto != null && postListCallResponseDto.isSuccess) {
                        val post = postListCallResponseDto.result
                        val postDataList: List<YeeData> = post.map { post ->
                            YeeData(
                                nickname = preferenceUtil.getUserNickname().toString(),
                                detail = post.content,
                                title = post.title,
                                timeAgo = post.created_at,
                                profileImage = post.image_url,
                                favorIcon = R.drawable.favor1,
                                favorCount = post.likecount.toString(),
                                commentIcon = R.drawable.comment,
                                commentCount = post.commentcount.toString(),
                                bookmarkIcon = R.drawable.bookmark,
                                bookmarkCount = post.scrapecount.toString()
                            )
                        }
                        yeeitem.addAll(postDataList)
                        yeeRVAdapter.notifyDataSetChanged() // RecyclerView에 새로운 아이템이 추가됨을 알림
                        recyclerView.scrollToPosition(0) // RecyclerView를 최상단으로 스크롤
                        Log.d("ghkrdls", "asfgksd")
                    }else {
                        Log.e(ContentValues.TAG, "Failed to get Wiang posts: ${postListCallResponseDto?.message}")
                    }
                } else {
                    Log.e(ContentValues.TAG, "Failed to get Wiang posts: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<PostListCallResponseDto>, t: Throwable) {
                Log.e(ContentValues.TAG, "Failed to fetch data: ${t.message}")
            }
        })
    }

    private fun getYourDataList(): ArrayList<YeeData> {
        return ArrayList()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewPost(newPost: YeeData) {
        yeeitem.add(newPost)
        yeeRVAdapter.notifyDataSetChanged()
        Log.d("list", newPost.toString())// 새로운 게시글을 리스트의 맨 위에 추가
    }
    override fun onItemClick(boardId: Int) {
        val intent = Intent(context, WriteActivity::class.java)
        intent.putExtra("boardId", boardId)
        startActivity(intent)
    }
}