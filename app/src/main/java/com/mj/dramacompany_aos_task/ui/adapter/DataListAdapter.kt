package com.mj.dramacompany_aos_task.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.mj.dramacompany_aos_task.databinding.ListRowBinding
import com.mj.dramacompany_aos_task.model.UserInfo

class DataListAdapter(var glide: RequestManager, private var userInfo: UserInfo) : RecyclerView.Adapter<DataListAdapter.Holder>() {

    lateinit var binding: ListRowBinding
//    private var userInfo = ArrayList<UserInfo.Info>()


    fun setData(data: UserInfo) {
        userInfo = data
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        binding = ListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.bind(userInfo.items[position])
    }

    override fun getItemCount(): Int {
        return userInfo.items.size
    }

    inner class Holder : RecyclerView.ViewHolder(binding.root) {



        fun bind(data: UserInfo.Info) {

            //프로필 이미지 노출
            glide
                .load(data.avatar_url)
                .circleCrop()
                .thumbnail(0.1f)
                .into(binding.ivProfile)


            binding.txtName.text = data.login

            //row 클릭시 해당 데이터 저장 및 삭제
            binding.llWhole.setOnClickListener {
                binding.ivFavorite.isSelected = true
            }
        }
    }
}