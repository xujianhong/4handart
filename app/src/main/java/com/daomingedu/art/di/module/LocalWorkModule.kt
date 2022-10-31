package com.daomingedu.art.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.LocalWorkContract
import com.daomingedu.art.mvp.model.LocalWorkModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/12/2019 15:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建LocalWorkModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class LocalWorkModule(private val view: LocalWorkContract.View) {
    @ActivityScope
    @Provides
    fun provideLocalWorkView(): LocalWorkContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideLocalWorkModel(model: LocalWorkModel): LocalWorkContract.Model {
        return model
    }
}
