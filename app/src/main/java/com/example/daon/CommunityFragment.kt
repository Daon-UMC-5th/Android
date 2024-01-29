package com.example.daon

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.daon.databinding.FragmentCommunityBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class CommunityFragment : Fragment() {
    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    private var yeeFragment: YeeFragment? = null

    private var selectedMenuItemTitle: String = "전체"
    private var selectedMenuItemIconResId: Drawable? = null

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        val rootView = binding.root

        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.main_bnv)
        drawerLayout = rootView.findViewById(R.id.Drawer_Layout)


        binding.drawerNavi.itemIconTintList = null
        binding.slide.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
                bottomNav?.visibility = View.VISIBLE
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
                bottomNav?.visibility = View.GONE
            }
        }

        binding.drawerNavi.setNavigationItemSelectedListener { menuItem ->
            handleDrawerMenuItemClick(menuItem)
            true
        }

        val initialFragment = CommudefFragment() // Replace YourInitialFragment with the actual initial fragment
        childFragmentManager.beginTransaction()
            .replace(R.id.post_frm, initialFragment)
            .commit()

        return binding.root
    }

    private fun handleDrawerMenuItemClick(menuItem: MenuItem) {
        // 드로어 메뉴의 아이템 클릭 처리
        when (menuItem.itemId) {
            R.id.item3 -> {
                // 위암 아이템 클릭 시 처리
               val fragment = YeeFragment()
                replaceFragment(fragment)
            }
            R.id.item4 -> {
                // 간암 아이템 클릭 시 처리
                val fragment = GanFragment()
                replaceFragment(fragment)
            }
            R.id.item5 -> {
                // 대장암 아이템 클릭 시 처리
                val fragment = DaeFragment()
                replaceFragment(fragment)
            }
            R.id.item6 -> {
                // 유방암 아이템 클릭 시 처리
                val fragment = YuuFragment()
                replaceFragment(fragment)
            }
            R.id.item7 -> {
                // 자궁경부암 아이템 클릭 시 처리
                val fragment = JaeGFragment()
                replaceFragment(fragment)
            }
            R.id.item8 -> {
                // 기타암 아이템 클릭 시 처리
                val fragment = GitarFragment()
                replaceFragment(fragment)
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.post_frm, fragment)
            .commit()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}