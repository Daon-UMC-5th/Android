package com.example.daon.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.R
import com.example.daon.databinding.CommentItemBinding


class CommentRVAdapter(private val dataList: ArrayList<CommentData>) : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>(){
    interface OnMoreButtonClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }
    private var moreButtonClickListener: OnMoreButtonClickListener? = null
    fun setOnMoreButtonClickListener(listener: OnMoreButtonClickListener) {
        this.moreButtonClickListener = listener
    }
    inner class ViewHolder(private val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root){
        private var editDeleteVisible = false

        val nickname_ = binding.commentNickname
        val detail_ = binding.commentDetail
        val icon_ = binding.writeIcon
        val timeAgo_ = binding.timeAgo
        val intro_ = binding.intro
        val profileImage_ = binding.writeProfile
        val more_ = binding.writeMore
        val favorIcon_ = binding.writeFavorite
        val favorCount_ = binding.favorCount
        init {
            binding.writeMore.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // 모어 버튼이 클릭된 위치(position)을 확인하고 해당 작업을 수행합니다.
                    moreButtonClickListener?.onEditClick(position)
                    moreButtonClickListener?.onDeleteClick(position)
                    toggleEditDeleteView(binding)
                }
            }
            binding.root.setOnClickListener {
                // 수정 및 삭제 뷰가 표시된 상태에서만 처리
                if (editDeleteVisible) {
                    toggleEditDeleteView(binding) // 토글 함수 호출
                }
            }
        }
        private fun toggleEditDeleteView(binding: CommentItemBinding) {
            // 수정 및 삭제 뷰의 visibility를 토글합니다.
            if (binding.editDeleteLayout.visibility == View.VISIBLE) {
                binding.editDeleteLayout.visibility = View.GONE
            } else {
                binding.editDeleteLayout.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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
    @SuppressLint("NotifyDataSetChanged")
    fun setData(newDataList: List<CommentData>) {
        dataList.clear()
        dataList.addAll(newDataList)
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addItem(commentData: CommentData) {
        dataList.add(commentData)
        notifyDataSetChanged()
    }
}