package com.mj.dramacompany_aos_task.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

open class Util {

    companion object{


        //한글에서 맨 앞글자의 초성을 구하는 메소드
        fun getInitialSound(text: String): String? {
            val chs = arrayOf(
                "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ",
                "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ",
                "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ",
                "ㅋ", "ㅌ", "ㅍ", "ㅎ"
            )
            if (text.isNotEmpty()) {
                val chName = text[0]
                if (chName.toInt() >= 0xAC00) {
                    val uniVal = chName.toInt() - 0xAC00
                    val cho = (uniVal - uniVal % 28) / 28 / 21
                    return chs[cho]
                }
            }
            return null
        }

        //들어온 char 값이 한국어인지 아닌지 구분
        fun isKorean(ch: Char): Boolean {
            return ch.toInt() >= "AC00".toInt(16) && ch.toInt() <= "D7A3".toInt(16)
        }

        fun hideKeyBoard(context: Context, view: View) {
            val imm: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}