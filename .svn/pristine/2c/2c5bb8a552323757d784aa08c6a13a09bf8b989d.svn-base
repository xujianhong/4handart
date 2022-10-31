package com.daomingedu.art.mvp.ui.activity.presenter

import android.content.Context
import android.os.Handler
import android.os.Message
import com.blankj.utilcode.util.ApiUtils
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.Preference
import com.daomingedu.art.mvp.model.api.Api
import com.daomingedu.art.mvp.ui.activity.view.IFeedbackView
import com.daomingedu.art.util.MyOkGoUtil
import com.lzy.okgo.model.HttpParams

class FeedbackCompl(val context: Context, val iFeedbackView: IFeedbackView): IFeedbackPresenter {

    private var sessionId by Preference(Constant.SESSIONID,"")

    override fun feedback(content: String) {
        val httpParams = HttpParams()
        httpParams.put("sessionId", sessionId)
        httpParams.put("feedback", content)
        MyOkGoUtil.post(context,  Api.APP_DOMAIN + "/api/customer/feedback", httpParams, object: Handler(){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == 0){
                    iFeedbackView.feedbackResult(msg.obj as String)
                }
            }
        })
    }
}