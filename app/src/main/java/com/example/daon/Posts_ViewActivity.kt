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

        binding.postviewBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val layoutManager = LinearLayoutManager(this)
        binding.postRV.layoutManager = layoutManager

        val adapter = RVAdapter_post(postitem)
        binding.postRV.adapter = adapter

        postitem.add(Post("게시물1","권혁찬","병원에 가야해여","병원 제가한번 가보겠습니다123","10분전",R.drawable.calendar,R.drawable.favor1,"02",R.drawable.comment,"01",R.drawable.bookmark,"05"))

        Log.d("Posts_ViewActivity", "postitem size: ${postitem.size}")
        adapter.notifyDataSetChanged()
    }

}