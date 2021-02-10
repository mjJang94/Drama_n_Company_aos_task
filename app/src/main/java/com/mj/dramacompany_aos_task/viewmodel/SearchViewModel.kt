package com.mj.dramacompany_aos_task.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mj.dramacompany_aos_task.config.FIX_PAGE
import com.mj.dramacompany_aos_task.config.FIX_PER_PAGE
import com.mj.dramacompany_aos_task.config.RESTRICT_NAME
import com.mj.dramacompany_aos_task.config.api.RetrofitClient
import com.mj.dramacompany_aos_task.config.api.RetrofitService
import com.mj.dramacompany_aos_task.model.UserInfo
import com.mj.dramacompany_aos_task.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    var name: MutableLiveData<String> = MutableLiveData()
    var userInfo: MutableLiveData<Map<Any?, List<UserInfo.Info>>> = MutableLiveData()
    var existData: MutableLiveData<Boolean> = MutableLiveData(false)


    //검색 api
    fun getSearchData() {

        RetrofitClient.getInstance()
            .create(RetrofitService::class.java)
            .searchUser(name.value+RESTRICT_NAME, FIX_PAGE, FIX_PER_PAGE).enqueue(object : Callback<UserInfo> {

                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {

                    if (response.isSuccessful) {

                        existData.value = response.body()?.items?.size != 0

                        sortByName(response.body()!!)

                    } else {
                        existData.value = false
                    }
                }
                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    existData.value = false
                }
            })
    }

    //이름 순으로 정렬하고 초성별로 그룹핑 실행
    private fun sortByName(data: UserInfo) {

        data.items.sortWith(Comparator { item1, item2 ->

            item1.login!!.toUpperCase().compareTo(item2.login!!.toUpperCase())
        })

        val map: Map<Any?, List<UserInfo.Info>> = data.items.groupBy {

            val tmp = it.login?.get(0)

            if (Util.isKorean(tmp!!)) {
                Util.getInitialSound(it.login.toString())
            } else {
                tmp.toUpperCase()
            }

        }

        userInfo.value = map
    }


}