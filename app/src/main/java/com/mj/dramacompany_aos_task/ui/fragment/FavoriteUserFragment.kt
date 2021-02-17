package com.mj.dramacompany_aos_task.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mj.dramacompany_aos_task.adapter.DataListAdapter
import com.mj.dramacompany_aos_task.config.Repository
import com.mj.dramacompany_aos_task.databinding.FragmentFavoriteBinding
import com.mj.dramacompany_aos_task.viewmodel.FragmentViewModel

/**
 * FavoriteUserFragment.kt
 * SearchUserFragment 에서 즐겨찾기한 사용자를 검색하고, 즐겨찾기를 취소할 수 있는 fragment 입니다.
 */
class FavoriteUserFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var adapter: DataListAdapter
    private lateinit var repository : Repository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //외부 repository 생성
        repository = Repository(requireActivity().application)

        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProvider(requireActivity(), FragmentViewModel.Factory(requireActivity().application, repository)).get(FragmentViewModel::class.java)
        binding.lifecycleOwner = activity

        initLayout()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if (binding.viewModel!!.favoriteName.value.isNullOrEmpty()) {
            binding.viewModel!!.favoriteName.value = null
        }

        binding.viewModel!!.searchFavoriteByName()
    }

    private fun initLayout() {
        //리사이클러뷰에 어댑터 연결
        adapter = DataListAdapter({
            //refreshListener
            binding.viewModel!!.getIds()
        }, { id, login, url ->
            //insertListener
            binding.viewModel!!.insert(id, login, url)

        }, { id, login, url ->
            //deleteListener
            binding.viewModel!!.delete(id, login, url)

        }, {
            //reloadListener
            binding.viewModel!!.searchFavoriteByName()

        }, Glide.with(this))

        binding.favoriteRcyUser.layoutManager = LinearLayoutManager(activity)
        binding.favoriteRcyUser.adapter = adapter
        binding.favoriteRcyUser.setHasFixedSize(true)

        //소프트 키보드 확인버튼을 클릭해도 api 호출
        binding.editFavorite.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.viewModel!!.searchFavoriteByName()
            }
            false
        }

        //검색 버튼 클릭시 api 호출
        binding.llFavoriteBtn.setOnClickListener {
            binding.viewModel!!.searchFavoriteByName()
        }

        binding.viewModel!!.userInfo.observe(this, Observer { data ->
            data.let {
                adapter.setData(it)
            }
        })
    }
}