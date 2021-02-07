package com.mj.dramacompany_aos_task.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mj.dramacompany_aos_task.config.FIX_PAGE
import com.mj.dramacompany_aos_task.config.FIX_PER_PAGE
import com.mj.dramacompany_aos_task.config.api.RetrofitClient
import com.mj.dramacompany_aos_task.config.api.RetrofitService
import com.mj.dramacompany_aos_task.model.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class SearchViewModel: ViewModel(){

    var name: MutableLiveData<String> = MutableLiveData()
    var userInfo: MutableLiveData<UserInfo> = MutableLiveData()
    var existData: MutableLiveData<Boolean> = MutableLiveData(false)



    //검색 api
    fun getSearchData() {
        RetrofitClient.getInstance()
            .create(RetrofitService::class.java)
            .searchUser(name.value, FIX_PAGE, FIX_PER_PAGE).enqueue(object : Callback<UserInfo> {

            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {

                if (response.isSuccessful){

                    existData.value = response.body()?.items?.size != 0
                    sortByName(response.body()!!)

                }else{
                    existData.value = false
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                existData.value = false
            }
        })
    }

    //이름 순으로 정렬
    fun sortByName(data: UserInfo) {
        data.items.sortWith(Comparator { item1, item2 ->
             item1.login!!.compareTo(item2.login!!)
        })

        userInfo.value = data
    }
}