package com.daomingedu.art.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.daomingedu.art.di.module.PersonInfoModule

import com.jess.arms.di.scope.ActivityScope
import com.daomingedu.art.mvp.ui.activity.PersonInfoActivity


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/04/2019 18:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = arrayOf(PersonInfoModule::class), dependencies = arrayOf(AppComponent::class))
interface PersonInfoComponent {
    fun inject(activity: PersonInfoActivity)
}
