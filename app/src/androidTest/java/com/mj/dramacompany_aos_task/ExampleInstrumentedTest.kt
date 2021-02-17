package com.mj.dramacompany_aos_task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mj.dramacompany_aos_task.config.Repository
import com.mj.dramacompany_aos_task.config.database.FavoriteDB
import com.mj.dramacompany_aos_task.config.database.FavoriteDao
import com.mj.dramacompany_aos_task.config.database.FavoriteEntity
import com.mj.dramacompany_aos_task.model.UserInfo
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.mj.dramacompany_aos_task", appContext.packageName)
    }

    @Test
    fun roomTest() = runBlocking{
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val favoriteDatabase = FavoriteDB.getInstance(appContext.applicationContext)!!
        val favoriteDao: FavoriteDao = favoriteDatabase.dao()

        val user = UserInfo()
        user.items.add(UserInfo.Info(1, "장민종", "www.naver.com"))

        //given
        favoriteDao.insertData( FavoriteEntity(1, "장민종", "www.naver.com"))

        //when
        val userFromDB = favoriteDao.getDataByLogin("장민종")

        //then
        assertEquals( user.items[0].id, userFromDB[0].id)
    }
}