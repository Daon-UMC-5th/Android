package com.example.daon.diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.DiaryList
import com.example.daon.databinding.DiaryListItemBinding

class DiaryListAdapter(): RecyclerView.Adapter<DiaryListAdapter.DiaryListViewHolder>() {
    private var listData = arrayListOf<DiaryList>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiaryListAdapter.DiaryListViewHolder {
        var binding = DiaryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiaryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiaryListAdapter.DiaryListViewHolder, position: Int) {
        val diaryList: DiaryList=listData[position]
        holder.bind(diaryList)
    }

    override fun getItemCount(): Int {
        return listData.count()
    }
    inner class DiaryListViewHolder(private val binding: DiaryListItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: DiaryList){
            binding.nickname.text = item.nickname
            binding.time.text = item.time
            binding.title.text = item.title
            binding.content.text = item.content
            binding.image.setImageBitmap(item.image)
            binding.heartCnt.text = item.heart.toString()
        }
    }
}