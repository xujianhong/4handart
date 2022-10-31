package com.daomingedu.art.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import javax.inject.Inject

import com.daomingedu.art.mvp.contract.HomeContract
import com.daomingedu.art.mvp.model.api.service.CommonService
import com.daomingedu.art.mvp.model.api.service.NewsService
import com.daomingedu.art.mvp.model.entity.BannerBean
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.GradeBean
import com.daomingedu.art.mvp.model.entity.NewsBean
import io.reactivex.Observable


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
class HomeModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), HomeContract.Model {
    override fun gradedList(key: String): Observable<BaseJson<MutableList<GradeBean>>> {
        return mRepositoryManager.obtainRetrofitService(CommonService::class.java)
            .gradedList(key)
    }

    override fun newsList(
        sessionId: String,
        type: Int,
        start: Int,
        size: Int
    ): Observable<BaseJson<MutableList<NewsBean>>> {
        return mRepositoryManager.obtainRetrofitService(NewsService::class.java)
            .newsList(sessionId, type, start, size)
    }

    override fun bannerList(sessionId: String): Observable<BaseJson<MutableList<BannerBean>>> {
        return mRepositoryManager.obtainRetrofitService(NewsService::class.java)
            .bannerList(sessionId)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
