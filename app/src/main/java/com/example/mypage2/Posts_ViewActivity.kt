package com.example.mypage2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypage2.databinding.ActivityPasswordchangeBinding
import com.example.mypage2.databinding.ActivityPostsViewBinding

class Posts_ViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostsViewBinding
    private var postitem = ArrayList<Post>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostsViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.postRV.layoutManager = layoutManager

        val adapter = RVAdapter_post(postitem)
        binding.postRV.adapter = adapter

        postitem.add(Post("게시물1","권혁찬","병원에 가야해여","병원 제가한번 가보겠습니다123",R.drawable.asd))
        postitem.add(Post("게시물2","권혁찬","병원에 가야해asdsglkdamgkldfg여","병원 제가한번 가보겠습니다123",
            R.drawable.btm_color_selector
        ))
        Log.d("Posts_ViewActivity", "postitem size: ${postitem.size}")
        adapter.notifyDataSetChanged()
    }

}