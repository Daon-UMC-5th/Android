package com.example.mypage2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mypage2.databinding.FragmentMypageBinding
import com.example.mypage2.databinding.FragmentUseBinding


class UseFragment : Fragment() {

    private var _binding: FragmentUseBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUseBinding.inflate(inflater, container, false)


        return binding.root
    }
}