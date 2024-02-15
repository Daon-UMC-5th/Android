package com.example.daon

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.daon.databinding.FragmentCommudefBinding
import com.example.daon.databinding.FragmentCommunityBinding
import com.example.daon.databinding.FragmentMypageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class CommudefFragment : Fragment() {
    private var _binding: FragmentCommudefBinding? = null
    private val binding get() = _binding!!

    private var totalbtnSelected = false
    private var noticebtnSelected = false
    private var favoritebtnSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommudefBinding.inflate(inflater, container, false)

        updateButtonState(binding.total,totalbtnSelected)
        updateButtonState(binding.notice,noticebtnSelected)
        updateButtonState(binding.favorite,favoritebtnSelected)

        binding.total.setOnClickListener {
            handleButtonClick(binding.total,totalbtnSelected)
        }
        binding.notice.setOnClickListener {
            handleButtonClick(binding.notice,noticebtnSelected)
        }
        binding.favorite.setOnClickListener {
            handleButtonClick(binding.favorite,favoritebtnSelected)
        }
        val isButtonClicked = arguments?.getBoolean("buttonClicked", false) ?: false
        val isButtonClicked2 = arguments?.getBoolean("buttonClicked2", false) ?: false
        if (isButtonClicked) {
            handleButtonClick(binding.total,totalbtnSelected)
            childFragmentManager.beginTransaction()
                .replace(R.id.post_frm2, CommuTotalFragment())
                .commit()
        }
        if (isButtonClicked2) {
            handleButtonClick(binding.notice,noticebtnSelected)
            childFragmentManager.beginTransaction()
                .replace(R.id.post_frm2, CommunoticeFragment())
                .commit()
        }
        return binding.root
    }

    private fun handleButtonClick(button: Button, isSelected: Boolean) {

        updateButtonState(binding.total,false)
        updateButtonState(binding.notice,false)
        updateButtonState(binding.favorite,false)

        updateButtonState(button,true)

        val fragment = when (button.id) {
            R.id.total -> CommuTotalFragment()
            R.id.notice -> CommunoticeFragment()
            R.id.favorite -> CommufavoriteFragment()
            else -> null
        }
        fragment?.let {
            childFragmentManager.beginTransaction()
                .replace(R.id.post_frm2, it)
                .commit()
        }
    }

    private fun updateButtonState(button: Button, isSelected: Boolean) {
        button.setBackgroundResource(if (isSelected) R.drawable.button_pressed else R.drawable.button_defalut)
        val textColorResId = if (isSelected) "#FFFFFF" else "#757575"
        button.setTextColor(Color.parseColor(textColorResId))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}