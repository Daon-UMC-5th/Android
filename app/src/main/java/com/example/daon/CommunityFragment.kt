    package com.example.daon

    import android.os.Bundle
    import android.util.Log
    import android.view.*
    import androidx.fragment.app.Fragment
    import android.widget.ImageView
    import android.widget.Toast
    import androidx.core.view.GravityCompat
    import androidx.drawerlayout.widget.DrawerLayout
    import com.example.daon.community.ApiClient
    import com.example.daon.community.LikeResponse
    import com.example.daon.community.token.PreferenceUtil
    import com.example.daon.databinding.FragmentCommunityBinding
    import com.google.android.material.bottomnavigation.BottomNavigationView
    import com.google.android.material.navigation.NavigationView
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response

    class CommunityFragment : Fragment() {
        private lateinit var preferenceUtil: PreferenceUtil
        private var _binding: FragmentCommunityBinding? = null
        private val binding get() = _binding!!

        private var isFavorite = false
        private lateinit var yeeFragment: YeeFragment

        private lateinit var drawerLayout: DrawerLayout
        private lateinit var navView: NavigationView

        private val favoriteStates = mutableMapOf<Int, Boolean>().apply {
            // 초기 상태를 모든 아이템에 대해 false로 설정
            put(R.id.item3, false)
            put(R.id.item4, false)
            put(R.id.item5, false)
            put(R.id.item6, false)
            put(R.id.item7, false)
            put(R.id.item8, false)
        }
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

            setItemClickListener()
            preferenceUtil = PreferenceUtil(requireContext())
            return binding.root
        }
        private fun setItemClickListener() {
            val menuItemIds = listOf(R.id.item3, R.id.item4, R.id.item5, R.id.item6, R.id.item7, R.id.item8)
            for (itemId in menuItemIds) {
                val menuItem = binding.drawerNavi.menu.findItem(itemId)
                val actionView = menuItem?.actionView
                actionView?.findViewById<ImageView>(R.id.menu_icon2)?.setOnClickListener {
                    favoriteStates[itemId] = !favoriteStates[itemId]!!
                    toggleFavoriteState(itemId)
                }
            }
        }

        private fun handleDrawerMenuItemClick(menuItem: MenuItem) {
            // 드로어 메뉴의 아이템 클릭 처리
            when (menuItem.itemId) {
                R.id.item1 -> {
                    // 전체 아이템 클릭 시 처리
                    val fragment = CommudefFragment().apply {
                        arguments = Bundle().apply {
                            putBoolean("buttonClicked", true) // 버튼이 눌려진 상태를 전달
                        }
                    }
                    replaceFragment(fragment)
                }
                R.id.item2-> {
                    // 공지 아이템 클릭 시 처리
                    val fragment = CommudefFragment().apply {
                        arguments = Bundle().apply {
                            putBoolean("buttonClicked2", true) // 버튼이 눌려진 상태를 전달
                        }
                    }
                    replaceFragment(fragment)
                }
                R.id.item3 -> {
                    // 위암 아이템 클릭 시 처리
                   val fragment = YeeFragment()
                    val isFavorite = favoriteStates[R.id.item3] ?: false
                    preferenceUtil.saveFavoriteState(R.id.item3, isFavorite)
                    replaceFragment(fragment)
                }
                R.id.item4 -> {
                    // 간암 아이템 클릭 시 처리
                    val fragment = GanFragment()
                    val isFavorite = favoriteStates[R.id.item4] ?: false
                    preferenceUtil.saveFavoriteState(R.id.item4, isFavorite)
                    replaceFragment(fragment)
                }
                R.id.item5 -> {
                    // 대장암 아이템 클릭 시 처리
                    val fragment = DaeFragment()
                    val isFavorite = favoriteStates[R.id.item5] ?: false
                    preferenceUtil.saveFavoriteState(R.id.item5, isFavorite)
                    replaceFragment(fragment)
                }
                R.id.item6 -> {
                    // 유방암 아이템 클릭 시 처리
                    val fragment = YuuFragment()
                    val isFavorite = favoriteStates[R.id.item6] ?: false
                    preferenceUtil.saveFavoriteState(R.id.item6, isFavorite)
                    replaceFragment(fragment)
                }
                R.id.item7 -> {
                    // 자궁경부암 아이템 클릭 시 처리
                    val fragment = JaeGFragment()
                    val isFavorite = favoriteStates[R.id.item7] ?: false
                    preferenceUtil.saveFavoriteState(R.id.item7, isFavorite)
                    replaceFragment(fragment)
                }
                R.id.item8 -> {
                    // 기타암 아이템 클릭 시 처리
                    val fragment = GitarFragment()
                    val isFavorite = favoriteStates[R.id.item8] ?: false
                    preferenceUtil.saveFavoriteState(R.id.item8, isFavorite)
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

        private fun toggleFavoriteState(menuItemId: Int) {
            val menuItem = binding.drawerNavi.menu.findItem(menuItemId)
            val actionView = menuItem?.actionView
            val favoriteIcon = actionView?.findViewById<ImageView>(R.id.menu_icon2)
            val isFavorite = favoriteStates[menuItemId] ?: false
            if (isFavorite) {
                favoriteIcon?.setImageResource(R.drawable.favorite_on) // 즐겨찾기가 되어 있는 상태의 이미지
            } else {
                favoriteIcon?.setImageResource(R.drawable.favorite_off) // 즐겨찾기가 되어 있지 않은 상태의 이미지
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }