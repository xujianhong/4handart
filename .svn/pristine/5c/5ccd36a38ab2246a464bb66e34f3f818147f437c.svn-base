package com.daomingedu.art.mvp.model

import android.app.Application
import com.daomingedu.art.app.UploadFileRequestBody
import com.daomingedu.art.mvp.contract.UploadShareContract
import com.daomingedu.art.mvp.model.api.service.CommonService
import com.daomingedu.art.mvp.model.api.service.ShareService
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.UploadInfoBean
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import io.reactivex.Observable
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
class UploadShareModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), UploadShareContract.Model {
    override fun createShare(
        sessionId: String,
        shareType: Int,
        remark: String?,
        previewImage: String?,
        filePath: String?,
        imageUrls: String?,
        imageSmallUrls: String?
    ): Observable<BaseJson<String>> {
        return mRepositoryManager.obtainRetrofitService(ShareService::class.java)
            .createShare(sessionId, shareType, remark, previewImage, filePath, imageUrls, imageSmallUrls)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;
    override fun uploadFile(
        options: Map<String, String>,
        externalFileParameters: Map<String, UploadFileRequestBody>
    ): Observable<BaseJson<UploadInfoBean>> {
        return mRepositoryManager.obtainRetrofitService(CommonService::class.java).uploadFile(options, externalFileParameters)
    }
    override fun onDestroy() {
        super.onDestroy();
    }
}
