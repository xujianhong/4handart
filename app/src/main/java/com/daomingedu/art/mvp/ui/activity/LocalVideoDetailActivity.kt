package com.daomingedu.art.mvp.ui.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle

import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import com.daomingedu.art.R
import com.daomingedu.art.data.BaseDataHelper
import com.daomingedu.art.di.component.DaggerLocalVideoDetailComponent
import com.daomingedu.art.di.module.LocalVideoDetailModule
import com.daomingedu.art.mvp.contract.LocalVideoDetailContract
import com.daomingedu.art.mvp.model.LocalWork
import com.daomingedu.art.mvp.presenter.LocalVideoDetailPresenter
import com.daomingedu.ijkplayertest.widget.PlayView
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import kotlinx.android.synthetic.main.activity_local_video_detail.*
import kotlinx.android.synthetic.main.include_page_head.*
import me.jessyan.autosize.internal.CancelAdapt
import java.io.File
import java.sql.Date
import java.text.SimpleDateFormat


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
class LocalVideoDetailActivity : BaseActivity<LocalVideoDetailPresenter>(),
    LocalVideoDetailContract.View,CancelAdapt {
    internal var helper: BaseDataHelper? = null//数据库
    internal var db: SQLiteDatabase? = null
    internal var work: LocalWork? =null
    var playView: PlayView? = null

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerLocalVideoDetailComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .localVideoDetailModule(LocalVideoDetailModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_local_video_detail //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        tv_name.text = "视频播放"
        val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        toolbar?.background?.alpha = 255
        toolbar?.setNavigationIcon(R.drawable.ic_back_white)
        toolbar?.setNavigationOnClickListener { killMyself() }
        /*toolbar?.inflateMenu(R.menu.menu_class_add)
        toolbar?.setOnMenuItemClickListener {
            if (it.itemId == R.id.add){//添加
                popLocalMenu = PopLocalMenu2(this, this, isShare)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    popLocalMenu?.setElevation(10f)
                }
                popLocalMenu?.showAsDropDown(toolbar2, 0,0, Gravity.END)
            }
            return@setOnMenuItemClickListener false
        }*/
        helper = BaseDataHelper(this)
        db = helper?.getWritableDatabase()
        work = intent.getSerializableExtra("work") as LocalWork

        playView = PlayView(this)
        if (work?.getType() === LocalWork.VIDEO) {
            title = "视频播放"
            playView?.rl_videoview?.setVisibility(View.VISIBLE)//视频View
            prv_recording.rl_recordingView.setVisibility(View.GONE)//音频View
            val file = File(work?.getPath())
//            LogUtils.e(file.length() / 1024 / 1024 + " " + (application as MyApp).getVideo() / 1024 / 1024)
            initPlay(work?.getPath()?:"")//播放视频
        } else if (work?.getType() === LocalWork.PINAO || work?.getType() === LocalWork.KSONG) {
            title = "录音播放"
            playView?.rl_videoview?.setVisibility(View.GONE)//视频View
            prv_recording.rl_recordingView.setVisibility(View.VISIBLE)//音频View
            prv_recording.setUrl(work?.getPath())//播放音频
            if (work?.getType() === LocalWork.KSONG) {
                ll_ksong_desc.setVisibility(View.VISIBLE)
                tv_score_name.setText(work?.getScoreName())
                tv_score.setText(work?.getTotalScore().toString() + "分")
            }
        }

        tv_local_name.setText(work?.getName())

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val resultdate = Date(work?.getCreatetime()?:0)
        tv_local_time.setText(sdf.format(resultdate))
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

    fun initPlay(path: String) {
        playView?.setPath(path, false)
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        val mCurrentOrientation = resources.configuration.orientation
        if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) { //竖屏的时候
            toolbar2?.visibility = View.VISIBLE
        } else if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) { //横屏的时候
            toolbar2?.visibility = View.GONE
        }
        playView?.onConfigurationChanged(newConfig)
        super.onConfigurationChanged(newConfig)
    }


    override fun onDestroy() {
        super.onDestroy()
        playView?.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        playView?.onStart()
    }

    override fun onPause() {
        super.onPause()
        playView?.onPause()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                finish()
            } else {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            return true
        }
        return false
    }
}
