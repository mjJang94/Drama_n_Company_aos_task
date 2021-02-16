package com.mj.dramacompany_aos_task.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.mj.dramacompany_aos_task.model.UserInfo

open class Util {

    companion object {

        fun hideKeyBoard(context: Context, view: View) {
            val imm: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}