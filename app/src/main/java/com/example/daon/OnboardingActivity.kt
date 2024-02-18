package com.example.daon

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.daon.R
import com.example.daon.ViewPagerAdapter
import com.example.daon.fragment_onpage1
import com.example.daon.fragment_onpage2
import com.example.daon.fragment_onpage3
import com.example.daon.fragment_onpage4
import com.example.daon.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()

        // 페이지 변경 이벤트를 감지
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateIndicatorImage(position)
                updateButtonText(position)
            }
        })

        binding.onboardingNextBtn.setOnClickListener {
            val nextItem = binding.viewPager.currentItem + 1
            if (nextItem < viewPagerAdapter.itemCount) {
                binding.viewPager.setCurrentItem(nextItem, true)
            }
        }

        binding.onboardingSkipTv.setOnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initAdapter() {
        val fragmentList =
            listOf(fragment_onpage1(), fragment_onpage2(), fragment_onpage3(), fragment_onpage4())

        viewPagerAdapter = ViewPagerAdapter(this)
        viewPagerAdapter.fragments.addAll(fragmentList)

        binding.viewPager.adapter = viewPagerAdapter
    }

    private fun updateIndicatorImage(position: Int) {
        when (position) {
            0 -> binding.indicator.setImageResource(R.drawable.onboarding2)
            1 -> binding.indicator.setImageResource(R.drawable.onboarding4)
            2 -> binding.indicator.setImageResource(R.drawable.onboarding6)
            3 -> binding.indicator.setImageResource(R.drawable.onboarding8)

        }
    }

    private fun updateButtonText(position: Int) {
        if (position == 3) {
            binding.onboardingNextBtn.text = "시작하기"

            binding.onboardingNextBtn.setOnClickListener {
                val intent = Intent(this, StartActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            binding.onboardingNextBtn.text = "다음"

            binding.onboardingNextBtn.setOnClickListener {
                val nextItem = binding.viewPager.currentItem + 1
                if (nextItem < viewPagerAdapter.itemCount) {
                    binding.viewPager.setCurrentItem(nextItem, true)
                }
            }
        }
    }
}
