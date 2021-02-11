package com.mj.dramacompany_aos_task.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mj.dramacompany_aos_task.ui.fragment.FavoriteUserFragment
import com.mj.dramacompany_aos_task.ui.fragment.SearchUserFragment

class ViewPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {

    var fragments = ArrayList<Fragment>()

    init {
        fragments.add(SearchUserFragment())
        fragments.add(FavoriteUserFragment())
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}