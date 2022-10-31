package com.daomingedu.art.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.daomingedu.art.di.component.DaggerUploadVideoPreviewComponent
import com.daomingedu.art.di.module.UploadVideoPreviewModule
import com.daomingedu.art.mvp.contract.UploadVideoPreviewContract
import com.daomingedu.art.mvp.presenter.UploadVideoPreviewPresenter

import com.daomingedu.art.R
import com.daomingedu.ijkplayertest.widget.PlayView
import kotlinx.android.synthetic.main.activity_upload_video_preview.*
import kotlinx.android.synthetic.main.item_daily_recording.view.*


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/12/2019 16:25
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
class UploadVideoPreviewActivity : BaseActivity<UploadVideoPreviewPresenter>(), UploadVideoPreviewContract.View {
    var playView: PlayView? = null
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerUploadVideoPreviewComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .uploadVideoPreviewModule(UploadVideoPreviewModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_upload_video_preview //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        playView = PlayView(this)
        val bundle = intent.extras
        if (bundle == null) {
            killMyself()
            return
        }
        val filePath = bundle.getString("filePath")
        val fileType = bundle.getInt("audiofile", 0)
        if (TextUtils.isEmpty(filePath)) {
            ArmsUtils.makeText(application,"找不到该文件，请重新选择")
            finish()
        }
        if (fileType == 0) {
            playView?.rl_videoview?.setVisibility(View.VISIBLE)//视频View
            prv_recording.ll_daily_recording.setVisibility(View.GONE)//音频View
            playView?.setPath(filePath, true)
        } else {
            playView?.rl_videoview?.setVisibility(View.GONE)//视频View
            prv_recording.ll_daily_recording.setVisibility(View.VISIBLE)//音频View
            prv_recording.setUrl(filePath)//播放音频
        }
        ll_back.setOnClickListener {
            killMyself()
        }
    }


    override fun showLoading() {

    }

    override fun hideLoading() {

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

    override fun onPause() {
        super.onPause()
        prv_recording.onPause()
    }
}
