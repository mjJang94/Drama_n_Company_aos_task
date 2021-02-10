package com.mj.dramacompany_aos_task.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mj.dramacompany_aos_task.model.UserInfo

class FragmentViewModel : ViewModel() {

    var name: MutableLiveData<String> = MutableLiveData()
    var userInfo: MutableLiveData<Map<Any?, List<UserInfo.Info>>> = MutableLiveData()
    var existData: MutableLiveData<Boolean> = MutableLiveData(false)

}

