package com.mj.dramacompany_aos_task.config.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
open class FavoriteEntity (
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "login") var login: String,
    @ColumnInfo(name = "avatar_url") var avatar_url: String
){
    constructor() : this(0, "", "")
}