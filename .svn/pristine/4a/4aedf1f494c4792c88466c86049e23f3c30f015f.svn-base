package com.daomingedu.art.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.LocalVideoListContract
import com.daomingedu.art.mvp.model.LocalVideoListModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/27/2020 23:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建LocalVideoListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class LocalVideoListModule(private val view: LocalVideoListContract.View) {
    @ActivityScope
    @Provides
    fun provideLocalVideoListView(): LocalVideoListContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideLocalVideoListModel(model: LocalVideoListModel): LocalVideoListContract.Model {
        return model
    }
}
