package com.daomingedu.art.mvp.ui.activity

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle

import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.daomingedu.art.BuildConfig

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.daomingedu.art.di.component.DaggerMainComponent
import com.daomingedu.art.di.module.MainModule
import com.daomingedu.art.mvp.contract.MainContract
import com.daomingedu.art.mvp.presenter.MainPresenter

import com.daomingedu.art.R
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.Preference
import com.daomingedu.art.app.visible
import com.daomingedu.art.mvp.model.entity.VersionBean
import com.daomingedu.art.mvp.ui.fragment.HomeFragment
import com.daomingedu.art.mvp.ui.fragment.MineFragment
import com.daomingedu.art.mvp.ui.fragment.StudyCircleFragment
import com.daomingedu.art.util.Log
import com.daomingedu.art.util.SharedPreferencesUtil
import com.jess.arms.integration.AppManager
import com.jess.arms.utils.Preconditions
import constant.UiType
import kotlinx.android.synthetic.main.activity_main.*
import me.jessyan.autosize.AutoSizeCompat
import model.UiConfig
import model.UpdateConfig
import org.jetbrains.anko.startActivity
import update.UpdateAppUtils
import java.io.File
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/25/2019 20:27
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
class MainActivity : BaseActivity<MainPresenter>(), MainContract.View, View.OnClickListener {
    var isFirst by Preference("isFirst", true)
    override fun requestVersionInfoSuccess(data: VersionBean?) {
        data?.let {
            llTabStudyCircle.visible(it.isOpenShare==1)
            SharedPreferencesUtil.setIsShowButton(this, it.isShowButton)
            SharedPreferencesUtil.setIsShowFolder(this, it.isShowFolder)
            SharedPreferencesUtil.setIsShowVideo(this, it.isShowVideo)
            if (BuildConfig.VERSION_CODE < it.versionCode){
                // ui配置
                val uiConfig = UiConfig().apply {
                    uiType = UiType.PLENTIFUL
                    cancelBtnText = "下次再说"
//                    updateLogoImgRes = R.mipmap.ic_launcher
//                    updateBtnBgRes = R.mipmap.ic_launcher
//                    titleTextColor = Color.BLACK
//                    titleTextSize = 18f
//                    contentTextColor = Color.parseColor("#88e16531")
                }

                // 更新配置
                val updateConfig = UpdateConfig().apply {
                    force = it.isMust == 1
                    checkWifi = true
//                    needCheckMd5 = true
                    isShowNotification = true
                    alwaysShowDownLoadDialog = true
                    notifyImgRes = R.mipmap.ic_launcher
//                    apkSavePath = Environment.getExternalStorageDirectory().absolutePath +"/teprinciple"
//                    apkSaveName = "teprinciple"
                }
                UpdateAppUtils
                    .getInstance()
                    .apkUrl(it.path)
                    .updateTitle("发现新版本${it.versionName}")
                    .updateContent(it.remark)
                    .updateConfig(updateConfig)
//                    .uiConfig(uiConfig)
                    .update()
            }
        }
    }

    @Inject
    lateinit var mHomeFragment:HomeFragment
    @Inject
    lateinit var mStudyCircleFragment:StudyCircleFragment
    @Inject
    lateinit var mMineFragment:MineFragment

    private var mCurrentFragment: Fragment? = null

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.llTabHome -> {
                ivTabHome.setImageResource(R.drawable.tab_home_selected)
                ivTabStudyCircle.setImageResource(R.drawable.tab_study_circle_defalut)
                ivTabMine.setImageResource(R.drawable.tab_mine_default)
                tvTabHome.setTextColor(resources.getColor(R.color.color_e4854d))
                tvTabStudyCircle.setTextColor(resources.getColor(R.color.color_686868))
                tvTabMine.setTextColor(resources.getColor(R.color.color_686868))
                onTabSelected(0)
            }
            R.id.llTabStudyCircle -> {
                ivTabHome.setImageResource(R.drawable.tab_home_default)
                ivTabStudyCircle.setImageResource(R.drawable.tab_study_circle_selectd)
                ivTabMine.setImageResource(R.drawable.tab_mine_default)
                tvTabHome.setTextColor(resources.getColor(R.color.color_686868))
                tvTabStudyCircle.setTextColor(resources.getColor(R.color.color_e4854d))
                tvTabMine.setTextColor(resources.getColor(R.color.color_686868))
                onTabSelected(1)
            }
            R.id.llTabMine -> {
                ivTabHome.setImageResource(R.drawable.tab_home_default)
                ivTabStudyCircle.setImageResource(R.drawable.tab_study_circle_defalut)
                ivTabMine.setImageResource(R.drawable.tab_mine_selected)
                tvTabHome.setTextColor(resources.getColor(R.color.color_686868))
                tvTabStudyCircle.setTextColor(resources.getColor(R.color.color_686868))
                tvTabMine.setTextColor(resources.getColor(R.color.color_e4854d))
                onTabSelected(2)
            }
        }
    }
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .mainModule(MainModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        AppManager.getAppManager().killAll(MainActivity::class.java)
        setupPages()
        mPresenter?.getVersionInfo()

        if(isFirst){
            showDialog()
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
    private fun setupPages() {
        llTabHome.setOnClickListener(this)
        llTabStudyCircle.setOnClickListener(this)
        llTabMine.setOnClickListener(this)
        onTabSelected(0)
    }
    private fun onTabSelected(position: Int) {
        when (position) {
            0 -> {
                switchFragment(mHomeFragment)
            }
            1 -> {
                switchFragment(mStudyCircleFragment)
            }
            2 -> {
                switchFragment(mMineFragment)
            }
            else -> {
            }
        }
    }

    /**
     * fragment的切换
     *
     * @param to
     */
    private fun switchFragment(to: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        Preconditions.checkNotNull(to)
        if (to !== mCurrentFragment) {
            if (mCurrentFragment == null) {
                transaction.add(R.id.flContainer, to).commit()
            } else if (!to.isAdded) {
                transaction.hide(mCurrentFragment!!).add(R.id.flContainer, to).commit()
            } else {
                transaction.hide(mCurrentFragment!!).show(to).commit()
            }
            mCurrentFragment = to
        }
    }

    var lastEndTime = 0L
    override fun onBackPressed() {
        val curTime = System.currentTimeMillis()
        val intervalTime = curTime - lastEndTime
        lastEndTime = curTime
        if (intervalTime < 2000) {
            super.onBackPressed()
        }else{
            ArmsUtils.makeText(applicationContext,"再按一次退出")
        }
    }

    /*override fun getResources(): Resources {
        AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()))
        return super.getResources()
    }*/

    private fun showDialog() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.show()
        alertDialog.setCancelable(false)
        val window = alertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.diag_privacy);
            window.setGravity(Gravity.CENTER);

            val tvContent: TextView = window.findViewById(R.id.tv_content);
            val tvCancel: TextView = window.findViewById(R.id.tv_cancel);
            val tvAgree: TextView = window.findViewById(R.id.tv_agree)


            val str = "    感谢您对本公司的支持!本公司非常重视您的个人信息和隐私保护。" +
                    "为了更好地保障您的个人权益，在您使用我们的产品前，" +
                    "请务必审慎阅读《隐私政策》内的所有条款，" +
                    "尤其是:\n" +
                    " 1.我们对您的个人信息的收集/保存/使用/对外提供/保护等规则条款，以及您的用户权利等条款;\n" +
                    " 2. 约定我们的限制责任、免责条款;\n" +
                    " 3.其他以颜色或加粗进行标识的重要条款。\n" +
                    "您点击“同意并继续”的行为即表示您已阅读完毕并同意以上协议的全部内容。" +
                    "如您同意以上协议内容，请点击“同意”，开始使用我们的产品和服务!";

            val ssb = SpannableStringBuilder();
            ssb.append(str);
            val start = str.indexOf("《");//第一个出现的位置
            ssb.setSpan(object : ClickableSpan() {

                override fun onClick(widget: View) {
                    startActivity<CommonWebActivity>(Constant.URL_EXTRA to "http://4hand.com.cn/art2.html",  Constant.TITLE_EXTRA to "隐私政策")
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = getResources().getColor(R.color.colorPrimary);
                    ds.isUnderlineText = false;
                }
            }, start, start + 6, 0);

//            val end = str.lastIndexOf("《");
//            ssb.setSpan(object : ClickableSpan() {
//
//                override fun onClick(widget: View) {
//                    mPresenter?.getProtocol(1)
//                }
//
//                override fun updateDrawState(ds: TextPaint) {
//                    super.updateDrawState(ds)
//                    ds.color = getResources().getColor(R.color.green_700);
//                    ds.isUnderlineText = false;
//                }
//            }, end, end + 6, 0);

            tvContent.movementMethod = LinkMovementMethod.getInstance();
            tvContent.setText(ssb, TextView.BufferType.SPANNABLE);


            tvCancel.setOnClickListener {
                alertDialog.cancel();
                finish();
            };

            tvAgree.setOnClickListener {
                isFirst = false
                alertDialog.cancel();
            }

        }

    }
}
