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
import com.mj.dramacompany_aos_task.config.FIX_PAGE
import com.mj.dramacompany_aos_task.config.FIX_PER_PAGE
import com.mj.dramacompany_aos_task.config.RESTRICT_NAME
import com.mj.dramacompany_aos_task.config.api.RetrofitClient
import com.mj.dramacompany_aos_task.config.api.RetrofitService
import com.mj.dramacompany_aos_task.databinding.FragmentSearchBinding
import com.mj.dramacompany_aos_task.model.UserInfo
import com.mj.dramacompany_aos_task.util.Util
import com.mj.dramacompany_aos_task.viewmodel.FragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FragmentViewModel::class.java)
        binding.lifecycleOwner = activity

        initLayout()

        return binding.root
    }


    //화면 구현에 필요한 요소들을 정의해줍니다.
    private fun initLayout() {

        //리사이클러뷰에 어댑터 연결
        val adapter = DataListAdapter(activity!!.applicationContext, Glide.with(this))
        binding.searchRcyUser.layoutManager = LinearLayoutManager(activity)
        binding.searchRcyUser.adapter = adapter
        binding.searchRcyUser.setHasFixedSize(true)


        //소프트 키보드 확인버튼을 클릭해도 api 호출
        binding.editSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                getSearchData()
            }
            false
        }

        //검색 버튼 클릭시 api 호출
        binding.llSearchBtn.setOnClickListener {
            getSearchData()
        }

        //live 데이터 userInfo 관찰
        binding.viewModel!!.userInfo.observe(this, Observer { data ->

            //데이터를 adapter에 전달하여 row 구성
            data.let {
                adapter.setData(it)
            }
        })
    }

    //검색 api
    private fun getSearchData() {
        var headerMap: Map<String, String> = HashMap()
        headerMap = mapOf("Accept" to "application/vnd.github.v3+json", "Authorization" to "Token 753db49432b57ce5b8859e038d564e966669f4ea")

        RetrofitClient.getInstance()
            .create(RetrofitService::class.java)
            .searchUser(headerMap, binding.viewModel!!.name.value + RESTRICT_NAME, FIX_PAGE, FIX_PER_PAGE).enqueue(object : Callback<UserInfo> {

                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {

                    if (response.isSuccessful) {

                        if (response.body()?.items!!.isEmpty()) {
                            binding.viewModel!!.existData.value = false
                        } else {
                            binding.viewModel!!.existData.value = true
                            binding.viewModel!!.userInfo.value = Util.sortByName(response.body()!!)
                        }


                    } else {
                        binding.viewModel!!.existData.value = false
                    }
                }

                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    binding.viewModel!!.existData.value = false
                }
            })
    }
}