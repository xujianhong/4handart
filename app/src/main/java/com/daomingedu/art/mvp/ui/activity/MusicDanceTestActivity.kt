package com.daomingedu.art.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.daomingedu.art.R
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.px
import com.daomingedu.art.di.component.DaggerMusicDanceTestComponent
import com.daomingedu.art.di.module.MusicDanceTestModule
import com.daomingedu.art.mvp.contract.MusicDanceTestContract
import com.daomingedu.art.mvp.model.entity.TestCityBean
import com.daomingedu.art.mvp.presenter.MusicDanceTestPresenter
import com.daomingedu.art.mvp.ui.adapter.MusicDanceTestAdapter
import com.daomingedu.art.mvp.ui.decoration.DividerDecoration
import com.daomingedu.art.util.Log
import com.daomingedu.art.util.PhoneUtil
import com.daomingedu.art.util.SharedPreferencesUtil
import com.daomingedu.art.util.SharedPreferencesUtil.setLimit
import com.daomingedu.art.util.SharedPreferencesUtil.setWater
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import kotlinx.android.synthetic.main.activity_music_dance_test.*
import kotlinx.android.synthetic.main.fragment_home.recyclerView
import org.jetbrains.anko.startActivity
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/25/2019 23:30
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
class MusicDanceTestActivity : BaseActivity<MusicDanceTestPresenter>(), MusicDanceTestContract.View {
    @Inject
    lateinit var mAdapter: MusicDanceTestAdapter
    @Inject
    lateinit var mDatas:MutableList<TestCityBean>
    private val position by lazy { intent.getIntExtra(Constant.TEST_POSITION_EXTRA,0) }
    private val title by lazy { intent.getStringExtra(Constant.TEST_TITLE_EXTRA) }
    private val cities by lazy { intent.getSerializableExtra(Constant.TEST_CITIES_EXTRA) as MutableList<TestCityBean> }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMusicDanceTestComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .musicDanceTestModule(MusicDanceTestModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_music_dance_test //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        setTitle(title)
        /*if (position == 0) {
            ivBanner.setImageResource(R.drawable.music_banner)
        }else if (position == 1) {
            ivBanner.setImageResource(R.drawable.dance_banner)
        }*/
        val paddingSize = 20.px
        val dividerDecoration = DividerDecoration(resources.getColor(R.color.color_d6d6d6), 1, paddingSize, paddingSize)
        dividerDecoration.setDrawLastItem(false)
        recyclerView.addItemDecoration(dividerDecoration)
        recyclerView.adapter = mAdapter.apply {
            setOnItemClickListener { _, _, position ->
                mAdapter.getItem(position)?.let {
                    setWater(this@MusicDanceTestActivity, it.isWaterMark)
                    setLimit(this@MusicDanceTestActivity, it.videoLimit)
//                    Log.e(it.url+"?deviceNo="+PhoneUtil.getDeviceId(this@MusicDanceTestActivity))
                    startActivity<CommonWebActivity>(Constant.URL_EXTRA to it.url+"?deviceNo="+PhoneUtil.getUniqueID(this@MusicDanceTestActivity),
                        Constant.TITLE_EXTRA to it.cityName)
                }

            }
        }
        if (cities.isNotEmpty()){
            mDatas.addAll(cities)
            mAdapter.notifyDataSetChanged()
        }
//        mPresenter?.cityList(type)
        if (SharedPreferencesUtil.getIsShowFolder(this) == 1){
            iv_record.visibility = View.VISIBLE
        }else {
            iv_record.visibility = View.GONE
        }
        iv_record.setOnClickListener {
            UploadVideoListActivity.startUploadVideoListActivity(this, 0, 0,"",0)
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
}
