package com.daomingedu.art.mvp.ui.activity.presenter

import android.content.Context
import android.os.Handler
import android.os.Message
import com.daomingedu.art.app.Constant
import com.daomingedu.art.mvp.ui.activity.presenter.IUploadPresenter
import com.daomingedu.art.mvp.ui.activity.view.IUploadView
import com.daomingedu.art.util.MyOkGoUtil
import com.daomingedu.art.util.Utils
import com.lzy.okgo.model.HttpParams

class UploadCompl(val context: Context, val iUploadView: IUploadView, val url: String) :
    IUploadPresenter {

    var sessionId: String? = ""
    val KEY = "715c714249094c5fb8c90a50f92c8bcd"

    init {
        val sp = Utils.app.getSharedPreferences(
            "config",
            Context.MODE_PRIVATE
        )
        sessionId = sp.getString(Constant.SESSIONID, "")
    }


    override fun getSongList(id: String) {
        val httpParams = HttpParams()
        httpParams.put("key", KEY)
        httpParams.put("videoId", id)
        MyOkGoUtil.getJsonObjectData(
            context,
            "$url/getMajorLevelSongList.do",
            httpParams,
            object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    if (msg.what == 0) {
                        iUploadView.getSongListResult(msg.obj as String)
                    }
                }
            })
    }

    override fun uploadPath(id: String, fileName: String) {
        val httpParams = HttpParams()
        httpParams.put("key", KEY)
        httpParams.put("videoId", id)
        httpParams.put("fileName", fileName)
        MyOkGoUtil.postString(context, "$url/getUploadPath.do", httpParams, object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == 0) {
                    iUploadView.uploadPathResult(msg.obj as String)
                }
            }
        })
    }

    override fun getTencentKey() {
        val httpParams = HttpParams()
        httpParams.put("key", KEY)
        MyOkGoUtil.getJsonObjectData(
            context,
            "$url/getCosCredential.do",
            httpParams,
            object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    if (msg.what == 0) {
                        iUploadView.getTencentKeyResult(msg.obj as String)
                    }
                }
            })
    }

    override fun getFaceScore(id: String, image: String) {
        val httpParams = HttpParams()
        httpParams.put("key", KEY)
        httpParams.put("videoId", id)
        httpParams.put("image", image)
        MyOkGoUtil.postnew(context, "$url/getCompareFaceScore.do", httpParams, object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == 0) {
                    iUploadView.getFaceScoreResult(msg.obj as String)
                } else if (msg.what == 201) {
                    iUploadView.getFaceScoreResultFailed(msg.obj as String)
                }
            }
        })
    }

    override fun saveUpload(
        id: String,
        videoPath: String,
        score: Double,
        majorLevelSongId: String,
        videoSize: String,
        videoCreateTime: String,
        isAudit: String,
        videoTime: String
    ) {
        val httpParams = HttpParams()
        httpParams.put("key", KEY)
        httpParams.put("videoId", id)
        httpParams.put("videoPath", videoPath)
        httpParams.put("score", score)
        httpParams.put("majorLevelSongId", majorLevelSongId)
        httpParams.put("videoSize", videoSize)
        httpParams.put("videoCreateTime", videoCreateTime)
        httpParams.put("isAudit", isAudit)
        httpParams.put("videoTime", videoTime)
        MyOkGoUtil.postnew(context, "$url/saveUploadVideo.do", httpParams, object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == 0) {
                    iUploadView.saveUploadResult(msg.obj as String)
                } else if (msg.what == 203) {
                    iUploadView.saveUploadFailedResult("")
                }
            }
        })
    }
}