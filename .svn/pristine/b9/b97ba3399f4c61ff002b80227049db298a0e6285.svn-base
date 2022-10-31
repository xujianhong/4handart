package com.daomingedu.art.mvp.ui.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daomingedu.art.R
import com.daomingedu.art.app.*
import com.daomingedu.art.mvp.model.entity.NewsBean
import me.jessyan.autosize.AutoSize

class HomeAdapter(datas: MutableList<NewsBean>) :
    BaseQuickAdapter<NewsBean, BaseViewHolder>(R.layout.item_home_news, datas) {
    override fun convert(helper: BaseViewHolder, item: NewsBean) {
        AutoSize.autoConvertDensityOfGlobal(mContext.getTopActivity())
        helper.setText(R.id.tvTitle,item.title)
        helper.setText(R.id.tvSourceOrigin,"${item.source}")
        helper.setText(R.id.tvTime,"${item.createTime}")
        /*helper.getView<ImageView>(R.id.ivCover).apply {
            post {
                loadImage(JHCImageConfig.Builder()
                    .imageRadius(10.px)
                    .imageView(this)
                    .url(Constant.IMAGE_PREFIX+item.image)
                    .build()
                )
            }
        }*/
        Glide.with(mContext).asBitmap().load(Constant.IMAGE_PREFIX+item.image).into(helper.getView<ImageView>(R.id.ivCover))
    }
}