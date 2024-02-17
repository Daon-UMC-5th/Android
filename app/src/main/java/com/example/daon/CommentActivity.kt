package com.example.daon

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.databinding.ActivityCommentBinding
import com.example.daon.databinding.ActivityMainBinding

class CommentActivity : AppCompatActivity() {

    private lateinit var datas: ArrayList<CommentData>
    private lateinit var CommentRVAdapter: CommentRVAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var binding : ActivityCommentBinding
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.commentRV
        datas = getYourDataList()
        CommentRVAdapter = CommentRVAdapter(datas)

        binding.commentRV.layoutManager = LinearLayoutManager(this)
        binding.commentRV.adapter = CommentRVAdapter
        datas.add(CommentData("권혁찬","안녕하세요",R.drawable.calendar,"10분전",R.drawable.calendar,R.drawable.calendar,"dasjfkjdsbj\nksdb\nfjksbdjklbsg",R.drawable.calendar,"00"))
        CommentRVAdapter.notifyDataSetChanged()


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

    private fun getYourDataList(): ArrayList<CommentData> {
        return ArrayList()
    }
}