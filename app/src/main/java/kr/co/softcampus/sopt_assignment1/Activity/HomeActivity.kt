package kr.co.softcampus.sopt_assignment1.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import kr.co.softcampus.sopt_assignment1.Adapter.HomeViewPagerAdapter
import kr.co.softcampus.sopt_assignment1.Fragment.HomeFragment
import kr.co.softcampus.sopt_assignment1.Fragment.ImageFragment
import kr.co.softcampus.sopt_assignment1.Fragment.ProfileFragment
import kr.co.softcampus.sopt_assignment1.R
import kr.co.softcampus.sopt_assignment1.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        initAdapter()
        initBottomNavigation()

        setContentView(binding.root)
    }

    //ViewPager2와 Adapter 연동
    private fun initAdapter() {
        val fragmentList = listOf(ProfileFragment(), HomeFragment(), ImageFragment())

        homeViewPagerAdapter = HomeViewPagerAdapter(this)
        homeViewPagerAdapter.fragments.addAll(fragmentList)

        binding.vpHome.adapter = homeViewPagerAdapter
    }

    //BottomNavigation과 ViewPager2 연동
    private fun initBottomNavigation() {
        binding.vpHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bnv.menu.getItem(position).isChecked = true
            }
        })

        binding.bnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_profile -> {
                    binding.vpHome.currentItem = PROFILE_FRAGMENT
                    return@setOnItemSelectedListener true
                }
                R.id.menu_home -> {
                    binding.vpHome.currentItem = HOME_FRAGMENT
                    return@setOnItemSelectedListener true
                }
                else -> {
                    binding.vpHome.currentItem = CAMERA_FRAGMENT
                    return@setOnItemSelectedListener true
                }
            }
        }
    }

    companion object {
        const val PROFILE_FRAGMENT = 0
        const val HOME_FRAGMENT = 1
        const val CAMERA_FRAGMENT = 2
    }

}