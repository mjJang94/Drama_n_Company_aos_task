package com.mj.dramacompany_aos_task.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.mj.dramacompany_aos_task.model.UserInfo
import kotlinx.coroutines.*

class FavoriteRepository(application: Application) {

    private val favoriteDatabase = FavoriteDB.getInstance(application)!!
    private val favoriteDao: FavoriteDao = favoriteDatabase.dao()

    // 검색어로 입력한 사용자이름에 매칭된 데이터 검색
    fun searchFavoriteByName(name: LiveData<String>): UserInfo {

        val userInfo = UserInfo()

        runBlocking(Dispatchers.IO) {

            val tmpItems = favoriteDao.getDataByLogin(name.value + "%").map { UserInfo.Info(it.id, it.login, it.avatar_url) }

            for (data in tmpItems) {
                userInfo.items.add(data)
            }
        }

        return userInfo
    }

    fun delete(entity: FavoriteEntity) {

        GlobalScope.launch(Dispatchers.IO) {

            favoriteDao.delete(entity)
        }
    }

    fun getID(): List<Long>{

        var savedID: List<Long>

        runBlocking(Dispatchers.IO) {
            savedID = favoriteDao.getID()
        }

        return savedID
    }

    fun insertData(entity: FavoriteEntity){
        GlobalScope.launch(Dispatchers.IO) {
            favoriteDao.insertData(entity)
        }
    }

}