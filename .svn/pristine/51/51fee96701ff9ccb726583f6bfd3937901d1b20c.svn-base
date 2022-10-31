package com.daomingedu.art.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.LocalVideoDetailContract
import com.daomingedu.art.mvp.model.LocalVideoDetailModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/28/2020 19:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建LocalVideoDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class LocalVideoDetailModule(private val view: LocalVideoDetailContract.View) {
    @ActivityScope
    @Provides
    fun provideLocalVideoDetailView(): LocalVideoDetailContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideLocalVideoDetailModel(model: LocalVideoDetailModel): LocalVideoDetailContract.Model {
        return model
    }
}
