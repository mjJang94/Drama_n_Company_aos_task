package com.mj.dramacompany_aos_task.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class FavoriteDB : RoomDatabase() {

    abstract fun dao(): FavoriteDao

    companion object {

        private var instance: FavoriteDB? = null

        fun getInstance(context: Context): FavoriteDB? {
            if (instance == null) {
                synchronized(FavoriteDB::class.java) {
                    instance = Room.databaseBuilder(
                        context,
                        FavoriteDB::class.java, "favorite_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return instance
        }
    }
}
