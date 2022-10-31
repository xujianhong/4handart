package com.daomingedu.smartnote.app.interfaces

/**
 * @创建者 chendong
 * @创建时间 2019/2/23 9:37
 * @描述
 */
interface ProgressListener{
    fun onProgress(hasWrittenLen:Long,totalLen:Long,hasFinish:Boolean)
}