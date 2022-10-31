package com.daomingedu.art.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.daomingedu.art.di.component.DaggerMyStudyCircleComponent
import com.daomingedu.art.di.module.MyStudyCircleModule
import com.daomingedu.art.mvp.contract.MyStudyCircleContract
import com.daomingedu.art.mvp.presenter.MyStudyCirclePresenter

import com.daomingedu.art.R
import com.daomingedu.art.app.onClick
import com.daomingedu.art.mvp.model.entity.ShareBean
import com.daomingedu.art.mvp.ui.adapter.ShareAdapter
import com.daomingedu.art.mvp.ui.widget.WrapContentLinearLayoutManager
import com.daomingedu.art.util.startActivityForResult
import kotlinx.android.synthetic.main.activity_my_study_circle.*
import kotlinx.android.synthetic.main.activity_my_study_circle.recyclerView
import kotlinx.android.synthetic.main.activity_my_study_circle.swipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_study_circle.*
import me.jessyan.autosize.internal.CancelAdapt
import javax.inject.Inject


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
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class MyStudyCircleActivity : BaseActivity<MyStudyCirclePresenter>(), MyStudyCircleContract.View, CancelAdapt {
    override fun getMActivity(): Activity {
        return this
    }

    override fun dismissDialogLoading() {

    }

    override fun showDialogLoading() {

    }

    @Inject
    lateinit var mAdapter: ShareAdapter
    @Inject
    lateinit var mData:MutableList<ShareBean>

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMyStudyCircleComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .myStudyCircleModule(MyStudyCircleModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_my_study_circle //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        toolbar_back.onClick { finish() }
        swipeRefreshLayout.setOnRefreshListener {
            mPresenter?.getShareList(true)
        }
        recyclerView.layoutManager = WrapContentLinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = mAdapter.apply {
            bindToRecyclerView(recyclerView)
            setOnItemChildClickListener { adapter, view, position ->
                when(view.id){
                    R.id.ib_share_delete -> {
                        AlertDialog.Builder(activity).setTitle("是否删除该分享?")
                            .setNegativeButton("否",null)
                            .setPositiveButton("是") { _, _ -> mPresenter?.delShare(mData[position].id,position) }
                            .show()
                    }
                }
            }
            setOnItemClickListener{ adapter, view, position ->
                startActivityForResult<StudyCircleInfoActivity>("share" to mData[position],callback = {
                    val share = it.getSerializableExtra("shareinfo") as ShareBean
                    mData[position] = share
                    adapter.notifyItemChanged(position)
                })
            }
            setOnLoadMoreListener({
                mPresenter?.getShareList(false)
            }, recyclerView)
            setEnableLoadMore(true)
        }
        mPresenter?.getShareList(true)
    }


    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }
}
