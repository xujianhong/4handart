package com.daomingedu.art.mvp.ui.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daomingedu.art.R
import com.daomingedu.art.mvp.ui.activity.UploadShareActivity
import java.io.File

class UploadPhotosAdapter(datas: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(
    R.layout.item_share_image, datas
) {
    override fun convert(helper: BaseViewHolder, item: String) {
        helper.getView<ImageView>(R.id.iv_photos).apply {
            post {
                if (UploadShareActivity.ADD_PHOTO_STR != item) {
                    Glide.with(context).load(File(item))
                        .apply(RequestOptions.centerCropTransform())
                        .into(this)
                } else {
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    setImageResource(R.mipmap.ic_add_photo)
                }
            }
        }
    }

}