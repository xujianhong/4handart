package com.daomingedu.art.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.UploadVideoPreviewContract
import com.daomingedu.art.mvp.model.UploadVideoPreviewModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/12/2019 16:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建UploadVideoPreviewModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class UploadVideoPreviewModule(private val view: UploadVideoPreviewContract.View) {
    @ActivityScope
    @Provides
    fun provideUploadVideoPreviewView(): UploadVideoPreviewContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideUploadVideoPreviewModel(model: UploadVideoPreviewModel): UploadVideoPreviewContract.Model {
        return model
    }
}
