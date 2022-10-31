package com.daomingedu.art.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.UploadShareContract
import com.daomingedu.art.mvp.model.UploadShareModel
import com.daomingedu.art.mvp.ui.adapter.UploadPhotosAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/07/2019 19:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建UploadShareModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class UploadShareModule(private val view: UploadShareContract.View) {
    @ActivityScope
    @Provides
    fun provideUploadShareView(): UploadShareContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideUploadShareModel(model: UploadShareModel): UploadShareContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    fun provideDatas() = mutableListOf<String>()

    @ActivityScope
    @Provides
    fun provideAdapter(datas:MutableList<String>) = UploadPhotosAdapter(datas)
}
