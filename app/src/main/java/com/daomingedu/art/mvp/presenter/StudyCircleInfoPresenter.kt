package com.daomingedu.art.mvp.presenter

import android.app.Application
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.Preference
import com.daomingedu.art.app.utils.RxUtils

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.daomingedu.art.mvp.contract.StudyCircleInfoContract
import com.daomingedu.art.mvp.model.api.Api
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.ShareDetailBean
import com.daomingedu.art.mvp.ui.adapter.ShareInfoAdapter
import com.daomingedu.art.mvp.ui.adapter.ShareInfoMuLtipleItem
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/09/2019 20:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class StudyCircleInfoPresenter
@Inject
constructor(model: StudyCircleInfoContract.Model, rootView: StudyCircleInfoContract.View) :
    BasePresenter<StudyCircleInfoContract.Model, StudyCircleInfoContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    val mSessionId by Preference(Constant.SESSIONID, "")
    override fun onDestroy() {
        super.onDestroy();
    }

    fun delShare(shareId:String){
        mModel.delShare(mSessionId,shareId)
            .retryWhen(RetryWithDelay(2, 5))
            .compose(RxUtils.applySchedulers(mRootView))
            .subscribe(object : ErrorHandleSubscriber<BaseJson<Any>>(mErrorHandler){
                override fun onNext(t: BaseJson<Any>) {
                    if (t.code == Api.SUCCESS){
                        mRootView.requestDelShareSuccess()
                    }else{
                        mRootView.showMessage(t.msg)
                    }
                }

                override fun onError(t: Throwable) {
                    super.onError(t)
                    mRootView.showMessage(t.message.toString())
                }
            })
    }

    fun shareDetail(shareId:String,isRefresh:Boolean?){
        mModel.shareDetail(mSessionId,shareId)
            .retryWhen(RetryWithDelay(2, 5))
            .compose(RxUtils.applySchedulers(mRootView))
            .subscribe(object : ErrorHandleSubscriber<BaseJson<ShareDetailBean>>(mErrorHandler){
                override fun onNext(t: BaseJson<ShareDetailBean>) {
                    if (t.code == Api.SUCCESS){
                        mRootView.requestShareDetailSuccess(t.data,isRefresh)
                    }else{
                        mRootView.showMessage(t.msg)
                        mRootView.requestShareDetailFail()
                    }
                }

                override fun onError(t: Throwable) {
                    super.onError(t)
                    mRootView.requestShareDetailFail()
                }
            })
    }

    fun shareComment(shareId:String,content:String){
        mModel.shareComment(mSessionId,shareId,content)
            .retryWhen(RetryWithDelay(2, 5))
            .compose(RxUtils.applySchedulers(mRootView))
            .subscribe(object : ErrorHandleSubscriber<BaseJson<Any>>(mErrorHandler){
                override fun onNext(t: BaseJson<Any>) {
                    if (t.code == Api.SUCCESS){
                        mRootView.requestShareCommentSuccess()
                    }else{
                        mRootView.showMessage(t.msg)
                    }
                }

                override fun onError(t: Throwable) {
                    super.onError(t)
                    mRootView.showMessage(t.message.toString())
                }
            })
    }
}
