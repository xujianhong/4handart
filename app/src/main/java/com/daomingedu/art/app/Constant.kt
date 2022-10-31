package com.daomingedu.art.app

import android.os.Environment
import java.io.File

/**
 * @创建者 chendong
 * @创建时间 2019/2/22 9:58
 * @描述
 */
interface Constant {
    companion object {
        const val KEY = "00e11bdc34b64d12a294efd02415b788"
        const val SESSIONID = "sessionId"
        const val VIDEO_TIME = "video_time" //录制时长限制(单位分钟)
        const val VIDEO_PIXEL = "video_pixel" //录制视频的像素(1:480p,2:720p,3:1080p)
        /**
         * 文件存储对象前缀
         */
        const val COS_PATH = "cosPath"
        const val URL_EXTRA = "url_extra"
        const val TITLE_EXTRA = "title_extra"
        const val TEST_TYPE_EXTRA = "test_type_extra"
        const val TEST_POSITION_EXTRA = "test_position_extra"
        const val TEST_CITIES_EXTRA = "test_cities_extra"
        const val TEST_TITLE_EXTRA = "test_title_extra"

        const val TYPE_PHOTO = 1//图片分享
        const val TYPE_RECORD = 2//录音分享
        const val TYPE_VIDEO = 3//视频分享
        const val ISMY_TRUE = 1//我的分享
        const val ISMY_FALSE = 0//全部分享
        const val IMAGE_PREFIX = "https://4handart-1255518711.cos.ap-chengdu.myqcloud.com"//图片前缀
        val SAVE_TEMP_IMG =
            Environment.getExternalStorageDirectory().toString() + File.separator + "temp/"//临时文件
    }
}