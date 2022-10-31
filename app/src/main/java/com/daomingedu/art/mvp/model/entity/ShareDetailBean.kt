package com.daomingedu.art.mvp.model.entity

data class ShareDetailBean(
    val commentList: List<Comment>,
    val isLike: Int,
    val isMy: Int
)