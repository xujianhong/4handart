package com.daomingedu.art.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.MyStudyCircleContract
import com.daomingedu.art.mvp.model.MyStudyCircleModel
import com.daomingedu.art.mvp.model.entity.ShareBean
import com.daomingedu.art.mvp.ui.adapter.ShareAdapter
import com.jess.arms.di.scope.FragmentScope


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/02/2019 22:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建MyStudyCircleModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class MyStudyCircleModule(private val view: MyStudyCircleContract.View) {
    @ActivityScope
    @Provides
    fun provideMyStudyCircleView(): MyStudyCircleContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideMyStudyCircleModel(model: MyStudyCircleModel): MyStudyCircleContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    fun provideDatas() = mutableListOf<ShareBean>()

    @ActivityScope
    @Provides
    fun provideAdapter(datas:MutableList<ShareBean>) = ShareAdapter(datas,view.getMActivity())
}
