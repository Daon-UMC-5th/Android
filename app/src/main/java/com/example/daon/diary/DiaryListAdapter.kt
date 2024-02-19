package com.example.daon.diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.DiaryList
import com.example.daon.conect.diary.data.DiaryGetPrivate
import com.example.daon.databinding.DiaryListItemBinding

class DiaryListAdapter(): RecyclerView.Adapter<DiaryListAdapter.DiaryListViewHolder>() {
    private var listData = listOf<DiaryGetPrivate>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiaryListAdapter.DiaryListViewHolder {
        var binding = DiaryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiaryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiaryListAdapter.DiaryListViewHolder, position: Int) {
        val diaryList: DiaryGetPrivate=listData[position]
        holder.bind(diaryList)
    }
    fun setList(listData: List<DiaryGetPrivate>){
        this.listData = listData
    }

    override fun getItemCount(): Int {
        return listData.count()
    }
    inner class DiaryListViewHolder(private val binding: DiaryListItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: DiaryGetPrivate){
            binding.nickname.text = item.userNickname
            binding.time.text = item.createdAt
            binding.title.text = item.title
            binding.content.text = item.content
//            binding.image.setImageBitmap(item.imageUrl)
            binding.heartCnt.text = item.likesCount.toString()
        }
    }
}