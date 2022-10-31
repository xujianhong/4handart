package com.daomingedu.art.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.PianoContract
import com.daomingedu.art.mvp.model.PianoModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/12/2019 15:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建PianoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class PianoModule(private val view: PianoContract.View) {
    @ActivityScope
    @Provides
    fun providePianoView(): PianoContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun providePianoModel(model: PianoModel): PianoContract.Model {
        return model
    }
}
