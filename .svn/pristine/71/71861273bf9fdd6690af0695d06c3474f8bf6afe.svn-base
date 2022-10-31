package com.daomingedu.art.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.CertificationContract
import com.daomingedu.art.mvp.model.CertificationModel


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
@Module
//构建CertificationModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class CertificationModule(private val view: CertificationContract.View) {
    @ActivityScope
    @Provides
    fun provideCertificationView(): CertificationContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideCertificationModel(model: CertificationModel): CertificationContract.Model {
        return model
    }
}
