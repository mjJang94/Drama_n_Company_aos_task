package com.mj.dramacompany_aos_task.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mj.dramacompany_aos_task.config.*
import com.mj.dramacompany_aos_task.config.api.RetrofitClient
import com.mj.dramacompany_aos_task.config.api.RetrofitService
import com.mj.dramacompany_aos_task.database.FavoriteEntity
import com.mj.dramacompany_aos_task.database.FavoriteRepository
import com.mj.dramacompany_aos_task.model.UserInfo
import com.mj.dramacompany_aos_task.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * FragmentViewModel.kt
 * SearchUserFragment, FavoriteUserFragment가 공통으로 참조하는 viewmodel 입니다.
 */

class FragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FavoriteRepository(application)

    var searchName: MutableLiveData<String> = MutableLiveData()
    var favoriteName: MutableLiveData<String> = MutableLiveData()
    var userInfo: MutableLiveData<Map<Any?, List<UserInfo.Info>>> = MutableLiveData()
    var existData: MutableLiveData<Boolean> = MutableLiveData(false)
    var firstSearch: MutableLiveData<Boolean> = MutableLiveData(false)

    var apiResponseError: ((code: Int) -> Unit)? = null


    // 검색어로 입력한 사용자이름에 매칭된 데이터 검색
    fun searchFavoriteByName() {

        val info = repository.searchFavoriteByName(favoriteName)

        if (info.items.isEmpty()) {
            existData.value = false
        } else {
            existData.value = true
            userInfo.value = sortByName(info)
        }
        firstSearch.value = true
    }

    fun getIds(): List<Long> {

        return repository.getID()
    }

    fun delete(id: Long, login: String, url: String) {
        repository.delete(FavoriteEntity(id, login, url))
    }

    fun insert(id: Long, login: String, url: String) {
        repository.insertData(FavoriteEntity(id, login, url))
    }


    //검색 api
    fun getSearchData() {
        val headerMap = mapOf(ACCEPT to MEDIA_TYPE)

        RetrofitClient.getInstance()
            .create(RetrofitService::class.java)
            .searchUser(headerMap, searchName.value + RESTRICT_NAME, FIX_PAGE, FIX_PER_PAGE).enqueue(object : Callback<UserInfo> {

                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {

                    if (response.isSuccessful) {

                        if (response.body()?.items!!.isEmpty()) {
                            existData.value = false
                        } else {
                            existData.value = true
                            userInfo.value = sortByName(response.body()!!)
                        }

                    } else {
                        apiResponseError?.let { it(response.code()) }
                        existData.value = false
                    }
                }

                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    existData.value = false
                }
            })
        firstSearch.value = true
    }

    //이름 순으로 정렬하고 초성별로 그룹핑 실행
    private fun sortByName(data: UserInfo): Map<Any?, List<UserInfo.Info>> {

        data.items.sortWith(Comparator { item1, item2 ->

            item1.login!!.toUpperCase().compareTo(item2.login!!.toUpperCase())
        })

        return data.items.groupBy {

            val tmp = it.login?.get(0)

            if (isKorean(tmp!!)) {
                getInitialSound(it.login.toString())
            } else {
                tmp?.toUpperCase()
            }
        }
    }

    //한글에서 맨 앞글자의 초성을 구하는 메소드
    private fun getInitialSound(text: String): String? {
        val chs = arrayOf(
            "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ",
            "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ",
            "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ",
            "ㅋ", "ㅌ", "ㅍ", "ㅎ"
        )
        if (text.isNotEmpty()) {
            val chName = text[0]
            if (chName.toInt() >= 0xAC00) {
                val uniVal = chName.toInt() - 0xAC00
                val cho = (uniVal - uniVal % 28) / 28 / 21
                return chs[cho]
            }
        }
        return null
    }

    //들어온 char 값이 한국어인지 아닌지 구분
    private fun isKorean(ch: Char): Boolean {
        return ch.toInt() >= "AC00".toInt(16) && ch.toInt() <= "D7A3".toInt(16)
    }
    class Factory(private val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FragmentViewModel(application) as T
        }
    }
}

