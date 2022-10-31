package com.daomingedu.art.mvp.presenter

import android.app.Application
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.util.Log
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.Preference
import com.daomingedu.art.app.UploadFileRequestBody
import com.daomingedu.art.app.utils.RxUtils
import com.daomingedu.art.mvp.contract.UploadShareContract
import com.daomingedu.art.mvp.model.api.Api
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.ui.activity.UploadShareActivity
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import top.zibin.luban.Luban
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject


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
@ActivityScope
class UploadSharePresenter
@Inject
constructor(model: UploadShareContract.Model, rootView: UploadShareContract.View) :
    BasePresenter<UploadShareContract.Model, UploadShareContract.View>(model, rootView) {
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

    /**
     * 上传文件
     *filePathList 文件路径
     *fileType  1视频 2音频 3图片
     *buzType   业务类型，1学习圈
     */
    fun  uploadFile(filePathList: MutableList<String>, fileType: Int, content: String?) {
        Observable.create<File> {
            filePathList.forEach { path ->
                if (UploadShareActivity.ADD_PHOTO_STR != path) {
                    val file = if (fileType == UploadShareActivity.TYPE_PHOTO) {
                        Luban.with(mApplication).get(path)
                    } else {
                        File(path)
                    }
                    if (fileType == UploadShareActivity.TYPE_VIDEO) {
                        val thumbnailBitmap =
                            ThumbnailUtils.createVideoThumbnail(file?.path, MediaStore.Images.Thumbnails.MINI_KIND)
                        val file2 = saveBitmapFile(thumbnailBitmap)
                        it.onNext(file2)
                    }
                    it.onNext(file)
                }
            }
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
                optionMap["fileType"] = "$fileType"
                optionMap["buzType"] = "1"
                val requestBodyMap = HashMap<String, UploadFileRequestBody>()
                val fileRequestBody = UploadFileRequestBody(file)
                requestBodyMap["file\"; filename=\"" + file.name] = fileRequestBody
                mModel.uploadFile(optionMap, requestBodyMap)
            }
            .toList()
            .subscribe { results, error ->
                var fileUrls = ""
                results.forEach {
                    if (it.code == Api.SUCCESS) {
                        fileUrls += "${it.data?.fileKey},"
                    } else {
                        mRootView.showMessage(it.msg)
                        return@forEach
                    }
                }
                fileUrls = fileUrls.substring(0, fileUrls.length - 1)
                val observable = when (fileType) {
                    UploadShareActivity.TYPE_RECORDING -> {
                        mModel.createShare(mSessionId, fileType, content, filePath = fileUrls)
                    }
                    UploadShareActivity.TYPE_VIDEO -> {
                        val files = fileUrls.split(",")
                        val file1 = files[0]
                        val file2 = files[1]
                        mModel.createShare(mSessionId, fileType, content, previewImage = file1, filePath = file2)
                    }
                    else -> {
                        mModel.createShare(
                            mSessionId,
                            fileType,
                            content,
                            imageUrls = fileUrls,
                            imageSmallUrls = fileUrls
                        )
                    }
                }
                observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally {
                        mRootView.hideLoading()//隐藏进度条
                    }
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(object : ErrorHandleSubscriber<BaseJson<String>>(mErrorHandler) {
                        override fun onNext(t: BaseJson<String>) {
                            if (t.code == Api.SUCCESS) {
                                mRootView.requestCreateShareSuccess(t.data)
                            } else {
                                mRootView.showMessage(t.msg)
                            }
                        }

                        override fun onError(t: Throwable) {
                            super.onError(t)
                        }
                    })
            }
    }

    fun saveBitmapFile(bitmap: Bitmap): File {
        val dirFile = File(Constant.SAVE_TEMP_IMG)//将要保存图片的路径
        if (!dirFile.exists()) {
            dirFile.mkdirs()
        }
        val file = File(Constant.SAVE_TEMP_IMG, "${System.currentTimeMillis()}.jpg")//将要保存图片的路

        try {
            val bos = BufferedOutputStream(FileOutputStream(file))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos)
            bos.flush()
            bos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }
}
