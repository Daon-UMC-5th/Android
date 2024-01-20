package com.example.mypage2

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.mypage2.databinding.FragmentMypageBinding

class MypageFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val sharedViewModel2: SharedViewModel2 by activityViewModels()
    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)

        binding.mypageNext.setOnClickListener {
            activity?.let{
                val intent = Intent(context, Edit_ProfileActivity::class.java)
                startActivity(intent)
            }
        }
        binding.alarmNext.setOnClickListener {
            activity?.let{
                val intent = Intent(context, PagealarmActivity::class.java)
                startActivity(intent)
            }
        }
        binding.accessNext.setOnClickListener {
            activity?.let{
                val intent = Intent(context, AccessActivity::class.java)
                startActivity(intent)
            }
        }
        binding.rule.setOnClickListener {
            val nextFragment = RulesFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, nextFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.rule2.setOnClickListener {
            val nextFragment = Rules2Fragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, nextFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        sharedViewModel.getUserInput().observe(viewLifecycleOwner) { userInputText ->
            binding.profileName.text = userInputText
        }
        sharedViewModel2.getUserInput2().observe(viewLifecycleOwner) { userInputText2 ->
            binding.profileIntro.text = userInputText2
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}