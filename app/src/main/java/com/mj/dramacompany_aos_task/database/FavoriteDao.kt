package com.mj.dramacompany_aos_task.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.mj.dramacompany_aos_task.model.UserInfo

@Dao
interface FavoriteDao {

    //id 값만 전부 가져오기
    @Query("select id from favorite")
    suspend fun getID(): List<Long>

    //해당 데이터 삽입
    @Insert(onConflict = REPLACE)
    suspend fun insertData(favoriteEntity: FavoriteEntity)

    //해당 데이터 삭제
    @Delete
    suspend fun delete(favoriteEntity: FavoriteEntity)

    //모든 데이터 가져오기
    @Query("select * from favorite")
    suspend fun getAll(): List<FavoriteEntity>

    //아이디 값으로 데이터 가져오기
    @Query("select * from favorite where login like :login")
    suspend fun getDataByLogin(login: String): List<FavoriteEntity>
}