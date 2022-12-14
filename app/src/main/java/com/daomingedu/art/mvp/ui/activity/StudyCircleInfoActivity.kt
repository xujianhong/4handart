package com.daomingedu.art.mvp.ui.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle

import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.daomingedu.art.R
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.JHCImageConfig
import com.daomingedu.art.app.Preference
import com.daomingedu.art.app.loadImage
import com.daomingedu.art.di.component.DaggerStudyCircleInfoComponent
import com.daomingedu.art.di.module.StudyCircleInfoModule
import com.daomingedu.art.mvp.contract.StudyCircleInfoContract
import com.daomingedu.art.mvp.model.api.Api
import com.daomingedu.art.mvp.model.api.service.ShareService
import com.daomingedu.art.mvp.model.entity.ShareBean
import com.daomingedu.art.mvp.model.entity.ShareDetailBean
import com.daomingedu.art.mvp.presenter.StudyCircleInfoPresenter
import com.daomingedu.art.mvp.ui.adapter.ImageItemAdapter
import com.daomingedu.art.mvp.ui.adapter.ShareInfoAdapter
import com.daomingedu.art.mvp.ui.adapter.ShareInfoMuLtipleItem
import com.daomingedu.art.mvp.ui.widget.RoundImageView
import com.daomingedu.art.mvp.ui.widget.playrecording.DailyRecordingView
import com.daomingedu.art.mvp.ui.widget.refreshview.BaseRefreshView
import com.daomingedu.art.util.Log
import com.daomingedu.ijkplayertest.widget.PlayView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.act_base_refresh.*
import kotlinx.android.synthetic.main.activity_study_circle_info.*
import kotlinx.android.synthetic.main.include_page_head.*
import kotlinx.android.synthetic.main.item_daily_recording.view.*
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.internal.CancelAdapt
import okhttp3.OkHttpClient
import org.jetbrains.anko.startActivity
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/09/2019 20:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">?????????????????????</a>
 * ================================================
 */
/**
 * ?????????presenter
 * ??????????????????
 *
 * @ActivityScope(?????????????????????) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class StudyCircleInfoActivity : BaseActivity<StudyCircleInfoPresenter>(), StudyCircleInfoContract.View,
    BaseRefreshView.BaseRefreshViewListener, CancelAdapt {
    override fun requestShareDetailFail() {
        refreshView.onRequestDataFailed()
    }

    override fun requestShareDetailSuccess(data: ShareDetailBean?, isRefresh: Boolean?) {
        data?.let {
            val list = it.commentList
            val items = ArrayList<ShareInfoMuLtipleItem>()
            /*val oldItems = refreshView.dataList
            if (oldItems != null && isRefresh!!) {
                for (oldItem in oldItems) {
                    if (oldItem.itemType == ShareInfoMuLtipleItem.FIRST_TYPE) {
                        items.add(oldItem)
                    }
                }
            }*/
                val firstItem = ShareInfoMuLtipleItem()
                firstItem.id = share.id
                firstItem.isLike = it.isLike
                firstItem.viewCount = share.viewCount
                firstItem.likeCount = share.likeCount
                firstItem.type = ShareInfoMuLtipleItem.FIRST_TYPE
                items.add(firstItem)
            for (comment in list) {
                val item = ShareInfoMuLtipleItem()
                item.type = ShareInfoMuLtipleItem.SECOND_TYPE
                item.commentTime = comment.commentTime
                item.content = comment.content
                item.userImg = comment.userImg
                item.userName = comment.userName
                items.add(item)
            }
            refreshView.onRequestDataSuccess(items)
        }
    }

    override fun requestShareCommentSuccess() {
        ArmsUtils.makeText(this, "????????????")
        et_comment.setText("")
        //refreshView.onRefresh()
        mPresenter?.shareDetail(share.id, false)
    }


    private val share by lazy { intent.getSerializableExtra("share") as ShareBean }
    private val mSessionId by Preference(Constant.SESSIONID, "")
    @Inject
    lateinit var mAdapter: ShareInfoAdapter
    @Inject
    lateinit var mData: MutableList<ShareInfoMuLtipleItem>
    lateinit var refreshView: BaseRefreshView<ShareInfoMuLtipleItem>
    private var playView: PlayView? = null//??????????????????

    private lateinit var rl_share_title: RelativeLayout

    private lateinit var drv_recording: DailyRecordingView

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerStudyCircleInfoComponent //??????????????????,?????????????????????
            .builder()
            .appComponent(appComponent)
            .studyCircleInfoModule(StudyCircleInfoModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_study_circle_info //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        refreshView = bfv_recycle as BaseRefreshView<ShareInfoMuLtipleItem>
        tv_name.text = "?????????"
        val toolbar = toolbar2
        toolbar.setNavigationIcon(R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener { refreshData() }
        if (share.isMy == Constant.ISMY_TRUE) {//????????????
            toolbar.background.alpha = 255
            toolbar.inflateMenu(R.menu.menu_share_delete)
            toolbar.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.delete) {//??????
                    AlertDialog.Builder(this).setTitle("??????????????????????")
                        .setNegativeButton("???", null)
                        .setPositiveButton("???") { _, _ -> mPresenter?.delShare(share.id) }
                        .show()
                }
                false
            }
            toolbar.setNavigationOnClickListener {
                refreshData()
            }
        }
        share.commentCount = 0//???share?????????????????????
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            run {
                mAdapter.getItem(position)?.let { item ->
                    if (view.id == R.id.rb_like) {
                        if (item.isMy == Constant.ISMY_TRUE) {//????????????
                            ArmsUtils.makeText(this@StudyCircleInfoActivity, "???????????????????????????")
                            return@let
                        }
                        val okHttpClientBuilder = OkHttpClient.Builder()
                        val mGson = GsonBuilder()
                            .setLenient()  // ??????GSON??????????????????setLenient()
                            .create()
                        val retrofit = Retrofit.Builder()
                            .baseUrl(Api.APP_DOMAIN)
                            .addConverterFactory(GsonConverterFactory.create(mGson))
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(okHttpClientBuilder.build())
                            .build()

                        val service = retrofit.create(ShareService::class.java)
                        val call = service.shareLike(mSessionId, item.id ?: "")
                        call.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                if (item.isLike == 0) {
                                    item.likeCount++
                                    item.isLike = 1
                                }else{
                                    item.likeCount--
                                    item.isLike = 0
                                }
                                share.likeCount = item.likeCount
                                shareLikeStr(true)
                            }, {
                                it.printStackTrace()
                            })
                    }
                }
            }
        }
        refreshView.setAdapter(mAdapter)
        refreshView.setBaseRefreshViewListener(this)
        refreshView.setNoDataHint("????????????")
        refreshView.setEnableLoadMore(false)
        addHeadView()
//        shareLikeStr(false)//??????????????????


        btn_share.setOnClickListener {
            var content = et_comment.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(content)) {
                ArmsUtils.makeText(this, "????????????????????????")
                return@setOnClickListener
            }
            //            if (StringUtils.containsEmoji(content)) {
            try {
                content = URLEncoder.encode(content, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            mPresenter?.shareComment(share.id, content)
        }//????????????

        mPresenter?.shareDetail(share.id, true)
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

    override fun requestDelShareSuccess() {
        ArmsUtils.makeText(application, "??????????????????")
        killMyself()
    }

    private fun refreshData() {
        refreshView.dataList.forEach {
            if (it.itemType == ShareInfoMuLtipleItem.FIRST_TYPE) {
                share.likeCount = it.likeCount
                share.viewCount = it.viewCount + 1
            }
        }
        share.commentCount = refreshView.dataList.size-1

        setResult(RESULT_OK, intent.putExtra("shareinfo", share))
        finish()
    }

    override fun onRequestData(pageSize: String?, mCurrentCounter: String?, isRefresh: Boolean?) {
        mPresenter?.shareDetail(share.id, isRefresh)
    }

    private fun addHeadView() {
        val headerView = layoutInflater.inflate(R.layout.item_share_title, refreshView, false)
        rl_share_title = headerView.findViewById(R.id.rl_share_title) as RelativeLayout
        val riv_person = headerView.findViewById(R.id.riv_person) as RoundImageView
        riv_person.loadImage(
            JHCImageConfig.Builder()
                .imageView(riv_person)
                .url(Constant.IMAGE_PREFIX + share.shareUserImg)
                .isCircle(true)
                .errorPic(R.drawable.avatar_default)
                .placeholder(R.drawable.avatar_default)
                .build()
        )
        val tv_person = headerView.findViewById(R.id.tv_person) as TextView
        tv_person.setCompoundDrawables(null, null, null, null)
        tv_person.setTextColor(resources.getColor(R.color.black_1f))
        tv_person.setText(share.shareUserName)
        //        TextView tv_share_title = (TextView) headerView.findViewById(R.id.tv_share_title);
        //        tv_share_title.setText(share.getMusicName());
        val tv_share_time = headerView.findViewById(R.id.tv_share_time) as TextView
        tv_share_time.setText(share.shareTime)
        val tv_share_introduction = headerView.findViewById(R.id.tv_share_introduction) as TextView
        tv_share_introduction.setText(share.remark)
        //        try {
        //            tv_share_introduction.setText(URLDecoder.decode(share.getRemark(), "UTF-8"));
        //        } catch (UnsupportedEncodingException e) {
        //            e.printStackTrace();
        //        }

        val rl_videoview = headerView.findViewById(R.id.rl_videoview) as RelativeLayout

        val rv_images = headerView.findViewById(R.id.rv_images) as RecyclerView

        drv_recording = headerView.findViewById(R.id.drv_recording) as DailyRecordingView
        if (share.shareType == Constant.TYPE_PHOTO) {
            drv_recording.ll_daily_recording.setVisibility(View.GONE)
            rl_videoview.visibility = View.GONE
            rv_images.visibility = View.VISIBLE

            rv_images.layoutManager = GridLayoutManager(this, 3)
            rv_images.setHasFixedSize(true)
            //            images.addItemDecoration(new DividerGridItemDecoration(context));
            val smallUrls = share.smallUrls.split(",")
            val adapter = ImageItemAdapter(smallUrls)
            rv_images.adapter = adapter
            adapter.setOnItemClickListener(BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                val urlList = share.urls.split(",")
                val urls = mutableListOf<String>()
                urlList.forEach {
                    urls.add(Constant.IMAGE_PREFIX + it)
                }
                startActivity<ImageDetailsActivity>(
                    "position" to position,
                    "images" to urls as Serializable
                )
            })

        } else if (share.shareType == Constant.TYPE_VIDEO) {
            drv_recording.ll_daily_recording.setVisibility(View.GONE)
            rl_videoview.visibility = View.VISIBLE
            rv_images.visibility = View.GONE
            playView = PlayView(this, headerView)
            playView?.setPath(Constant.IMAGE_PREFIX + share.filePath, false)
        } else if (share.shareType == Constant.TYPE_RECORD) {
            rl_videoview.visibility = View.GONE
            rv_images.visibility = View.GONE

            drv_recording.ll_daily_recording.setVisibility(View.VISIBLE)
            drv_recording.setUrl(Constant.IMAGE_PREFIX + share.filePath)
        }
        mAdapter.addHeaderView(headerView)
    }

    fun shareLikeStr(isRefersh: Boolean) {
        if (isRefersh) {
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        playView?.onConfigurationChanged(newConfig)
        val mCurrentOrientation = resources.configuration.orientation
        if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {//???????????????

            refreshView.isScroll = true//????????????
            //            adapter.setNewData(items);
            rl_share_title.visibility = View.VISIBLE
            ll_comment.visibility = View.VISIBLE
            refreshView.mSwipeRefreshLayout.isEnabled = true
            toolbar2.visibility = View.VISIBLE
        } else if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {//???????????????
            //            items = adapter.getData();
            //            adapter.getData().clear();
            refreshView.isScroll = false//??????????????????
            rl_share_title.visibility = View.GONE
            ll_comment.visibility = View.GONE
            refreshView.mSwipeRefreshLayout.isEnabled = false
            toolbar2.visibility = View.GONE
        }
        super.onConfigurationChanged(newConfig)
    }

    override fun onDestroy() {
        super.onDestroy()
        playView?.onDestroy()
        drv_recording.player.stop()
    }

    override fun onStart() {
        super.onStart()
        playView?.onStart()
    }

    override fun onPause() {
        super.onPause()
        playView?.onPause()
        drv_recording.onPause()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                refreshData()
                finish()
            } else {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            return true

        }
        return false
    }

    override fun onResume() {
        super.onResume()
        AutoSize.cancelAdapt(this)
    }
}
