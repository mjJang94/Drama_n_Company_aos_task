package com.mj.dramacompany_aos_task.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mj.dramacompany_aos_task.R
import com.mj.dramacompany_aos_task.config.api.RetrofitClient
import com.mj.dramacompany_aos_task.config.api.RetrofitService
import com.mj.dramacompany_aos_task.databinding.FragmentSearchBinding
import com.mj.dramacompany_aos_task.model.UserInfo
import com.mj.dramacompany_aos_task.ui.adapter.DataListAdapter
import com.mj.dramacompany_aos_task.viewmodel.SearchViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SearchFragment : Fragment() {

    private lateinit var searchDataBinding: FragmentSearchBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        searchDataBinding = FragmentSearchBinding.inflate(inflater, container, false)
        searchDataBinding.searchViewModel = SearchViewModel()
        searchDataBinding.lifecycleOwner = activity

        initLayout()

        return searchDataBinding.root
    }


    //화면 구현에 필요한 요소들을 정의해줍니다.
    private fun initLayout() {


        //리사이클러뷰에 어댑터 연결
        val adapter = DataListAdapter(Glide.with(this), UserInfo())
        searchDataBinding.rcyUser.adapter = adapter
        searchDataBinding.rcyUser.layoutManager = LinearLayoutManager(context)


        searchDataBinding.searchViewModel!!.userInfo.observe(this, Observer { data ->

            //데이터를 adapter에 전달하여 row 구성
            data.let {
                adapter.setData(data)
            }
        })
    }
}