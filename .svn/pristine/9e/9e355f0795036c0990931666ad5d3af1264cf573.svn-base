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

import com.daomingedu.art.mvp.contract.PersonInfoContract
import com.daomingedu.art.mvp.model.api.Api
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.UserInfoBean
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/04/2019 18:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class PersonInfoPresenter
@Inject
constructor(model: PersonInfoContract.Model, rootView: PersonInfoContract.View) :
    BasePresenter<PersonInfoContract.Model, PersonInfoContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    private val mSessionId by Preference(Constant.SESSIONID,"")
    override fun onDestroy() {
        super.onDestroy();
    }

    fun getCustomerInfo(){
        mModel.getCustomerInfo(mSessionId)
            .retryWhen(RetryWithDelay(2, 5))
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(object : ErrorHandleSubscriber<BaseJson<UserInfoBean>>(mErrorHandler){
                override fun onNext(t: BaseJson<UserInfoBean>) {
                    if (Api.SUCCESS == t.code) {
                        mRootView.requestGetCustomerInfoSuccess(t.data)
                    }else{
                        mRootView.showMessage(t.msg)
                    }
                }

            })
    }

    fun updateCustomer(image:String?=null,birthday:String?=null){
        mModel.updateCustomer(mSessionId,image,null,null,null,birthday,null)
            .retryWhen(RetryWithDelay(2, 5))
            .compose(RxUtils.applySchedulers(mRootView))
            .subscribe(object : ErrorHandleSubscriber<BaseJson<Any>>(mErrorHandler){
                override fun onNext(t: BaseJson<Any>) {
                    if (Api.SUCCESS == t.code) {
                        mRootView.requestUpdateCustomer()
                    }else{
                        mRootView.showMessage(t.msg)
                    }
                }

            })
    }
}
