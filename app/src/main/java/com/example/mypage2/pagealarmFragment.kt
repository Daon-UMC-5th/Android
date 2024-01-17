package com.example.mypage2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mypage2.databinding.FragmentPagealarmBinding


class pagealarmFragment : Fragment() {

    private var _binding: FragmentPagealarmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPagealarmBinding.inflate(inflater, container, false)

        binding.alarmBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

}