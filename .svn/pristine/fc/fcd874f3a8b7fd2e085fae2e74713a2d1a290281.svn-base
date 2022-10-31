package com.daomingedu.art.mvp.model

import android.app.Application
import com.daomingedu.art.app.UploadFileRequestBody
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.daomingedu.art.mvp.contract.CertificationContract
import com.daomingedu.art.mvp.model.api.service.CerStuService
import com.daomingedu.art.mvp.model.api.service.CommonService
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.UploadInfoBean
import io.reactivex.Observable


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
class CertificationModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), CertificationContract.Model {
    override fun saveCer(
        sessionId: String,
        name: String,
        idNumber: String,
        phonePath: String
    ): Observable<BaseJson<Any>> {
        return mRepositoryManager.obtainRetrofitService(CerStuService::class.java).saveCer(sessionId, name, idNumber, phonePath)
    }

    override fun uploadFile(
        options: Map<String, String>,
        externalFileParameters: Map<String, UploadFileRequestBody>
    ): Observable<BaseJson<UploadInfoBean>> {
        return mRepositoryManager.obtainRetrofitService(CommonService::class.java).uploadFile(options, externalFileParameters)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
