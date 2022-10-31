package com.daomingedu.art.mvp.presenter

import android.app.Application
import android.util.Log
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.Preference
import com.daomingedu.art.app.utils.RxUtils

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.daomingedu.art.mvp.contract.HomeContract
import com.daomingedu.art.mvp.model.api.Api
import com.daomingedu.art.mvp.model.entity.*
import com.daomingedu.art.mvp.ui.adapter.HomeAdapter
import com.daomingedu.art.mvp.ui.adapter.HomeGradeAdapter
import com.google.gson.Gson
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/25/2019 21:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class HomePresenter
@Inject
constructor(model: HomeContract.Model, rootView: HomeContract.View) :
    BasePresenter<HomeContract.Model, HomeContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    @Inject
    lateinit var mHomeGradAdapter: HomeGradeAdapter
    @Inject
    lateinit var mGrades:MutableList<GradeBean>
    @Inject
    lateinit var mAdapter: HomeAdapter
    @Inject
    lateinit var mDatas:MutableList<NewsBean>
    private val mSessionId by Preference(Constant.SESSIONID,"")
    var pageStart = 0
    val pageSize = 20
    override fun onDestroy() {
        super.onDestroy()
    }

    fun bannerList(){
        mModel.bannerList(mSessionId)
            .retryWhen(RetryWithDelay(2, 5))
            .compose(RxUtils.applySchedulers(mRootView))
            .subscribe(object : ErrorHandleSubscriber<BaseJson<MutableList<BannerBean>>>(mErrorHandler){
                override fun onNext(t: BaseJson<MutableList<BannerBean>>) {
                    if (Api.SUCCESS ==t.code) {
                        mRootView.requestBannerListSuccess(t.data)
                    }else{
                        mRootView.showMessage(t.msg)
                    }
                }

            })
    }

    fun newsList(pullToRefresh: Boolean){
        if (pullToRefresh) {
            pageStart = 0
        }
        mModel.newsList(mSessionId,1,pageStart, pageSize)
            .subscribeOn(Schedulers.io())
            .retryWhen(RetryWithDelay(2, 5))
            .doOnSubscribe {
                if (pullToRefresh) {
                    mRootView.showLoading()
                }
            }.subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                if (pullToRefresh){
                    mRootView.hideLoading()
                }
            }
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(object : ErrorHandleSubscriber<BaseJson<MutableList<NewsBean>>>(mErrorHandler){
                override fun onNext(t: BaseJson<MutableList<NewsBean>>) {
                    if (t.code == Api.SUCCESS) {
                        if (t.data != null) {
                            if (pullToRefresh) {
                                mDatas.clear()
                                mDatas.addAll(t.data!!)
                                mAdapter.notifyDataSetChanged()
                            } else {
                                mDatas.addAll(t.data!!)
                                mAdapter.notifyItemRangeChanged(mDatas.size - t.data!!.size, mDatas.size)
                            }
                            if (t.data!!.size < pageSize) {
                                mAdapter.loadMoreEnd()
                            } else {
                                mAdapter.loadMoreComplete()
                                pageStart += pageSize
                            }
                        }
                    } else {
                        mRootView.showMessage(t.msg)
                    }
                }

            })
    }

    fun gradeList(){
        mModel.gradedList(Constant.KEY)
            .retryWhen(RetryWithDelay(2, 5))
            .compose(RxUtils.applySchedulers(mRootView))
            .subscribe(object : ErrorHandleSubscriber<BaseJson<MutableList<GradeBean>>>(mErrorHandler){
                override fun onNext(t: BaseJson<MutableList<GradeBean>>) {
                    if (Api.SUCCESS ==t.code) {
                        Log.d("test1", Gson().toJson(t.data))
                        mGrades.clear()
                        mGrades.addAll(t.data?: mutableListOf())
                        mHomeGradAdapter.notifyDataSetChanged()
                    }else{
                        mRootView.showMessage(t.msg)
                    }
                }

            })
    }


}
