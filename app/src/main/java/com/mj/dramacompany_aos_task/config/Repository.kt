package com.mj.dramacompany_aos_task.config

import android.content.Context
import androidx.lifecycle.LiveData
import com.mj.dramacompany_aos_task.config.api.RetrofitClient
import com.mj.dramacompany_aos_task.config.api.RetrofitService
import com.mj.dramacompany_aos_task.config.database.FavoriteDB
import com.mj.dramacompany_aos_task.config.database.FavoriteDao
import com.mj.dramacompany_aos_task.config.database.FavoriteEntity
import com.mj.dramacompany_aos_task.model.UserInfo
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(context: Context) {

    private val favoriteDatabase = FavoriteDB.getInstance(context.applicationContext)!!
    private val favoriteDao: FavoriteDao = favoriteDatabase.dao()

    // 검색어로 입력한 사용자이름에 매칭된 데이터 검색
//    fun searchFavoriteByName(name: LiveData<String>): UserInfo = runBlocking{
//
//        val userInfo = UserInfo()
//        val tmpItems = favoriteDao.getDataByLogin(name.value + "%").map { UserInfo.Info(it.id, it.login, it.avatar_url) }
//
//        for (data in tmpItems) {
//            userInfo.items.add(data)
//        }
//
//        return@runBlocking userInfo
//    }
    fun searchFavoriteByName(name: LiveData<String>, completion: (UserInfo) -> Unit){

        GlobalScope.launch(Dispatchers.IO) {
            val userInfo = UserInfo()
            val tmpItems = favoriteDao.getDataByLogin(name.value + "%").map { UserInfo.Info(it.id, it.login, it.avatar_url) }

            for (data in tmpItems) {
                userInfo.items.add(data)
            }

            withContext(Dispatchers.Main){
                completion(userInfo)
            }
        }
    }

    fun delete(entity: FavoriteEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            favoriteDao.delete(entity)
        }

    }

    fun getID(): List<Long> = runBlocking {
        val savedID: List<Long>
        savedID = favoriteDao.getID().toMutableList()

        return@runBlocking savedID
    }

    fun insertData(entity: FavoriteEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            favoriteDao.insertData(entity)
        }
    }

    fun getSearchData(name: LiveData<String>, onSuccess: (info: UserInfo) -> Unit, onFail: (failCode: Int?) -> Unit) {
        val headerMap = mapOf(ACCEPT to MEDIA_TYPE)

        RetrofitClient.getInstance()
            .create(RetrofitService::class.java)
            .searchUser(headerMap, name.value + RESTRICT_NAME, FIX_PAGE, FIX_PER_PAGE).enqueue(object : Callback<UserInfo> {

                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {

                    if (response.isSuccessful) {

                        if (response.body()?.items!!.isEmpty()) {
                            onFail(null)
                        } else {
                            onSuccess(response.body()!!)
                        }

                    } else {
                        onFail(response.code())
                    }
                }

                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    onFail(null)
                }
            })
    }

}