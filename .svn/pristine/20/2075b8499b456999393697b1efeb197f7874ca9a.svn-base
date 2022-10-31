package com.daomingedu.art.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.ModifySexContract
import com.daomingedu.art.mvp.model.ModifySexModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/04/2019 20:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ModifySexModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ModifySexModule(private val view: ModifySexContract.View) {
    @ActivityScope
    @Provides
    fun provideModifySexView(): ModifySexContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideModifySexModel(model: ModifySexModel): ModifySexContract.Model {
        return model
    }
}
