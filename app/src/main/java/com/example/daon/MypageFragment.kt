package com.example.daon

import android.annotation.SuppressLint
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
    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)

        val nicknameget = arguments?.getString("nick")
        val introget = arguments?.getString("intr")
        Log.d("닉네임,", nicknameget.toString())
        if(nicknameget != null && introget != null){
            binding.profileName.text = nicknameget
            binding.profileIntro.text = introget
        }
        else{
            binding.profileName.text = "홍길동"
            binding.profileIntro.text = "안녕하세요. 좋은 모든 일들이 다 오기를!"
        }

        binding.mypageNext.setOnClickListener {
            activity?.let{
                val intent = Intent(context, Edit_ProfileActivity::class.java)
                startActivity(intent)
                activity?.supportFragmentManager?.popBackStack()
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


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}