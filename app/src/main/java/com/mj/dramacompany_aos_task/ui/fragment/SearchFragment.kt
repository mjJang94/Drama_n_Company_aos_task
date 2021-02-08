package com.mj.dramacompany_aos_task.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mj.dramacompany_aos_task.databinding.FragmentSearchBinding
import com.mj.dramacompany_aos_task.adapter.DataListAdapter
import com.mj.dramacompany_aos_task.viewmodel.SearchViewModel

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
        val adapter = DataListAdapter(Glide.with(this))
        searchDataBinding.rcyUser.layoutManager = LinearLayoutManager(activity)
        searchDataBinding.rcyUser.adapter = adapter
        searchDataBinding.rcyUser.setHasFixedSize(true)


        searchDataBinding.searchViewModel!!.userInfo.observe(this, Observer { data ->

            //데이터를 adapter에 전달하여 row 구성
            data.let {
                adapter.setData(data)
            }
        })
    }
}