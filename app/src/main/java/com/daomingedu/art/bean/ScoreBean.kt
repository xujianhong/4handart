package com.daomingedu.art.bean

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class ScoreBean(
    val passScore: String,
    val score: String,
    val isAudit: Int,
    val text: String,
    val isUpload: Int
){
    companion object {
        fun getData(result: String): ScoreBean {
            return Gson().fromJson(result, object : TypeToken<ScoreBean>() {}.type)
        }
    }
}