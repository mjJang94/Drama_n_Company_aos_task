package com.mj.dramacompany_aos_task.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.mj.dramacompany_aos_task.R
import com.mj.dramacompany_aos_task.config.FAVORITE
import com.mj.dramacompany_aos_task.config.SEARCH
import com.mj.dramacompany_aos_task.databinding.ActivityMainBinding
import com.mj.dramacompany_aos_task.ui.fragment.FavoriteFragment
import com.mj.dramacompany_aos_task.ui.fragment.SearchFragment

class MainActivity : AppCompatActivity() {


    private lateinit var mainDataBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        dataBinding()

    }

    //데이터 바인딩을 위해 레이아웃과 연결하고 라이프사이클에 종속시킵니다.
    private fun dataBinding() {
        mainDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainDataBinding.lifecycleOwner = this

        initLayout()
    }

    //화면의 기능 구현에 필요한 요소들을 정의해줍니다.
    private fun initLayout() {

        mainDataBinding.tabMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                var fragment = Fragment()

                when (mainDataBinding.tabMain.selectedTabPosition) {

                    SEARCH -> {
                        fragment = SearchFragment()
                    }

                    FAVORITE -> {
                        fragment = FavoriteFragment()
                    }
                }

                changeFragment(fragment)
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })


        changeFragment(SearchFragment())
    }

    //넘겨 받는 프래그먼트에 따라 화면을 교체해주는 메소드 입니다.
    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(mainDataBinding.flFragMain.id, fragment).commit()
    }
}