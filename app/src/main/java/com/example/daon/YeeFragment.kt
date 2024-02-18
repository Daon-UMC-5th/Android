package com.example.daon

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.Adapter.OnItemClickListener
import com.example.daon.Adapter.YeeData
import com.example.daon.Adapter.YeeRVAdapter
import com.example.daon.data.community.BoardService
import com.example.daon.data.community.PostListCallResponseDto
import com.example.daon.data.community.token.PreferenceUtil
import com.example.daon.databinding.FragmentYeeBinding
import retrofit2.Call
import retrofit2.Response

class YeeFragment : Fragment(), OnItemClickListener {
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

        yeeRVAdapter = YeeRVAdapter(yeeitem,this)
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
        val bundle = Bundle()
        val deleteBoardId = bundle.getInt("deleteboardId", -1)
        val deleteBoardType = bundle.getString("deleteboardType", "")

        getWiangPosts()
        deleteItem(deleteBoardId,deleteBoardType)
        return binding.root
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun deleteItem(boardIdToDelete: Int, boardType: String){
        val iterator = yeeitem.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.boardId == boardIdToDelete) {
                iterator.remove()
                // 삭제된 아이템을 RecyclerView에 적용하기 위해 notifyDataSetChanged 호출
                break
            }
        }
        yeeRVAdapter.notifyDataSetChanged()
    }
    private fun getWiangPosts() {
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
                                boardId = post.board_id,
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
    fun setAdapter(adapter: YeeRVAdapter) {
        yeeRVAdapter = adapter
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addNewPost(newPost: YeeData) {
        if (::yeeRVAdapter.isInitialized) {
            yeeitem.add(newPost)
            yeeRVAdapter.notifyDataSetChanged()
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