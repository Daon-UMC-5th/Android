package com.example.daon.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.conect.calendar.data.DoseListCall
import com.example.daon.databinding.DoseItemBinding

class DoseAdapter(): RecyclerView.Adapter<DoseAdapter.DoseViewHolder>() {
    var listData = mutableListOf<DoseListCall>()
    fun setData(listData : List<DoseListCall>){
        this.listData = listData.toMutableList()
    }
    override fun getItemCount(): Int {
        return listData.count()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoseViewHolder {
        var binding = DoseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoseViewHolder, position: Int) {
        val doseListCall: DoseListCall = listData[position]
        holder.bind(doseListCall)
    }
    inner class DoseViewHolder(private val binding: DoseItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: DoseListCall){
            binding.threeTime.text = item.time_of_day
            binding.doseName.text = item.medicine
            binding.doseNotification.text = item.alarmed_at // 나중에 파싱
        }
    }
    interface OnDoseItemClickListener {
        fun onDoseItemClick(position: Int)
    }
}