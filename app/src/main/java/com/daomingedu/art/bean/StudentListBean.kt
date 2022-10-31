package com.daomingedu.art.bean

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class StudentListBean(
    val levelName: String,
    val majorNname: String,
    val studentName: String,
    val testNumber: String
){
    companion object {
        fun getListData(result: String): ArrayList<StudentListBean> {
            return Gson().fromJson(result, object : TypeToken<ArrayList<StudentListBean>>() {}.type)
        }
    }
}