package com.daomingedu.art.mvp.model.entity

import java.io.Serializable

data class ShareBean(
    val isMy:Int,
    val id:String,
    val userId:String,
    val shareUserName:String,
    val shareUserImg:String,
    val remark:String,
    val shareTime:String,
    val shareType:Int,
    var likeCount:Int,
    var viewCount:Int,
    val previewImage:String,
    val urls:String,
    val smallUrls:String,
    val nickName:String,
    val realName:String,
    val filePath:String,
    var commentCount:Int
):Serializable