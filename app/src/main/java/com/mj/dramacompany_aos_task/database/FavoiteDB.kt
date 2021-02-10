package com.mj.dramacompany_aos_task.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class FavoiteDB : RoomDatabase() {

    abstract fun dao(): FavoriteDao

    companion object {

        private var instance: FavoiteDB? = null

        fun getInstance(context: Context): FavoiteDB? {
            if (instance == null) {
                synchronized(FavoiteDB::class.java) {
                    instance = Room.databaseBuilder(
                        context,
                        FavoiteDB::class.java, "favorite_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return instance
        }
    }
}
