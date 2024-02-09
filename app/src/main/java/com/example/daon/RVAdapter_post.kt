package com.example.daon

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.daon.databinding.RecyclerItemBinding

class RVAdapter_post(private val dataList: ArrayList<Post>) : RecyclerView.Adapter<RVAdapter_post.ViewHolder>() {

    inner class ViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val post_: TextView = binding.post
        val nickname_: TextView = binding.nickname
        val title_: TextView = binding.title
        val detail_: TextView = binding.detail
        val timeAgo_ = binding.timeAgo
        val Image_: ImageView = binding.profile
        val favorIcon_ = binding.favorIcon
        val favorCount_ = binding.favorCount
        val commentIcon_ = binding.commentIcon
        val commentCount_ = binding.commentCount
        val bookmarkIcon_ = binding.bookmarkIcon
        val bookmarkCount_ = binding.bookmarkCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.post_.text = dataList[position].post
        holder.nickname_.text = dataList[position].nickname
        val titleText = dataList[position].title
        if (titleText.length >= 15) {
            val truncatedText2 = titleText.substring(0, 15) + "···"
            holder.title_.text = truncatedText2
        } else {
            holder.title_.text = titleText
        }
        val detailText = dataList[position].detail
        if (detailText.length >= 15) {
            val truncatedText = detailText.substring(0, 15) + "···"
            holder.detail_.text = truncatedText
        } else {
            holder.detail_.text = detailText
        }
        holder.timeAgo_.text = dataList[position].timeAgo
        holder.Image_.setImageResource(R.drawable.asd)
        holder.favorIcon_.setImageResource(R.drawable.favor1)
        holder.favorCount_.text = dataList[position].favorCount
        holder.commentIcon_.setImageResource(R.drawable.comment)
        holder.commentCount_.text = dataList[position].commentCount
        holder.bookmarkIcon_.setImageResource(R.drawable.bookmark)
        holder.bookmarkCount_.text = dataList[position].bookmarkCount
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
