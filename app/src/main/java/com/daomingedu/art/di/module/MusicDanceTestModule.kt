package com.daomingedu.art.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.MusicDanceTestContract
import com.daomingedu.art.mvp.model.MusicDanceTestModel
import com.daomingedu.art.mvp.model.entity.TestCityBean
import com.daomingedu.art.mvp.ui.adapter.MusicDanceTestAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/25/2019 23:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建MusicDanceTestModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class MusicDanceTestModule(private val view: MusicDanceTestContract.View) {
    @ActivityScope
    @Provides
    fun provideMusicDanceTestView(): MusicDanceTestContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideMusicDanceTestModel(model: MusicDanceTestModel): MusicDanceTestContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    fun provideDatas() = mutableListOf<TestCityBean>()
    @ActivityScope
    @Provides
    fun provideAdapter(datas:MutableList<TestCityBean>) = MusicDanceTestAdapter(datas)
}
