package com.example.daon.diary

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.daon.main.DiaryFragment

class DiaryViewPagerAdapter(fragment: DiaryFragment) :FragmentStateAdapter(fragment) {
    private lateinit var viewPagerAdapter: DiaryViewPagerAdapter
    private val fragments = listOf<Fragment>(DiaryCalendarFragment(), DiaryListFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
