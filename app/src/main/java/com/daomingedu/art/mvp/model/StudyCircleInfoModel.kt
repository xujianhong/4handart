package com.daomingedu.art.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.daomingedu.art.mvp.contract.StudyCircleInfoContract
import com.daomingedu.art.mvp.model.api.service.ShareService
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.ShareDetailBean
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/09/2019 20:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class StudyCircleInfoModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), StudyCircleInfoContract.Model {
    override fun shareComment(sessionId: String, shareId: String, comment: String): Observable<BaseJson<Any>> {
        return mRepositoryManager.obtainRetrofitService(ShareService::class.java)
            .shareComment(sessionId, shareId, comment)
    }

    override fun shareDetail(sessionId: String, shareId: String): Observable<BaseJson<ShareDetailBean>> {
        return mRepositoryManager.obtainRetrofitService(ShareService::class.java)
            .shareDetail(sessionId, shareId)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;
    override fun delShare(sessionId: String, shareId: String): Observable<BaseJson<Any>> {
        return mRepositoryManager.obtainRetrofitService(ShareService::class.java)
            .delShare(sessionId, shareId)
    }
    override fun onDestroy() {
        super.onDestroy();
    }
}
