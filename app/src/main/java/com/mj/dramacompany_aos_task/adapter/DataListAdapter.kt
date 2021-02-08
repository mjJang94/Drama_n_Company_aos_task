package com.mj.dramacompany_aos_task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.mj.dramacompany_aos_task.databinding.ListRowBinding
import com.mj.dramacompany_aos_task.model.UserInfo

class DataListAdapter(var glide: RequestManager) : RecyclerView.Adapter<DataListAdapter.Holder>() {

    private var userInfo: MutableList<AdapterItem> = ArrayList()
    private var initialName: String = ""


    data class AdapterItem(var initial: Any?, var data: UserInfo.Info)

    fun setData(data: Map<Any?, List<UserInfo.Info>>) {
        userInfo.clear()
        val keySet = data.keys

        for (section in keySet) {

            for (i in data.getValue(section).indices) {
                userInfo.add(AdapterItem(section, data.getValue(section)[i]))
            }
        }
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val listRowBinding = ListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(listRowBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.bind(userInfo[position])
    }

    override fun getItemCount(): Int {
        return userInfo.size
    }

    inner class Holder(private val bind: ListRowBinding) : RecyclerView.ViewHolder(bind.root) {


        fun bind(data: AdapterItem) {

            val tmpInitialName = data.initial.toString()

            //저장한 초성과 다를경우 헤더뷰를 노출시키고 다른 초성값 적용
            if (tmpInitialName != initialName) {
                initialName = tmpInitialName
                bind.llInitializeArea.visibility = View.VISIBLE
                bind.txtInitializeName.text = tmpInitialName
            } else {
                bind.llInitializeArea.visibility = View.GONE
            }

            //프로필 이미지 노출
            glide
                .load(data.data.avatar_url)
                .circleCrop()
                .thumbnail(0.1f)
                .into(bind.ivProfile)


            bind.txtName.text = data.data.login

            //row 클릭시 해당 데이터 저장 및 삭제
            bind.llWhole.setOnClickListener {
                bind.ivFavorite.isSelected = true
            }
        }
    }
}