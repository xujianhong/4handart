package com.daomingedu.art.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daomingedu.art.R
import com.daomingedu.art.db.VideoListItem
import com.daomingedu.art.util.StringUtils

/**
 * Description
 * Created by jianhongxu on 2021/11/23
 */
class VideoListAdapter(datas:MutableList<VideoListItem>):BaseQuickAdapter<VideoListItem,BaseViewHolder>(
    R.layout.item_videolist,datas) {
    override fun convert(helper: BaseViewHolder, item: VideoListItem) {
        helper.addOnClickListener(R.id.item_videolist_scan)
        helper.addOnClickListener(R.id.item_videolist_delete)

        Glide.with(mContext).load(item.path).into(
            helper.getView(R.id.item_videolist_iv)
        )

        if (item.isSelect)

            helper.getView<ImageView>(R.id.item_video_select).setImageResource(R.drawable.item_video_select_true)
        else
            helper.getView<ImageView>(R.id.item_video_select).setImageResource(R.drawable.item_video_select_false)

//        helper.setText(R.id.video_number,"视频序号: ${item.id}")


        val videoTime = StringUtils.getVedioTotalTime(item.path).toInt()/1000;

        val minute = videoTime/60

        val strMinute = if(minute >=10){
            minute.toString()
        }else{
            "0${minute}"
        }

        val second = videoTime%60
        val strSecond = if(second>=10){
            second.toString()
        }else{
            "0${second}"
        }

        helper.setText(R.id.textView2,"视频时长: ${strMinute}:${strSecond}")
    }
}