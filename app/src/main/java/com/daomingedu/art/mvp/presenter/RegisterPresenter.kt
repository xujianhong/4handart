package com.daomingedu.art.mvp.presenter

import android.app.Application
import android.text.TextUtils
import com.daomingedu.art.BuildConfig
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.Preference
import com.daomingedu.art.app.utils.RxUtils

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.daomingedu.art.mvp.contract.RegisterContract
import com.daomingedu.art.mvp.model.api.Api
import com.daomingedu.art.mvp.model.entity.AboutUsBean
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.RegisterBean
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay


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
@ActivityScope
class RegisterPresenter
@Inject
constructor(model: RegisterContract.Model, rootView: RegisterContract.View) :
    BasePresenter<RegisterContract.Model, RegisterContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    private var sessionId by Preference(Constant.SESSIONID,"")
    override fun onDestroy() {
        super.onDestroy()
    }

    fun sendSMS(mobile:String){
        mModel.sendSMS(Constant.KEY,mobile,1)
            .retryWhen(RetryWithDelay(2, 5))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ErrorHandleSubscriber<BaseJson<Any>>(mErrorHandler) {
                override fun onNext(t: BaseJson<Any>) {
                    if (Api.SUCCESS == t.code) {
                        mRootView.requestSendSMSSuccess()
                    }
                    if (!TextUtils.isEmpty(t.msg)){
                        mRootView.showMessage(t.msg)
                    }
                }

                override fun onError(t: Throwable) {
                    super.onError(t)
                    mRootView.showMessage(t.toString())
                }
            })
    }

    fun reg(mobile:String,password:String,code:String){
        mModel.reg(Constant.KEY,mobile,password,1,null,null,BuildConfig.VERSION_CODE,code)
            .retryWhen(RetryWithDelay(2, 5))
            .compose(RxUtils.applySchedulers(mRootView))
            .subscribe(object : ErrorHandleSubscriber<BaseJson<RegisterBean>>(mErrorHandler) {
                override fun onNext(t: BaseJson<RegisterBean>) {
                    if (Api.SUCCESS == t.code) {
                        sessionId = t.data?.sessionId?:""
                        mRootView.requestRegSuccess()
                    }else{
                        mRootView.showMessage(t.msg)
                    }
                }

                override fun onError(t: Throwable) {
                    super.onError(t)
                    mRootView.showMessage(t.toString())
                }
            })
    }

    fun aboutUs(type:Int){
        mModel.aboutUs(sessionId)
            .retryWhen(RetryWithDelay(2, 5))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(object : ErrorHandleSubscriber<BaseJson<AboutUsBean>>(mErrorHandler){
                override fun onNext(t: BaseJson<AboutUsBean>) {
                    if (Api.SUCCESS == t.code) {
                        mRootView.requestAboutUsSuccess(t.data,type)
                    }else{
                        mRootView.showMessage(t.msg)
                    }
                }

            })
    }
}
