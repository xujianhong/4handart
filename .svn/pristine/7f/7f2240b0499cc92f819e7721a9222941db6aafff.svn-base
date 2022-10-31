package com.daomingedu.art.mvp.ui.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daomingedu.art.R
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.JHCImageConfig
import com.daomingedu.art.app.loadImage
import com.daomingedu.art.app.px
import com.daomingedu.art.mvp.model.entity.GradeBean
import com.daomingedu.art.util.Log
import com.google.gson.Gson

class HomeGradeAdapter(datas:MutableList<GradeBean>) :BaseQuickAdapter<GradeBean,BaseViewHolder>(R.layout.item_home_grade,datas){
    override fun convert(helper: BaseViewHolder, item: GradeBean) {
        helper.setText(R.id.tvGradeName,"${item.gradedNamae}")
        Glide.with(mContext).asBitmap().load(item.logoImg).into(helper.getView<ImageView>(R.id.ivGradeIcon))
        /*helper.getView<ImageView>(R.id.ivGradeIcon).apply {
            post {
                loadImage(
                    JHCImageConfig.Builder()
                        .imageRadius(10.px)
                        .imageView(this)
                        .url(item.logoImg)
                        .build()
                )
            }
        }*/
    }

}