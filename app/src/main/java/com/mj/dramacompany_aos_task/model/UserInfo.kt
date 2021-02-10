package com.mj.dramacompany_aos_task.model

class UserInfo {

    var items: ArrayList<Info> = ArrayList()

    data class Info(
        var id: Long ?= 0,
        var login: String? = null,
        var avatar_url: String? = null
    )
}