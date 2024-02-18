package com.example.daon.bottomsheet

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.conect.calendar.data.ClinicListCall
import com.example.daon.databinding.ClinicItemBinding
import com.example.daon.info.BodyInfoActivity
import com.example.daon.info.ClinicInfoActivity

class ClinicAdapter(private val context: Context): RecyclerView.Adapter<ClinicAdapter.ClinicViewHolder>() {
    var listData = listOf<ClinicListCall>()
    fun setData(listData : List<ClinicListCall>){
        this.listData= listData
    }
    override fun getItemCount(): Int {
        return listData.count()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicViewHolder {
        var binding = ClinicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClinicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClinicViewHolder, position: Int) {
        val clinicListCall: ClinicListCall = listData[position]
        holder.bind(clinicListCall)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ClinicInfoActivity::class.java)
            val clickedItem = listData[position]
            Log.i("clickedItem",clickedItem.toString())
            intent.putExtra("data",clickedItem)
            context.startActivity(intent)
        }
    }
    inner class ClinicViewHolder(private val binding: ClinicItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: ClinicListCall){
            binding.clinicName.text = item.hospital
            binding.clinicContent.text = item.content
            binding.clinicNotification.text = item.alarmed_at //이 부분 시간 파싱해서 넣어야함
        }
    }
}