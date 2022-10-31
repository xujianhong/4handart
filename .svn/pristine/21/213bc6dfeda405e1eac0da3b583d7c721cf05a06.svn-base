package com.daomingedu.art.mvp.presenter

import android.app.Application
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.Preference

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.daomingedu.art.mvp.contract.MyStudyCircleContract
import com.daomingedu.art.mvp.model.api.Api
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.ShareBean
import com.daomingedu.art.mvp.ui.adapter.ShareAdapter
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay


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
@ActivityScope
class MyStudyCirclePresenter
@Inject
constructor(model: MyStudyCircleContract.Model, rootView: MyStudyCircleContract.View) :
    BasePresenter<MyStudyCircleContract.Model, MyStudyCircleContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager
    @Inject
    lateinit var mAdapter: ShareAdapter
    @Inject
    lateinit var mData:MutableList<ShareBean>
    val mSessionId by Preference(Constant.SESSIONID, "")
    val pageSize = 20
    var pageStart = 0

    override fun onDestroy() {
        super.onDestroy()
    }

    fun getShareList(pullToRefresh: Boolean){
        if (pullToRefresh) {
            pageStart = 0
        }
        mModel.getShareList(mSessionId,1,pageStart,pageSize)
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
            .subscribe(object : ErrorHandleSubscriber<BaseJson<MutableList<ShareBean>>>(mErrorHandler) {
                override fun onNext(t: BaseJson<MutableList<ShareBean>>) {
                    if (t.code == Api.SUCCESS){
                        if (t.data != null) {
                            if (pullToRefresh) {
                                mData.clear()
                                mData.addAll(t.data!!)
                                mAdapter.notifyDataSetChanged()
                            } else {
                                mData.addAll(t.data!!)
                                mAdapter.notifyItemRangeChanged(mData.size - t.data!!.size, mData.size)
                            }
                            if (t.data!!.size < pageSize) {
                                mAdapter.loadMoreEnd()
                            } else {
                                mAdapter.loadMoreComplete()
                                pageStart += pageSize
                            }
                        }
                    }else{
                        mRootView.showMessage(t.msg)
                    }
                }

                override fun onError(t: Throwable) {
                    super.onError(t)
                }
            })
    }

    fun delShare(shareId:String,position:Int){
        mModel.delShare(mSessionId,shareId)
            .subscribeOn(Schedulers.io())
            .retryWhen(RetryWithDelay(2, 5))
            .doOnSubscribe {
                mRootView.showDialogLoading()
            }.subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                mRootView.dismissDialogLoading()
            }
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(object :ErrorHandleSubscriber<BaseJson<Any>>(mErrorHandler){
                override fun onNext(t: BaseJson<Any>) {
                    if (t.code == Api.SUCCESS){
                        mData.removeAt(position)
                        mAdapter.notifyItemRemoved(position)
                        mRootView.showMessage("删除成功")
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
