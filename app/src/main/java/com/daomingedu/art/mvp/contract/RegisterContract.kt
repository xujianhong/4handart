package com.daomingedu.art.mvp.contract

import com.daomingedu.art.mvp.model.entity.AboutUsBean
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.RegisterBean
import com.jess.arms.mvp.IView
import com.jess.arms.mvp.IModel
import io.reactivex.Observable
import retrofit2.http.Field


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/25/2019 20:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface RegisterContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun requestSendSMSSuccess()
        fun requestRegSuccess()
        fun requestAboutUsSuccess(data: AboutUsBean?, type: Int)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun sendSMS(
            key: String, mobile: String, type: Int
        ): Observable<BaseJson<Any>>

        fun reg(
            key: String,
            mobile: String,
            password: String,
            channel: Int,
            pushId: String?,
            deviceNo: String?,
            versionCode: Int,
            code: String
        ): Observable<BaseJson<RegisterBean>>

        fun aboutUs(
            sessionId: String
        ): Observable<BaseJson<AboutUsBean>>

    }

}