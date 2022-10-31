package com.daomingedu.art.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.daomingedu.art.di.module.LocalVideoListModule

import com.jess.arms.di.scope.ActivityScope
import com.daomingedu.art.mvp.ui.activity.LocalVideoListActivity


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/27/2020 23:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(
    modules = arrayOf(LocalVideoListModule::class),
    dependencies = arrayOf(AppComponent::class)
)
interface LocalVideoListComponent {
    fun inject(activity: LocalVideoListActivity)
}
