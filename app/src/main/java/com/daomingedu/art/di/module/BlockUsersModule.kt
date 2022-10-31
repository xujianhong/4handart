package com.daomingedu.art.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.daomingedu.art.mvp.contract.BlockUsersContract
import com.daomingedu.art.mvp.model.BlockUsersModel
import com.daomingedu.art.mvp.model.entity.BlockUserBean
import com.daomingedu.art.mvp.ui.adapter.BlockUsersAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/02/2019 23:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建BlockUsersModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class BlockUsersModule(private val view: BlockUsersContract.View) {
    @ActivityScope
    @Provides
    fun provideBlockUsersView(): BlockUsersContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideBlockUsersModel(model: BlockUsersModel): BlockUsersContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    fun provideDatas() = mutableListOf<BlockUserBean>()

    @ActivityScope
    @Provides
    fun provideAdapter(datas:MutableList<BlockUserBean>) = BlockUsersAdapter(datas)
}
