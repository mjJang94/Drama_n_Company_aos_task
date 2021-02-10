package com.mj.dramacompany_aos_task.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mj.dramacompany_aos_task.adapter.DataListAdapter
import com.mj.dramacompany_aos_task.database.FavoiteDB
import com.mj.dramacompany_aos_task.databinding.FragmentFavoriteBinding
import com.mj.dramacompany_aos_task.databinding.FragmentSearchBinding
import com.mj.dramacompany_aos_task.model.UserInfo
import com.mj.dramacompany_aos_task.util.Util
import com.mj.dramacompany_aos_task.viewmodel.FragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteDB: FavoiteDB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FragmentViewModel::class.java)
        binding.lifecycleOwner = activity

        favoriteDB = FavoiteDB.getInstance(activity!!.applicationContext)!!

        initLayout()

        return binding.root
    }

    private fun initLayout(){
        //리사이클러뷰에 어댑터 연결
        val adapter = DataListAdapter(activity!!.applicationContext , Glide.with(this))
        binding.searchRcyUser.layoutManager = LinearLayoutManager(activity)
        binding.searchRcyUser.adapter = adapter
        binding.searchRcyUser.setHasFixedSize(true)

        //소프트 키보드 확인버튼을 클릭해도 api 호출
        binding.editSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchFavoriteByName()
            }
            false
        }

        //검색 버튼 클릭시 api 호출
        binding.llFavoriteBtn.setOnClickListener {
            searchFavoriteByName()
        }

        binding.viewModel!!.userInfo.observe(this, Observer { data ->
            data.let {
                adapter.setData(it)
            }
        })
    }

    // 검색어로 입력한 사용자이름에 매칭된 데이터 검색
    private fun searchFavoriteByName(){

        GlobalScope.launch(Dispatchers.IO) {

            val tmpItems = favoriteDB.dao().getDataByLogin(binding.viewModel!!.name.value!! + "%").map { UserInfo.Info(it.id, it.login, it.avatar_url) }
            val userInfo = UserInfo()

            for (data in tmpItems) {
                userInfo.items.add(data)
            }

            withContext(Dispatchers.Main) {

                if (userInfo.items.isEmpty()) {
                    binding.viewModel!!.existData.value = false
                } else {
                    binding.viewModel!!.existData.value = true
                    binding.viewModel!!.userInfo.value = Util.sortByName(userInfo)
                }

            }
        }
    }
}