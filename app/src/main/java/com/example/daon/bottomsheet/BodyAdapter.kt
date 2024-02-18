package com.example.daon.bottomsheet

import android.content.Context
import android.content.Intent
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
import com.example.daon.fab.BodyActivity
import com.example.daon.info.BodyInfoActivity

class BodyAdapter(private val context: Context): RecyclerView.Adapter<BodyAdapter.BodyViewHolder>() {
    var listData = listOf<BodyListCall>()
    fun setData(listData : List<BodyListCall>){
        this.listData = listData
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
        holder.itemView.setOnClickListener {
            val intent = Intent(context, BodyInfoActivity::class.java)
            val clickedItem = listData[position]
            Log.i("clickedItem",clickedItem.toString())
            intent.putExtra("data",clickedItem)
            intent.putExtra("date",clickedItem.alarmed_date)
            context.startActivity(intent)
        }
    }
    inner class BodyViewHolder(private val binding: BodyItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BodyListCall) {
            binding.temperatureCnt.text = item.temperature
            binding.weightCnt.text = item.weight
            binding.bloodSugarCnt.text = item.fasting_blood_sugar.toString()
            binding.specialNoteContext.text = item.special_note.toString()
        }
    }
}