package com.daomingedu.art.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daomingedu.art.R
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.JHCImageConfig
import com.daomingedu.art.app.loadImage
import com.daomingedu.art.mvp.model.entity.BlockUserBean

class BlockUsersAdapter(datas:MutableList<BlockUserBean>):BaseQuickAdapter<BlockUserBean,BaseViewHolder>(R.layout.item_block_users,datas){
    override fun convert(helper: BaseViewHolder, item: BlockUserBean) {
        val headView = helper.getView<ImageView>(R.id.ivAvatar)

        headView.loadImage(
            JHCImageConfig.Builder()
                .imageView(headView)
                .url(Constant.IMAGE_PREFIX+item.image)
                .isCircle(true)
                .errorPic(R.drawable.avatar_default)
                .placeholder(R.drawable.avatar_default)
                .build())

        helper.setText(R.id.tvNickName,item.nickName)
        helper.addOnClickListener(R.id.btnCancelBlock)
    }

}