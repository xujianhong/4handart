package com.daomingedu.art.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.daomingedu.art.mvp.contract.RegisterContract
import com.daomingedu.art.mvp.model.api.service.CommonService
import com.daomingedu.art.mvp.model.api.service.CustomerService
import com.daomingedu.art.mvp.model.entity.AboutUsBean
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.RegisterBean
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/25/2019 20:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class RegisterModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), RegisterContract.Model {
    override fun sendSMS(key: String, mobile: String, type: Int): Observable<BaseJson<Any>> {
        return mRepositoryManager.obtainRetrofitService(CommonService::class.java).sendSMS(key, mobile, type)
    }

    override fun reg(
        key: String,
        mobile: String,
        password: String,
        channel: Int,
        pushId: String?,
        deviceNo: String?,
        versionCode: Int,
        code: String
    ): Observable<BaseJson<RegisterBean>> {
        return mRepositoryManager.obtainRetrofitService(CustomerService::class.java)
            .reg(key, mobile, password, channel, pushId, deviceNo, versionCode, code)
    }

    override fun aboutUs(sessionId: String): Observable<BaseJson<AboutUsBean>> {
        return mRepositoryManager.obtainRetrofitService(CommonService::class.java).aboutUs(sessionId)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
