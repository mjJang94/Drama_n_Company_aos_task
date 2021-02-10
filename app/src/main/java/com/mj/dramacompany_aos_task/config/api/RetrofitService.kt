package com.mj.dramacompany_aos_task.config.api

import com.mj.dramacompany_aos_task.model.UserInfo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.Query


interface RetrofitService {


    @GET("users")
    fun searchUser(@HeaderMap headers: Map<String, String>, @Query("q") param: String?, @Query("page") page: Int, @Query("per_page") perPage: Int): Call<UserInfo>

}