package com.example.daon

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.community.ApiClient
import com.example.daon.community.BoardService
import com.example.daon.community.PostListCallResponseDto
import com.example.daon.community.token.PreferenceUtil
import com.example.daon.databinding.FragmentYeeBinding
import retrofit2.Call
import retrofit2.Response

class YeeFragment : Fragment() {
    private lateinit var preferenceUtil: PreferenceUtil
    private var _binding: FragmentYeeBinding? = null
    private val binding get() = _binding!!
    private var isFavorite = false
    private val pendingPosts: ArrayList<YeeData> = ArrayList()

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

        preferenceUtil = PreferenceUtil(requireContext())
        val isFavorite = preferenceUtil.getFavoriteState(R.id.item3)
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
                intent.putExtra("boardType", "stomach")
                startActivity(intent)
            }
        }
        //getWiangPosts()
        processPendingPosts()
        return binding.root
    }
    private fun getWiangPosts() {
        Log.d("ghkrdls","asfgksd")
        val boardService = ApiClient.retrofit.create(BoardService::class.java)
        val call = boardService.getAllPosts("stomach", 0) // 위암 게시판의 종류를 나타내는 값입니다.
        call.enqueue(object : retrofit2.Callback<PostListCallResponseDto> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<PostListCallResponseDto>, response: Response<PostListCallResponseDto>) {
                if (response.isSuccessful) {
                    val postListCallResponseDto = response.body()
                    if (postListCallResponseDto != null && postListCallResponseDto.isSuccess) {
                        val post = postListCallResponseDto.result
                        yeeRVAdapter.notifyDataSetChanged() // RecyclerView에 새로운 아이템이 추가됨을 알림
                        recyclerView.scrollToPosition(0) // RecyclerView를 최상단으로 스크롤
                        Log.d("ghkrdls","asfgksd")
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
    private fun processPendingPosts() {
        if (::yeeRVAdapter.isInitialized) {
            for (post in pendingPosts) {
                yeeitem.add(post)
            }
            yeeRVAdapter.notifyDataSetChanged()
            pendingPosts.clear() // 처리된 데이터를 지움
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewPost(newPost: YeeData) {
        yeeitem.add(newPost)
        yeeRVAdapter.notifyDataSetChanged()
        Log.d("list", newPost.toString())// 새로운 게시글을 리스트의 맨 위에 추가

    }

    fun toggleFavoriteState() {
        isFavorite = !isFavorite
        updateFavoriteIcon()
    }

    // 별표시 아이콘 상태에 따라 이미지 업데이트
        private fun updateFavoriteIcon() {
        val favoriteIcon = view?.findViewById<ImageView>(R.id.favorite_icon)
        if (isFavorite) {
            favoriteIcon?.setImageResource(R.drawable.favorite_on)
        } else {
            favoriteIcon?.setImageResource(R.drawable.favorite_off)
        }
    }
}