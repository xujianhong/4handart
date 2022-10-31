package com.daomingedu.art.mvp.contract

import com.daomingedu.art.app.UploadFileRequestBody
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.UploadInfoBean
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/07/2019 19:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface UploadShareContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
//        fun onProgress(hasWrittenLen: Long, totalLen: Long, hasFinish: Boolean)
        fun requestCreateShareSuccess(data:String?)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun uploadFile(
            options: Map<String, String>, externalFileParameters: Map<String, UploadFileRequestBody>
        ): Observable<BaseJson<UploadInfoBean>>

        fun createShare(
            sessionId: String, shareType: Int, remark: String? = null, previewImage: String? = null,
            filePath: String? = null, imageUrls: String? = null, imageSmallUrls: String? = null
        ): Observable<BaseJson<String>>
    }

}
