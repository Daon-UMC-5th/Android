package com.example.daon.bottomsheet

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.conect.calendar.data.BodyListCall
import com.example.daon.conect.calendar.data.DoseListCall
import com.example.daon.databinding.DoseItemBinding
import com.example.daon.info.BodyInfoActivity
import com.example.daon.info.DoseInfoActivity

class DoseAdapter(private val context: Context): RecyclerView.Adapter<DoseAdapter.DoseViewHolder>() {
    var listData = listOf<DoseListCall>()
    fun setData(listData : List<DoseListCall>){
        this.listData = listData
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
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DoseInfoActivity::class.java)
            val clickedItem = listData[position]
            Log.i("clickedItem",clickedItem.toString())
            intent.putExtra("data",clickedItem)
            context.startActivity(intent)
        }
    }
    inner class DoseViewHolder(private val binding: DoseItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: DoseListCall){
            binding.threeTime.text = item.timeOfDay
            binding.doseName.text = item.medicine
            binding.doseNotification.text = item.alarmedAt // 나중에 파싱
        }
    }
    interface OnDoseItemClickListener {
        fun onDoseItemClick(position: Int)
    }
}