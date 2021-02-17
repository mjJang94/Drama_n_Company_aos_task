package com.mj.dramacompany_aos_task.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mj.dramacompany_aos_task.R
import com.mj.dramacompany_aos_task.adapter.DataListAdapter
import com.mj.dramacompany_aos_task.config.*
import com.mj.dramacompany_aos_task.databinding.FragmentSearchBinding
import com.mj.dramacompany_aos_task.viewmodel.FragmentViewModel

/**
 * SearchUserFragment.kt
 * Github api를 통해 유저 검색 기능과 즐겨찾기 기능을 포함한 fragment 입니다.
 */
class SearchUserFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: DataListAdapter
    private lateinit var repository : Repository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //외부 repository 생성
        repository = Repository(requireActivity().application)

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProvider(requireActivity(), FragmentViewModel.Factory(requireActivity().application, repository)).get(FragmentViewModel::class.java)
        binding.lifecycleOwner = activity

        initLayout()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if (!binding.viewModel!!.searchName.value.isNullOrEmpty()) {
            binding.viewModel!!.searchUser()
        }
    }

    //화면 구현에 필요한 요소들을 정의해줍니다.
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
            binding.viewModel!!.searchUser()
        }, Glide.with(this))

        binding.searchRcyUser.layoutManager = LinearLayoutManager(activity)
        binding.searchRcyUser.adapter = adapter
        binding.searchRcyUser.setHasFixedSize(true)


        //소프트 키보드 확인버튼을 클릭해도 api 호출
        binding.editSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.viewModel!!.searchUser()
            }
            false
        }

        //검색 버튼 클릭시 api 호출
        binding.llSearchBtn.setOnClickListener {
            binding.viewModel!!.searchUser()
        }

        //live 데이터 userInfo 관찰
        binding.viewModel!!.userInfo.observe(this, Observer { data ->
            //데이터를 adapter에 전달하여 row 구성
            data.let {
                adapter.setData(it)
            }
        })

        binding.viewModel!!.apiResponseError = { code ->
            when (code) {

                NOT_MODIFIED -> {
                    Toast.makeText(activity!!.applicationContext, getString(R.string.error_not_modified), Toast.LENGTH_SHORT).show()
                }

                VALIDATION_FAILED -> {
                    Toast.makeText(activity!!.applicationContext, getString(R.string.error_unprocessable_entity), Toast.LENGTH_SHORT).show()
                }

                SERVICE_UNAVAILABLE -> {
                    Toast.makeText(activity!!.applicationContext, getString(R.string.error_service_unavailable), Toast.LENGTH_SHORT).show()
                }

                NO_TOKEN -> {
                    Toast.makeText(activity!!.applicationContext, getString(R.string.error_token_expired), Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(activity!!.applicationContext, getString(R.string.error_unknown) + code, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}