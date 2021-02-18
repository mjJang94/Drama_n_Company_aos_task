package com.mj.dramacompany_aos_task.viewmodel

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mj.dramacompany_aos_task.config.Repository
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.manipulation.Ordering
import org.mockito.Mock

class FragmentViewModelTest {

    @Mock
    private val application : Application = Application()

    val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun getSearchName() {
        val repository= Repository(context)

        val viewmodel = FragmentViewModel(application, repository)

        assertEquals(true, viewmodel.isKorean('ㅁ'))
    }


    @Test
    fun setSearchName() {
    }
}