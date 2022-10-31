package com.daomingedu.art.mvp.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daomingedu.art.R
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.JHCImageConfig
import com.daomingedu.art.app.loadImage
import com.daomingedu.art.mvp.ui.widget.RoundImageView
import com.jess.arms.integration.AppManager
import com.jess.arms.integration.RepositoryManager
import com.jess.arms.utils.ArmsUtils
import io.reactivex.Observable
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import retrofit2.Retrofit
import com.daomingedu.art.app.Preference
import com.daomingedu.art.mvp.model.api.Api
import com.daomingedu.art.mvp.model.api.service.ShareService
import com.daomingedu.art.mvp.ui.activity.StudyCircleInfoActivity
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.autosize.internal.CancelAdapt
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ShareInfoAdapter(data: MutableList<ShareInfoMuLtipleItem>) :
    BaseMultiItemQuickAdapter<ShareInfoMuLtipleItem, BaseViewHolder>(data), CancelAdapt {
    private var bean: Int = 0//叮豆数

    init {
        addItemType(ShareInfoMuLtipleItem.FIRST_TYPE, R.layout.item_share_person)
        addItemType(ShareInfoMuLtipleItem.SECOND_TYPE, R.layout.item_share_comment)
    }

    override fun convert(helper: BaseViewHolder, item: ShareInfoMuLtipleItem) {
        when (helper.itemViewType) {

            ShareInfoMuLtipleItem.FIRST_TYPE -> {
                if (bean == 0) {
                    bean = item.likeScore
                }
                val tv_bean = helper.getView<View>(R.id.tv_bean) as TextView
                if (item.likeScore != 0) {
                    tv_bean.visibility = View.VISIBLE
                    tv_bean.text = "+${item.likeScore}"
                }
                val rb_like = helper.getView<View>(R.id.rb_like) as RadioButton
                rb_like.text = "${item.likeCount}"
                if (item.isMy == Constant.ISMY_TRUE) {//我的分享
                    val nav_up = mContext.resources.getDrawable(R.mipmap.like_false)
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight())
                    rb_like.setCompoundDrawables(nav_up, null, null, null)
                    rb_like.compoundDrawablePadding = 10
                } else {
                    if (0 == item.isLike) {//还没点赞
                        val nav_up = mContext.resources.getDrawable(R.mipmap.like_false)
                        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight())
                        rb_like.setCompoundDrawables(nav_up, null, null, null)
                        rb_like.compoundDrawablePadding = 10
                    } else {//已经点赞
                        val nav_up = mContext.resources.getDrawable(R.mipmap.like_true)
                        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight())
                        rb_like.setCompoundDrawables(nav_up, null, null, null)
                        rb_like.compoundDrawablePadding = 10
                    }
                }
                helper.addOnClickListener(R.id.rb_like)

                val ll_people = helper.getView<View>(R.id.ll_people) as LinearLayout
                val tv_pepole = helper.getView<View>(R.id.tv_pepole) as TextView
                val tv_playing = helper.getView<View>(R.id.tv_playing) as TextView
                tv_playing.text = "浏览量：${item.viewCount}"
                if (!TextUtils.isEmpty(item.userNames)) {
                    ll_people.visibility = View.VISIBLE
                    tv_pepole.text = "${item.userNames}觉得很赞"
                } else {
                    ll_people.visibility = View.GONE
                }
                val tv_share = helper.getView<View>(R.id.tv_share) as TextView
            }
            ShareInfoMuLtipleItem.SECOND_TYPE -> {

                val riv_comment = helper.getView<View>(R.id.riv_comment) as RoundImageView
                riv_comment.loadImage(
                    JHCImageConfig.Builder()
                        .imageView(riv_comment)
                        .url(Constant.IMAGE_PREFIX + item.userImg)
                        .isCircle(true)
                        .errorPic(R.drawable.avatar_default)
                        .placeholder(R.drawable.avatar_default)
                        .build()
                )

                helper.setText(R.id.tv_comment_person, item.userName)
                helper.setText(R.id.tv_comment_time, item.commentTime)

                try {
                    helper.setText(R.id.tv_comment, URLDecoder.decode(item.content, "UTF-8"))
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

            }
        }
    }

}