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

import com.daomingedu.art.mvp.contract.MusicDanceTestContract
import com.daomingedu.art.mvp.model.api.Api
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.TestCityBean
import com.daomingedu.art.mvp.ui.adapter.MusicDanceTestAdapter
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/25/2019 23:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class MusicDanceTestPresenter
@Inject
constructor(model: MusicDanceTestContract.Model, rootView: MusicDanceTestContract.View) :
    BasePresenter<MusicDanceTestContract.Model, MusicDanceTestContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    @Inject
    lateinit var mAdapter: MusicDanceTestAdapter
    @Inject
    lateinit var mDatas:MutableList<TestCityBean>

    private val mSessionId by Preference(Constant.SESSIONID,"")
    override fun onDestroy() {
        super.onDestroy()
    }

    fun cityList(type:Int){
        mModel.cityList(mSessionId,type)
            .retryWhen(RetryWithDelay(2, 5))
            .compose(RxUtils.applySchedulers(mRootView))
            .subscribe(object : ErrorHandleSubscriber<BaseJson<MutableList<TestCityBean>>>(mErrorHandler){
                override fun onNext(t: BaseJson<MutableList<TestCityBean>>) {
                    if (Api.SUCCESS == t.code) {
                        mDatas.clear()
                        mDatas.addAll(t.data!!)
                        mAdapter.notifyDataSetChanged()
                    }else{
                        mRootView.showMessage(t.msg)
                    }
                }

            })
    }
}
