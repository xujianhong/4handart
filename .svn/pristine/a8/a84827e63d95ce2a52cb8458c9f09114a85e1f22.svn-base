package com.daomingedu.art.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.StudyCircleInfoContract
import com.daomingedu.art.mvp.model.StudyCircleInfoModel
import com.daomingedu.art.mvp.ui.adapter.ShareInfoAdapter
import com.daomingedu.art.mvp.ui.adapter.ShareInfoMuLtipleItem


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/09/2019 20:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建StudyCircleInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class StudyCircleInfoModule(private val view: StudyCircleInfoContract.View) {
    @ActivityScope
    @Provides
    fun provideStudyCircleInfoView(): StudyCircleInfoContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideStudyCircleInfoModel(model: StudyCircleInfoModel): StudyCircleInfoContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    fun provideDatas() = mutableListOf<ShareInfoMuLtipleItem>()

    @ActivityScope
    @Provides
    fun provideAdapter(data:MutableList<ShareInfoMuLtipleItem>) = ShareInfoAdapter(data)
}
