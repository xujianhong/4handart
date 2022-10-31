package com.daomingedu.art.bean

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class KeyBean(
    val bucketName: String,
    val cosTempKey: KeyBeanCosTempKey,
    val region: String
){
    companion object {
        fun getData(result: String): KeyBean {
            return Gson().fromJson(result, object : TypeToken<KeyBean>() {}.type)
        }
    }
}

data class KeyBeanCosTempKey(
    val credentials: KeyBeanCredentials,
    val expiration: String,
    val expiredTime: Int,
    val requestId: String,
    val startTime: Int
)

data class KeyBeanCredentials(
    val sessionToken: String,
    val tmpSecretId: String,
    val tmpSecretKey: String
)


