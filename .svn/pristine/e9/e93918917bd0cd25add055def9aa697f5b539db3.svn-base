package com.daomingedu.art.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.HomeContract
import com.daomingedu.art.mvp.model.HomeModel
import com.daomingedu.art.mvp.model.entity.GradeBean
import com.daomingedu.art.mvp.model.entity.NewsBean
import com.daomingedu.art.mvp.ui.adapter.HomeAdapter
import com.daomingedu.art.mvp.ui.adapter.HomeGradeAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/25/2019 21:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建HomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class HomeModule(private val view: HomeContract.View) {
    @FragmentScope
    @Provides
    fun provideHomeView(): HomeContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideHomeModel(model: HomeModel): HomeContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    fun provideDatas() = mutableListOf<NewsBean>()
    @FragmentScope
    @Provides
    fun provideAdapter(datas:MutableList<NewsBean>) = HomeAdapter(datas)

    @FragmentScope
    @Provides
    fun provideGrades() = mutableListOf<GradeBean>()
    @FragmentScope
    @Provides
    fun provideHomeGradeAdapter(grades:MutableList<GradeBean>) = HomeGradeAdapter(grades)
}
