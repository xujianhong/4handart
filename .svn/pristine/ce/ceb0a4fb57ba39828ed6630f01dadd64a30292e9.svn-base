package com.daomingedu.art.mvp.presenter

import android.app.Application
import android.media.ThumbnailUtils
import android.provider.MediaStore
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.Preference
import com.daomingedu.art.app.UploadFileRequestBody

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.daomingedu.art.mvp.contract.CertificationContract
import com.daomingedu.art.mvp.model.api.Api
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.UploadInfoBean
import com.daomingedu.art.mvp.ui.activity.UploadShareActivity
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import top.zibin.luban.Luban
import java.io.File


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/13/2019 21:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class CertificationPresenter
@Inject
constructor(model: CertificationContract.Model, rootView: CertificationContract.View) :
    BasePresenter<CertificationContract.Model, CertificationContract.View>(model, rootView) {
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

    fun  uploadFile(filePath: String, name: String, idNumber: String) {
        Observable.create<File> {
            val file = Luban.with(mApplication).get(filePath)
            it.onNext(file)
            it.onComplete()
        }.subscribeOn(Schedulers.io())
            .doOnSubscribe {
                mRootView.showLoading()//显示进度条
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .concatMap { file ->
                val optionMap = HashMap<String, String>()
                optionMap["sessionId"] = mSessionId
                optionMap["fileType"] = "3"
                optionMap["buzType"] = "1"
                val requestBodyMap = HashMap<String, UploadFileRequestBody>()
                val fileRequestBody = UploadFileRequestBody(file)
                requestBodyMap["file\"; filename=\"" + file.name] = fileRequestBody
                mModel.uploadFile(optionMap, requestBodyMap)
            }.subscribe(object : ErrorHandleSubscriber<BaseJson<UploadInfoBean>>(mErrorHandler){
                override fun onNext(t: BaseJson<UploadInfoBean>) {
                    if (t.code == Api.SUCCESS) {
                        mModel.saveCer(mSessionId,name,idNumber,t.data?.fileKey?:"")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doFinally {
                                mRootView.hideLoading()//隐藏进度条
                            }
                            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                            .subscribe(object : ErrorHandleSubscriber<BaseJson<Any>>(mErrorHandler) {
                                override fun onNext(t: BaseJson<Any>) {
                                    if (t.code == Api.SUCCESS) {
                                        mRootView.requestSaveCerSuccess()
                                    } else {
                                        mRootView.showMessage(t.msg)
                                    }
                                }
                            })
                    } else {
                        mRootView.showMessage(t.msg)
                    }
                }

                override fun onError(t: Throwable) {
                    super.onError(t)
                    mRootView.hideLoading()//隐藏进度条
                    mRootView.showMessage(t.message.toString())
                }

            })
    }
}
