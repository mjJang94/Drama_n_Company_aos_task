package com.mj.dramacompany_aos_task.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.mj.dramacompany_aos_task.database.FavoriteDB
import com.mj.dramacompany_aos_task.database.FavoriteEntity
import com.mj.dramacompany_aos_task.databinding.ListRowBinding
import com.mj.dramacompany_aos_task.model.UserInfo
import com.mj.dramacompany_aos_task.ui.fragment.FavoriteUserFragment
import kotlinx.coroutines.*

/**
 * DataListAdapter.kt
 * 사용자 검색 결과와 즐겨찾기 검색 결과에 대한 데이터들을 리스트로 표현하고, 각각의 동작을 정의하는 adapter 클래스 입니다.
 */
class DataListAdapter(
    val refreshListener: () -> List<Long>,
    val insertListener: (id: Long, login: String, url: String) -> Unit,
    val deleteListener: (id: Long, login: String, url: String) -> Unit,
    val reloadListener: (() -> Unit) ?= null,
    var glide: RequestManager,
    var firstSearch: MutableLiveData<Boolean>
) : RecyclerView.Adapter<DataListAdapter.Holder>() {

    private var userInfo: MutableList<AdapterItem> = ArrayList()

    private lateinit var savedID: List<Long>


    private var initialName: String = ""

    data class AdapterItem(var initial: Any?, var data: UserInfo.Info)

    init {
        refreshSavedIDs()
    }

    fun setData(data: Map<Any?, List<UserInfo.Info>>) {
        refreshSavedIDs()

        userInfo.clear()
        val keySet = data.keys

        for (section in keySet) {

            for (i in data.getValue(section).indices) {
                userInfo.add(AdapterItem(section, data.getValue(section)[i]))
            }
        }
        notifyDataSetChanged()

    }

    private fun refreshSavedIDs() {
        savedID = refreshListener()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val listRowBinding = ListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(listRowBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.bind(userInfo[position], position)
    }

    override fun getItemCount(): Int {
        return userInfo.size
    }

    inner class Holder(private val bind: ListRowBinding) : RecyclerView.ViewHolder(bind.root) {


        fun bind(item: AdapterItem, position: Int) {

            val tmpInitialName = item.initial.toString()

            bind.ivFavorite.isSelected = savedID.contains(item.data.id)

            //저장한 초성과 다를경우 헤더뷰를 노출시키고 다른 초성값 적용
            if (tmpInitialName != initialName || position == 0) {

                initialName = tmpInitialName
                bind.llInitializeArea.visibility = View.VISIBLE
                bind.txtInitializeName.text = tmpInitialName

                firstSearch.value = false
            } else {
                bind.llInitializeArea.visibility = View.GONE
            }

            //프로필 이미지 노출
            glide
                .load(item.data.avatar_url)
                .circleCrop()
                .thumbnail(0.1f)
                .into(bind.ivProfile)

            bind.txtName.text = item.data.login


            //row 클릭시 해당 데이터 저장 및 삭제
            bind.llWhole.setOnClickListener { _ ->

                when (bind.ivFavorite.isSelected) {

                    //즐겨찾기 비활성화
                    true -> {

                        deleteListener(item.data.id!!, item.data.login!!, item.data.avatar_url!!)
                        refreshSavedIDs()
                        reloadListener?.let { it -> it() }

                        bind.ivFavorite.isSelected = false
                    }

                    //즐겨찾기 활성화
                    false -> {

                        insertListener(item.data.id!!, item.data.login!!, item.data.avatar_url!!)
                        refreshSavedIDs()

                        bind.ivFavorite.isSelected = true
                    }
                }
            }
        }
    }
}