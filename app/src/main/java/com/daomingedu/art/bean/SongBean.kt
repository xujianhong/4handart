package com.daomingedu.art.bean

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class SongBean(
    val isLimitTime: Int,
    val remark: String,
    val musicSong: SongBeanMusicSong,
    val majorLevelSongList: ArrayList<SongBeanMajorLevelSong>
){
    companion object {
        fun getData(result: String): SongBean {
            return Gson().fromJson(result, object : TypeToken<SongBean>() {}.type)
        }
    }
}

data class SongBeanMusicSong(
    val id: String,
    val img: String,
    val musicLevelAssignId: String,
    val name: String,
    val sortNo: Int
)

data class SongBeanMajorLevelSong(
    val id: String,
    val majorId: String,
    val majorLevelId: String,
    val musicTypeId: String,
    val name: String,
    val sortNo: Int
)