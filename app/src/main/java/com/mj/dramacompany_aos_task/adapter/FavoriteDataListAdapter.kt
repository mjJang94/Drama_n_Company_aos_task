package com.mj.dramacompany_aos_task.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.RoomDatabase
import com.bumptech.glide.RequestManager
import com.mj.dramacompany_aos_task.database.FavoiteDB
import com.mj.dramacompany_aos_task.database.FavoriteEntity
import com.mj.dramacompany_aos_task.databinding.ListRowBinding
import com.mj.dramacompany_aos_task.model.UserInfo
import com.mj.dramacompany_aos_task.viewmodel.FavoriteViewModel
import kotlinx.coroutines.*
import kotlin.math.sign

class FavoriteDataListAdapter(val applicationContext: Context, var glide: RequestManager, var favoriteViewModel: FavoriteViewModel) : RecyclerView.Adapter<FavoriteDataListAdapter.Holder>() {

    private var userInfo: MutableList<AdapterItem> = ArrayList()

    private var initialName: String = ""

//    private lateinit var savedID: List<Long>

    private var favoriteDB: FavoiteDB = FavoiteDB.getInstance(applicationContext)!!

    data class AdapterItem(var initial: Any?, var data: FavoriteEntity)

//    init {
//
//        GlobalScope.launch(Dispatchers.IO) {
//            savedID = favoriteDB.dao().getID()
//        }
//
//    }

    fun setData(data: Map<Any?, List<FavoriteEntity>>) {
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


        return Holder(listRowBinding, applicationContext)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.bind(userInfo[position])
    }

    override fun getItemCount(): Int {
        return userInfo.size
    }

    inner class Holder(private val bind: ListRowBinding, private val context: Context) : RecyclerView.ViewHolder(bind.root) {


        fun bind(data: AdapterItem) {

            val tmpInitialName = data.initial.toString()


//            for (savedValue in savedID) {
//
//                if (data.data.id == savedValue) {
                    bind.ivFavorite.isSelected = true
//                }
//            }


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

//                when (bind.ivFavorite.isSelected) {

                    //즐겨찾기 비활성화
//                    true -> {

                        GlobalScope.launch(Dispatchers.IO) {

                            favoriteDB.dao().delete(FavoriteEntity(data.data.id, data.data.login, data.data.avatar_url))
                            favoriteViewModel.getFavoriteData(applicationContext)

//                            savedID = favoriteDB.dao().getID()

                            withContext(Dispatchers.Main) {
//                                bind.ivFavorite.isSelected = false
                                val sfs = favoriteDB.dao().getID()
                                for (vv in sfs){
                                    Log.e("delete", vv.toString())
                                }
                            }
                        }
//                    }

//                    //즐겨찾기 활성화
//                    false -> {
//
//                        GlobalScope.launch(Dispatchers.IO) {
//
//                            favoriteDB.dao().insertData(FavoriteEntity(data.data.id, data.data.login, data.data.avatar_url))
////                            savedID = favoriteDB.dao().getID()
//
//                            withContext(Dispatchers.Main) {
//                                bind.ivFavorite.isSelected = true
//
//                                val sfs = favoriteDB.dao().getID()
//                                for (vv in sfs){
//                                    Log.e("save", vv.toString())
//                                }
//                            }
//                        }
//                    }
//                }
            }
        }
    }
}