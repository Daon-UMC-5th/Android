package com.example.mypage2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.mypage2.databinding.FragmentMypageBinding

class MypageFragment : Fragment() {
    private val sharedViewModel by activityViewModels<SharedViewModel>()
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
            activity?.let{
                val intent = Intent(context, RulesActivity::class.java)
                startActivity(intent)
            }
        }
        binding.rule2.setOnClickListener {
            activity?.let{
                val intent = Intent(context, RulesActivity2::class.java)
                startActivity(intent)
            }
        }
        binding.postCount.setOnClickListener {
            activity?.let{
                val intent = Intent(context, Posts_ViewActivity::class.java)
                startActivity(intent)
            }
        }

        val nicknameFromIntent = arguments?.getString("nickname")
        val introFromIntent = arguments?.getString("intro")
        Log.d("asdsad",nicknameFromIntent.toString())
        if (!nicknameFromIntent.isNullOrEmpty() && !introFromIntent.isNullOrEmpty()) {
            binding.profileName.text = nicknameFromIntent
            binding.profileIntro.text = introFromIntent
        }
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        Log.d("MypageFragment_resume", "resume")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setData(dataFromEditProfile: String?) {
        if (dataFromEditProfile != null) {
            binding.profileName.text = dataFromEditProfile
        }
    }
}