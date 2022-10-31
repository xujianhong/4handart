package com.daomingedu.art.bean

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class ConfirmBean(
    val isConfirm: Int,
    val confirmMsg: String
) {
    companion object {
        fun getData(result: String): ConfirmBean {
            return Gson().fromJson(result, object : TypeToken<ConfirmBean>() {}.type)
        }
    }
}