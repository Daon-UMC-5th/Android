package com.example.mypage2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mypage2.databinding.FragmentAccessBinding
import com.example.mypage2.databinding.FragmentPasswordchangeBinding

class PasswordchangeFragment : Fragment() {

    private var _binding: FragmentPasswordchangeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPasswordchangeBinding.inflate(inflater, container, false)

        binding.passwordBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }
}
