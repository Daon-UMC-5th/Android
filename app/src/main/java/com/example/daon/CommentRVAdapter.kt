package com.example.daon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.databinding.CommentItemBinding

class CommentRVAdapter(private val dataList: ArrayList<CommentData>) : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>(){
    inner class ViewHolder(private val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root){
        val nickname_ = binding.commentNickname
        val detail_ = binding.commentDetail
        val icon_ = binding.writeIcon
        val timeAgo_ = binding.timeAgo
        val intro_ = binding.intro
        val profileImage_ = binding.writeProfile
        val more_ = binding.writeMore
        val favorIcon_ = binding.writeFavorite
        val favorCount_ = binding.favorCount

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentRVAdapter.ViewHolder {
        val binding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nickname_.text = dataList[position].nickname
        holder.detail_.text = dataList[position].detail
        holder.icon_.setImageResource(R.drawable.light)
        holder.intro_.text = dataList[position].intro
        holder.timeAgo_.text = dataList[position].timeAgo
        holder.more_.setImageResource(R.drawable.morebtn)
        holder.profileImage_.setImageResource(R.drawable.mypage)
        holder.favorIcon_.setImageResource(R.drawable.favor1)
        holder.favorCount_.text = dataList[position].favorCount
    }
    override fun getItemCount(): Int {
        return dataList.size
    }
}