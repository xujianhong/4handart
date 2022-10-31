package com.daomingedu.art.bean

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class LoginBean(
    val contactAddress: String,
    val contactPhone: String,
    val info: String,
    val logoPath: String,
    val name: String,
    val sessionId: String
) {
    companion object {
        fun getData(result: String): LoginBean {
            return Gson().fromJson(result, object : TypeToken<LoginBean>() {}.type)
        }
    }
}

