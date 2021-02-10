package com.mj.dramacompany_aos_task.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mj.dramacompany_aos_task.R
import com.mj.dramacompany_aos_task.adapter.DataListAdapter
import com.mj.dramacompany_aos_task.adapter.FavoriteDataListAdapter
import com.mj.dramacompany_aos_task.databinding.FragmentFavoriteBinding
import com.mj.dramacompany_aos_task.databinding.FragmentSearchBinding
import com.mj.dramacompany_aos_task.viewmodel.FavoriteViewModel
import com.mj.dramacompany_aos_task.viewmodel.SearchViewModel

class FavoriteFragment : Fragment() {

    private lateinit var favoriteBinding: FragmentFavoriteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        favoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        favoriteBinding.favoriteViewModel = FavoriteViewModel()
        favoriteBinding.lifecycleOwner = activity

        initLayout()

        return favoriteBinding.root
    }

    private fun initLayout(){
        //리사이클러뷰에 어댑터 연결
        val adapter = DataListAdapter(activity!!.applicationContext , Glide.with(this))
        favoriteBinding.favoriteRcyUser.layoutManager = LinearLayoutManager(activity)
        favoriteBinding.favoriteRcyUser.adapter = adapter
        favoriteBinding.favoriteRcyUser.setHasFixedSize(true)


        favoriteBinding.favoriteViewModel!!.userInfo.observe(this, Observer { data ->
            data.let {
                adapter.setData(it)
            }
        })

        getFavoriteList()
    }

    private fun getFavoriteList(){

        favoriteBinding.favoriteViewModel!!.getFavoriteData(activity!!.applicationContext)
    }
}