package com.daomingedu.art.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.daomingedu.art.mvp.contract.BlockUsersContract
import com.daomingedu.art.mvp.model.api.service.CommonService
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.BlockUserBean
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/02/2019 23:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class BlockUsersModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), BlockUsersContract.Model {
    override fun delReport(sessionId: String, reportId: String): Observable<BaseJson<Any>> {
        return mRepositoryManager.obtainRetrofitService(CommonService::class.java).delReport(sessionId, reportId)
    }

    override fun myReport(sessionId: String, start: Int, size: Int): Observable<BaseJson<MutableList<BlockUserBean>>> {
        return mRepositoryManager.obtainRetrofitService(CommonService::class.java).myReport(sessionId, start, size)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
