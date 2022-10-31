package com.daomingedu.art.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.daomingedu.art.mvp.contract.PersonInfoContract
import com.daomingedu.art.mvp.model.api.service.CustomerService
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.UserInfoBean
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/04/2019 18:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class PersonInfoModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), PersonInfoContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;
    override fun getCustomerInfo(sessionId: String): Observable<BaseJson<UserInfoBean>> {
        return mRepositoryManager.obtainRetrofitService(CustomerService::class.java).getCustomerInfo(sessionId)
    }

    override fun updateCustomer(
        sessionId: String,
        image: String?,
        realName: String?,
        nickName: String?,
        sex: Int?,
        birthday: String?,
        motto: String?
    ): Observable<BaseJson<Any>> {
        return mRepositoryManager.obtainRetrofitService(CustomerService::class.java)
            .updateCustomer(sessionId, image, realName, nickName, sex, birthday, motto)
    }
    override fun onDestroy() {
        super.onDestroy();
    }
}
