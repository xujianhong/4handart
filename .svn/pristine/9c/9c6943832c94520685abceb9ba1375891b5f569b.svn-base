package com.daomingedu.art.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.SelectImageVideoContract
import com.daomingedu.art.mvp.model.SelectImageVideoModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/07/2019 20:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建SelectImageVideoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class SelectImageVideoModule(private val view: SelectImageVideoContract.View) {
    @ActivityScope
    @Provides
    fun provideSelectImageVideoView(): SelectImageVideoContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideSelectImageVideoModel(model: SelectImageVideoModel): SelectImageVideoContract.Model {
        return model
    }
}
