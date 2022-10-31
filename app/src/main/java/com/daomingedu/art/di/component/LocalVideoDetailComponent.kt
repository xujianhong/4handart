package com.daomingedu.art.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.daomingedu.art.di.module.LocalVideoDetailModule

import com.jess.arms.di.scope.ActivityScope
import com.daomingedu.art.mvp.ui.activity.LocalVideoDetailActivity


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/28/2020 19:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(
    modules = arrayOf(LocalVideoDetailModule::class),
    dependencies = arrayOf(AppComponent::class)
)
interface LocalVideoDetailComponent {
    fun inject(activity: LocalVideoDetailActivity)
}
