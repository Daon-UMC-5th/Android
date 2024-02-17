package com.example.daon.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.R
import com.example.daon.community.token.PreferenceUtil
import com.example.daon.databinding.PostItemBinding
interface OnItemClickListener {
   fun onItemClick(boardId: Int)
}
class YeeRVAdapter(private val dataList: ArrayList<YeeData>, private val listener: OnItemClickListener) : RecyclerView.Adapter<YeeRVAdapter.ViewHolder>() {
    private lateinit var preferenceUtil: PreferenceUtil
    inner class ViewHolder(private val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root){
        val nickname_ = binding.nickname
        val title_ = binding.title
        val detail_ = binding.detail
        val timeAgo_ = binding.timeAgo
        val profileImage_ = binding.profile
        val favorIcon_ = binding.favorIcon
        val favorCount_ = binding.favorCount
        val commentIcon_ = binding.commentIcon
        val commentCount_ = binding.commentCount
        val bookmarkIcon_ = binding.bookmarkIcon
        val bookmarkCount_ = binding.bookmarkCount
        init {
            preferenceUtil = PreferenceUtil(itemView.context)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val boardId = preferenceUtil.getPostId()

        holder.nickname_.text = preferenceUtil.getUserNickname().toString()
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
        holder.profileImage_.setImageResource(R.drawable.calendar)
        holder.favorIcon_.setImageResource(R.drawable.favor1)
        holder.favorCount_.text = dataList[position].favorCount
        holder.commentIcon_.setImageResource(R.drawable.comment)
        holder.commentCount_.text = dataList[position].commentCount
        holder.bookmarkIcon_.setImageResource(R.drawable.bookmark)
        holder.bookmarkCount_.text = dataList[position].bookmarkCount
        holder.itemView.setOnClickListener {
           listener.onItemClick(boardId)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
