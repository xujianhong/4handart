package com.daomingedu.art.mvp.contract

import android.app.Activity
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.ShareBean
import com.jess.arms.mvp.IView
import com.jess.arms.mvp.IModel
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/02/2019 22:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface MyStudyCircleContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView{
        fun showDialogLoading()
        fun dismissDialogLoading()
        fun getMActivity(): Activity
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel{
        fun delShare(
            sessionId: String, shareId: String
        ): Observable<BaseJson<Any>>
        fun getShareList(
            sessionId: String, isMy: Int, start: Int, size: Int
        ): Observable<BaseJson<MutableList<ShareBean>>>
    }

}
