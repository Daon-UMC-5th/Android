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
import com.example.daon.databinding.FragmentGanBinding
import retrofit2.Call
import retrofit2.Response

class GanFragment : Fragment(), OnItemClickListener {
    private lateinit var preferenceUtil: PreferenceUtil
    private var _binding: FragmentGanBinding? = null
    private val binding get() = _binding!!

    private lateinit var ganRVAdapter: GanRVAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var yeeitem: ArrayList<YeeData>
    private val pendingPosts: ArrayList<YeeData> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGanBinding.inflate(inflater, container, false)

        recyclerView = binding.yeeRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        yeeitem = getYourDataList()

        ganRVAdapter = GanRVAdapter(yeeitem,this)
        binding.yeeRv.adapter = ganRVAdapter

        preferenceUtil = PreferenceUtil(requireContext())
        val isFavorite = preferenceUtil.getFavoriteState(R.id.item4)
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
                intent.putExtra("boardType", "liver")
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
                                post.board_id,
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
                        ganRVAdapter.notifyDataSetChanged() // RecyclerView에 새로운 아이템이 추가됨을 알림
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
    fun setAdapter2(adapter: GanRVAdapter) {
        ganRVAdapter = adapter
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addNewPost(newPost: YeeData) {
        if (::ganRVAdapter.isInitialized) {
            yeeitem.add(newPost)
            ganRVAdapter.notifyDataSetChanged()
        } else {
            // 어댑터가 초기화되지 않은 경우, pendingPosts에 추가
            pendingPosts.add(newPost)
        }
    }
    override fun onItemClick(boardId: Int) {
        val intent = Intent(context, ReadwriteActivity::class.java)
        intent.putExtra("boardId", boardId)
        startActivity(intent)
    }

}