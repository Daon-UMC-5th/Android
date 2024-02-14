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
import com.example.daon.databinding.*

class JaeGFragment : Fragment() {

    private var _binding: FragmentJaeGBinding? = null
    private val binding get() = _binding!!

    private lateinit var yeeRVAdapter: YeeRVAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var yeeitem: ArrayList<YeeData>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJaeGBinding.inflate(inflater, container, false)

        recyclerView = binding.yeeRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        yeeitem = getYourDataList()

        yeeRVAdapter = YeeRVAdapter(yeeitem)
        binding.yeeRv.adapter = yeeRVAdapter


        binding.fab01.setOnClickListener {
            activity?.let{
                val intent = Intent(context, WriteActivity::class.java)
                intent.putExtra("boardType", "womb")
                startActivity(intent)
            }
        }

        return binding.root
    }

    private fun getYourDataList(): ArrayList<YeeData> {
        return ArrayList()
    }

    fun addNewPost(newPostData: YeeData) {

    }

}