package com.daomingedu.art.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.ForgetPasswordContract
import com.daomingedu.art.mvp.model.ForgetPasswordModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/27/2019 16:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ForgetPasswordModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ForgetPasswordModule(private val view: ForgetPasswordContract.View) {
    @ActivityScope
    @Provides
    fun provideForgetPasswordView(): ForgetPasswordContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideForgetPasswordModel(model: ForgetPasswordModel): ForgetPasswordContract.Model {
        return model
    }
}
