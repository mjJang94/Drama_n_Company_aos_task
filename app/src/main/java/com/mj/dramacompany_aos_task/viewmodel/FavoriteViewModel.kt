package com.mj.dramacompany_aos_task.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mj.dramacompany_aos_task.database.FavoiteDB
import com.mj.dramacompany_aos_task.model.UserInfo
import com.mj.dramacompany_aos_task.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel : ViewModel() {

    var userInfo: MutableLiveData<Map<Any?, List<UserInfo.Info>>> = MutableLiveData()
    var existData: MutableLiveData<Boolean> = MutableLiveData(false)

    private var tmpItems: List<UserInfo.Info> ?= null

    // db에서 즐겨찾기 데이터 불러오는 메소드
    fun getFavoriteData(context: Context) {

        val favoriteDB: FavoiteDB = FavoiteDB.getInstance(context)!!

        GlobalScope.launch(Dispatchers.IO) {

            //Entity 를 UserInfo 로 변환
            tmpItems = favoriteDB.dao().getAll().map { UserInfo.Info(it.id, it.login, it.avatar_url) }
            val userInfo = UserInfo()

            for (data in tmpItems!!) {
                userInfo.items.add(data)
            }


            withContext(Dispatchers.Main){

                if (userInfo.items.isEmpty()){
                    existData.value = false
                }else{
                    existData.value = true
                    sortByName(userInfo)
                }

            }
        }
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

//d
    //이름 순으로 정렬하고 초성별로 그룹핑 실행
//    private fun sortByName(data: MutableList<UserInfo.Info>) {
//
//        data.sortWith(Comparator { item1, item2 ->
//
//            item1.login.toUpperCase().compareTo(item2.login!!.toUpperCase())
//        })
//
//        val map: Map<Any?, List<FavoriteEntity>> = data.groupBy {
//
//            val tmp = it.login[0]
//
//            if (Util.isKorean(tmp)) {
//                Util.getInitialSound(it.login.toString())
//            } else {
//                tmp.toUpperCase()
//            }
//
//        }
//
//        userInfo.value = map
//    }
}