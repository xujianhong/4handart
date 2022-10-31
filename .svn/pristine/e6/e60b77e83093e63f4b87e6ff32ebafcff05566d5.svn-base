package com.daomingedu.art.util

import android.content.Context
import android.provider.Settings
import java.util.*


object DeviceUtil {

    private fun getAndroidID(context: Context): String{
        val id = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return when(id.isNotEmpty()){
            true -> id
            false -> ""
        }
    }

    private fun getDeviceUUid(context: Context): String{
        val androidId = DeviceUtil.getAndroidID(context)
        val hashCode = androidId.hashCode().toString()
        val deviceUuid = UUID(hashCode.toLong(), hashCode.toLong() shl 32)
        return deviceUuid.toString()
    }

    private fun getAppUUid(context: Context): String{
        var uuid = SharedPreferencesUtil.getUUID(context)
        if (uuid.isEmpty()){
            uuid = UUID.randomUUID().toString()
            SharedPreferencesUtil.setUUID(context, uuid)
        }
        return uuid
    }

    fun getUUID(context: Context): String{
        var uuid = getDeviceUUid(context)
        if (uuid.isEmpty()){
            uuid = getAppUUid(context)
        }
        return uuid
    }
}