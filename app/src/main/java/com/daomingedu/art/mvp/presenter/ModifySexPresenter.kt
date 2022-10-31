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

import com.daomingedu.art.mvp.contract.ModifySexContract
import com.daomingedu.art.mvp.model.api.Api
import com.daomingedu.art.mvp.model.entity.BaseJson
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/04/2019 20:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ModifySexPresenter
@Inject
constructor(model: ModifySexContract.Model, rootView: ModifySexContract.View) :
    BasePresenter<ModifySexContract.Model, ModifySexContract.View>(model, rootView) {
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

    fun updateCustomer(sex:Int){
        mModel.updateCustomer(mSessionId,null,null,null,sex,null,null)
            .retryWhen(RetryWithDelay(2, 5))
            .compose(RxUtils.applySchedulers(mRootView))
            .subscribe(object : ErrorHandleSubscriber<BaseJson<Any>>(mErrorHandler){
                override fun onNext(t: BaseJson<Any>) {
                    if (Api.SUCCESS == t.code) {
                        mRootView.requestUpdateCustomerSuccess()
                    }else{
                        mRootView.showMessage(t.msg)
                    }
                }

            })
    }
}
