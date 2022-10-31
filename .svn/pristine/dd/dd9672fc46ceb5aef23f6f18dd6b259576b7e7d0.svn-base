package com.daomingedu.art.mvp.ui.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daomingedu.art.R
import com.daomingedu.art.app.Constant

class ImageItemAdapter(data:List<String>) :BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_image,data){
    override fun convert(helper: BaseViewHolder, item: String) {
        val imageView = helper.getView<ImageView>(R.id.iv_item_image)
        Glide.with(mContext)
            .load(Constant.IMAGE_PREFIX+item)
            .apply(RequestOptions().error(R.drawable.avatar_default))
//            .apply(RequestOptions.overrideOf(DensityUtil.dip2px(context, 110), DensityUtil.dip2px(context, 110)))
            .into(imageView)
    }

}