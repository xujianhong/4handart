package com.daomingedu.art.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.ModifyNameContract
import com.daomingedu.art.mvp.model.ModifyNameModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/04/2019 18:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ModifyNameModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ModifyNameModule(private val view: ModifyNameContract.View) {
    @ActivityScope
    @Provides
    fun provideModifyNameView(): ModifyNameContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideModifyNameModel(model: ModifyNameModel): ModifyNameContract.Model {
        return model
    }
}
