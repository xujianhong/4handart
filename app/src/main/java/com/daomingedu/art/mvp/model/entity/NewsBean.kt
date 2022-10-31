package com.daomingedu.art.mvp.model.entity

data class NewsBean(
    val content: String,
    val createTime: Any,
    val createUser: String,
    val id: String,
    val image: String,
    val lastUpdateTime: Any,
    val lastUpdateUser: String,
    val sortNo: Int,
    val source: String,
    val state: Int,
    val title: String,
    val url: String
)