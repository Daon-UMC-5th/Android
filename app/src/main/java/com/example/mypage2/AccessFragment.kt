package com.example.mypage2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mypage2.databinding.FragmentAccessBinding
import com.example.mypage2.databinding.FragmentPagealarmBinding

class AccessFragment : Fragment() {
    private var _binding: FragmentAccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccessBinding.inflate(inflater, container, false)


        binding.accessBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.changePassword.setOnClickListener {
            val nextFragment = PasswordchangeFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, nextFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        return binding.root
    }
}