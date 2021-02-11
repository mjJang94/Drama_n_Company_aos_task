package com.mj.dramacompany_aos_task.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mj.dramacompany_aos_task.model.UserInfo

/**
 * FragmentViewModel.kt
 * SearchUserFragment, FavoriteUserFragment가 공통으로 참조하는 viewmodel 입니다.
 */
class FragmentViewModel : ViewModel() {

    var name: MutableLiveData<String> = MutableLiveData()
    var userInfo: MutableLiveData<Map<Any?, List<UserInfo.Info>>> = MutableLiveData()
    var existData: MutableLiveData<Boolean> = MutableLiveData(false)
    var firstSearch: MutableLiveData<Boolean> = MutableLiveData(false)

}

