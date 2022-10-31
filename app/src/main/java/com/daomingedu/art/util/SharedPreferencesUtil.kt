package com.daomingedu.art.util

import android.content.Context

object SharedPreferencesUtil {

    private val SHAREDPREFERENCESNAME = "CONFIG"

    private val UUID = "UUID"
    private val DEVICEID = "DEVICEID"
    private val REGISTER = "REGISTER"
    private val USER = "USER"
    private val ISSHOWBUTTON = "ISSHOWBUTTON"
    private val ISSHOWFOLDER = "ISSHOWFOLDER"
    private val ISSHOWVIDEO = "ISSHOWVIDEO"
    private val ISSHOWIMPORT = "ISSHOWIMPORT"
    private val LIMIT = "LIMIT"
    private val WATER = "WATER"
    private const val VIDEONUM ="VideoNum"
    private const val FOLDER = "FOLDER"

    fun setFolder(context: Context,folder: String){
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME,Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putString(FOLDER,folder)
        edit.commit()
    }
    fun getFolder(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(FOLDER,"")!!
    }
    fun setVideoNum(context: Context, videoNum: Int){
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putInt(VIDEONUM, videoNum)
        edit.commit()
    }

    fun getVideoNum(context: Context): Int{
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(VIDEONUM, 0)
    }


    fun setWater(context: Context, Water: Int){
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putInt(WATER, Water)
        edit.commit()
    }

    fun getWater(context: Context): Int{
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(WATER, 1)
    }


    fun setLimit(context: Context, Limit: Int){
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putInt(LIMIT, Limit)
        edit.commit()
    }

    fun getLimit(context: Context): Int{
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(LIMIT, 0)
    }

    fun setIsShowImport(context: Context, Import: Int){
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putInt(ISSHOWVIDEO, Import)
        edit.commit()
    }

    fun getIsShowImport(context: Context): Int{
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(ISSHOWIMPORT, 0)
    }

    fun setIsShowVideo(context: Context, isShowVideo: Int){
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putInt(ISSHOWVIDEO, isShowVideo)
        edit.commit()
    }

    fun getIsShowVideo(context: Context): Int{
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(ISSHOWVIDEO, 0)
    }


    fun setIsShowFolder(context: Context, isShowFolder: Int){
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putInt(ISSHOWFOLDER, isShowFolder)
        edit.commit()
    }

    fun getIsShowFolder(context: Context): Int{
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(ISSHOWFOLDER, 0)
    }

    fun setIsShowButton(context: Context, isShowButton: Int){
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putInt(ISSHOWBUTTON, isShowButton)
        edit.commit()
    }

    fun getIsShowButton(context: Context): Int{
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(ISSHOWBUTTON, 0)
    }

    fun setUUID(context: Context, uuid: String){
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putString(UUID, uuid)
        edit.commit()
    }

    fun getUUID(context: Context): String{
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(UUID, "")!!
    }

    fun setDeviceId(context: Context, deviceId: String){
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putString(DEVICEID, deviceId)
        edit.commit()
    }

    fun getDeviceId(context: Context): String{
        val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(DEVICEID, "")!!
    }


}