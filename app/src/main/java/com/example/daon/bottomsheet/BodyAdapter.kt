package com.example.daon.bottomsheet

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.R
import com.example.daon.conect.calendar.data.BodyListCall
import com.example.daon.databinding.BodyItemBinding

class BodyAdapter(): RecyclerView.Adapter<BodyAdapter.BodyViewHolder>() {
    var listData = mutableListOf<BodyListCall>()
    fun setData(listData : BodyListCall){
        this.listData.add(listData)
    }
    override fun getItemCount(): Int {
        return listData.count()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BodyViewHolder {
        var binding = BodyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BodyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BodyViewHolder, position: Int) {
        val bodyListCall: BodyListCall = listData[position]
        holder.bind(bodyListCall)
    }
    inner class BodyViewHolder(private val binding: BodyItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: BodyListCall){
            binding.temperatureCnt.text = item.temperture
            binding.weightCnt.text = item.weight
            binding.bloodSugarCnt.text = item.fasting_blood_sugar.toString()
            binding.specialNoteContext.text = item.special_note.toString()
        }
    }

    interface OnBodyItemClickListener {
        fun onBodyItemClick(position: Int)
    }
}