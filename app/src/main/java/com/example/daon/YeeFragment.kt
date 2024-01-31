package com.example.daon

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daon.databinding.FragmentYeeBinding

class YeeFragment : Fragment() {

    private var _binding: FragmentYeeBinding? = null
    private val binding get() = _binding!!

    private lateinit var yeeRVAdapter: YeeRVAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var yeeitem: ArrayList<YeeData>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYeeBinding.inflate(inflater, container, false)

        recyclerView = binding.yeeRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        yeeitem = getYourDataList()

        yeeRVAdapter = YeeRVAdapter(yeeitem)
        binding.yeeRv.adapter = yeeRVAdapter

        yeeitem.add(YeeData("권혁찬","줄바꿈이 되는지 확인해야지 후후","줄바꿈이 되는지 확인해야지 후후","10분전",R.drawable.calendar,R.drawable.calendar,"02",R.drawable.calendar,"01",R.drawable.calendar,"05"))
        yeeitem.add(YeeData("권혁찬","줄바꿈이 되는지 확인해야지 후후","줄바꿈이 되는지 확인해야지 후후","10분전",R.drawable.calendar,R.drawable.calendar,"02",R.drawable.calendar,"01",R.drawable.calendar,"05"))
        yeeRVAdapter.notifyDataSetChanged()

        binding.fab01.setOnClickListener {
            activity?.let{
                val intent = Intent(context, WriteActivity::class.java)
                startActivity(intent)
            }
        }

        return binding.root
    }

    private fun getYourDataList(): ArrayList<YeeData> {
        return ArrayList()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(bundle: Bundle) {
        val title = bundle.getString("title", "")
        val detail = bundle.getString("detail", "")
        Log.d("asd",title)

        if (title.isNotEmpty()) {
            yeeitem.add(YeeData("권혁찬", title, detail, "방금", R.drawable.calendar, R.drawable.calendar, "02", R.drawable.calendar, "01", R.drawable.calendar, "05"))
            yeeRVAdapter.notifyDataSetChanged()
        }
    }

}