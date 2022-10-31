package com.daomingedu.art.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.daomingedu.art.di.module.ModifyPasswordModule

import com.jess.arms.di.scope.ActivityScope
import com.daomingedu.art.mvp.ui.activity.ModifyPasswordActivity


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/27/2019 16:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = arrayOf(ModifyPasswordModule::class), dependencies = arrayOf(AppComponent::class))
interface ModifyPasswordComponent {
    fun inject(activity: ModifyPasswordActivity)
}
