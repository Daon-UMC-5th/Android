package com.example.daon.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.conect.calendar.data.ClinicListCall
import com.example.daon.databinding.ClinicItemBinding

class ClinicAdapter(): RecyclerView.Adapter<ClinicAdapter.ClinicViewHolder>() {
    var listData = mutableListOf<ClinicListCall>()
    fun setData(listData : ClinicListCall){
        this.listData.add(listData)
    }
    override fun getItemCount(): Int {
        return listData.count()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicViewHolder {
        var binding = ClinicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClinicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClinicViewHolder, position: Int) {
        val bodyListCall: ClinicListCall = listData[position]
        holder.bind(bodyListCall)
    }
    inner class ClinicViewHolder(private val binding: ClinicItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: ClinicListCall){
            binding.clinicName.text = item.hospital
            binding.clinicContent.text = item.content
            binding.clinicNotification.text = item.alarmed_at //이 부분 시간 파싱해서 넣어야함
        }
    }

    interface OnClinicItemClickListener {
        fun onClinicItemClick(position: Int)
    }
}