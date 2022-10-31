package com.daomingedu.art.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import javax.inject.Inject

import com.daomingedu.art.mvp.contract.StudyCircleContract
import com.daomingedu.art.mvp.model.api.service.CommonService
import com.daomingedu.art.mvp.model.api.service.ShareService
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.ShareBean
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/25/2019 21:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class StudyCircleModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), StudyCircleContract.Model {
    override fun reportShare(
        sessionId: String,
        shareId: String?,
        userId: String?,
        type: Int,
        remake: String?
    ): Observable<BaseJson<Any>> {
        return mRepositoryManager.obtainRetrofitService(CommonService::class.java)
            .reportShare(sessionId, shareId, userId, type, remake)
    }

    override fun delShare(sessionId: String, shareId: String): Observable<BaseJson<Any>> {
        return mRepositoryManager.obtainRetrofitService(ShareService::class.java)
            .delShare(sessionId, shareId)
    }

    override fun getShareList(
        sessionId: String,
        isMy: Int,
        start: Int,
        size: Int
    ): Observable<BaseJson<MutableList<ShareBean>>> {
        return mRepositoryManager.obtainRetrofitService(ShareService::class.java)
            .getShareList(sessionId, isMy, start, size)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
