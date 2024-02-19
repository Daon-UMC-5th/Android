package com.example.daon.diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daon.databinding.FragmentDiaryListBinding

class DiaryListFragment : Fragment() {
    private var _binding: FragmentDiaryListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiaryListBinding.inflate(inflater,container,false)
        setDiaryList()
        return binding.root
    }
    private fun setDiaryList(){
        var diaryListAdapter = DiaryListAdapter()
        binding.diaryListRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = diaryListAdapter
            diaryListAdapter.notifyDataSetChanged()
        }
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}