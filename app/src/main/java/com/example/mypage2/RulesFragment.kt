package com.example.mypage2

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.mypage2.databinding.FragmentRulesBinding


class RulesFragment : Fragment() {

    private var _binding: FragmentRulesBinding? = null
    private val binding get() = _binding!!

    private var isCommunityBtnSelected = false
    private var isDiaryBtnSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRulesBinding.inflate(inflater, container, false)

        binding.rulesBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        updateButtonState(binding.communityBtn, isCommunityBtnSelected)
        updateButtonState(binding.diaryBtn, isDiaryBtnSelected)

        binding.communityBtn.setOnClickListener {
            handleButtonClick(binding.communityBtn, isCommunityBtnSelected)
        }

        binding.diaryBtn.setOnClickListener {
            handleButtonClick(binding.diaryBtn, isDiaryBtnSelected)
        }

        return binding.root
    }

    private fun handleButtonClick(button: Button, isSelected: Boolean) {

        updateButtonState(binding.communityBtn, false)
        updateButtonState(binding.diaryBtn, false)

        updateButtonState(button, true)

        button.setBackgroundResource(R.drawable.button_pressed)

        val textColorResId = if (isSelected) "#AAAAAA" else "#1C734D"
        button.setTextColor(Color.parseColor(textColorResId))

        val fragment = when (button.id) {
            R.id.community_btn -> Rule_commuFragment()
            R.id.diary_btn -> Rule_diaryFragment()
            else -> null
        }
        fragment?.let {
            parentFragmentManager.beginTransaction()
                .replace(R.id.btn_frame, it)
                .commit()
        }
    }

    private fun updateButtonState(button: Button, isSelected: Boolean) {
        button.setBackgroundResource(if (!isSelected) R.drawable.button_default else R.drawable.button_pressed)
        val textColorResId = if (!isSelected) "#AAAAAA" else "#1C734D"
        button.setTextColor(Color.parseColor(textColorResId))
    }

}