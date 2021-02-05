package com.mj.dramacompany_aos_task.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.mj.dramacompany_aos_task.R
import com.mj.dramacompany_aos_task.databinding.ActivityMainBinding
import com.mj.dramacompany_aos_task.ui.fragment.FavoriteFragment
import com.mj.dramacompany_aos_task.ui.fragment.SearchFragment
import com.mj.dramacompany_aos_task.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {


    private lateinit var mainDataBinding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding()


    }

    //데이터 바인딩을 위해 레이아웃과 연결하고 라이프사이클에 종속시킵니다.
    private fun dataBinding() {
        mainDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainDataBinding.mainViewModel = MainViewModel()
        mainDataBinding.lifecycleOwner = this

        initLayout()
    }

    //화면의 기능 구현에 필요한 요소들을 정의해줍니다.
    private fun initLayout() {


        //탭 선택에따른 동작을 정의합니다.
        mainDataBinding.tabMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            //탭 선택된 경우
            override fun onTabSelected(tab: TabLayout.Tab?) {

                var fragment: Fragment = Fragment()

                val position = mainDataBinding.tabMain.selectedTabPosition

                when(position){
                    0 ->{
                        fragment = SearchFragment()
                    }

                    1->{
                        fragment = FavoriteFragment()
                    }
                }

                supportFragmentManager.beginTransaction().replace(mainDataBinding.flFragMain.id, fragment!!).commit()
            }

            //탭 재선택된 경우
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }


            //탭 선택이 안된경우
            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })

        mainDataBinding.tabMain.setSelect
    }
}