package com.daomingedu.art.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.ModifyPasswordContract
import com.daomingedu.art.mvp.model.ModifyPasswordModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/27/2019 16:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ModifyPasswordModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ModifyPasswordModule(private val view: ModifyPasswordContract.View) {
    @ActivityScope
    @Provides
    fun provideModifyPasswordView(): ModifyPasswordContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideModifyPasswordModel(model: ModifyPasswordModel): ModifyPasswordContract.Model {
        return model
    }
}
