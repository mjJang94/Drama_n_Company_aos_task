package com.mj.dramacompany_aos_task.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.tabs.TabLayout
import com.mj.dramacompany_aos_task.R
import com.mj.dramacompany_aos_task.adapter.ViewPagerAdapter
import com.mj.dramacompany_aos_task.databinding.ActivityMainBinding

/**
 * MainActivity.kt
 * SearchUserFragment, FavoriteUserFragment 를 포함하고 있는 메인 액티비티 클래스입니다.
 * 탭레이아웃의 선택값에 따라 fragment를 교체하여 상황에 맞는 화면을 보여줍니다.
 */
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        dataBinding()

    }

    //데이터 바인딩을 위해 레이아웃과 연결하고 라이프사이클에 종속시킵니다.
    private fun dataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        initLayout()
    }

    //화면의 기능 구현에 필요한 요소들을 정의해줍니다.
    private fun initLayout() {

        binding.viewPagerMain.adapter = ViewPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)

        binding.viewPagerMain.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabMain))

        binding.tabMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                binding.viewPagerMain.currentItem = tab!!.position
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
    }
}