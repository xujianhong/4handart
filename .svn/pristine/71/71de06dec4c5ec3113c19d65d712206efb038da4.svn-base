package com.daomingedu.art.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.StudyCircleContract
import com.daomingedu.art.mvp.model.StudyCircleModel
import com.daomingedu.art.mvp.model.entity.ShareBean
import com.daomingedu.art.mvp.ui.adapter.ShareAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/25/2019 21:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建StudyCircleModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class StudyCircleModule(private val view: StudyCircleContract.View) {
    @FragmentScope
    @Provides
    fun provideStudyCircleView(): StudyCircleContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideStudyCircleModel(model: StudyCircleModel): StudyCircleContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    fun provideDatas() = mutableListOf<ShareBean>()

    @FragmentScope
    @Provides
    fun provideAdapter(datas:MutableList<ShareBean>) = ShareAdapter(datas,view.getMActivity())
}
