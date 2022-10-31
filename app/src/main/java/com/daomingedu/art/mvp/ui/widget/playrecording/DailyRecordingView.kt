package com.daomingedu.art.mvp.ui.widget.playrecording

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Message

import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import butterknife.BindView
import butterknife.ButterKnife
import com.daomingedu.art.R
import com.daomingedu.art.util.network.NetworkType
import com.daomingedu.art.util.network.NetworkUtils
import kotlinx.android.synthetic.main.item_daily_recording.view.*
import me.jessyan.autosize.internal.CancelAdapt


/**
 * 播放录音View
 * Created by xjh on 2016/7/29.
 */
class DailyRecordingView(internal var context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs),
    View.OnClickListener, CancelAdapt {


    internal var url: String?=null//音频播放路径

    lateinit var player: Player

    internal var isFirst = true
    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == PREPRE) {
                val min = msg.obj as Int / 1000 / 60//分钟
                val s = msg.obj as Int / 1000 % 60//秒
                //设置总的时长
                if (min < 10) {
                    tv_time!!.text = "0$min:$s"
                    if (s < 10)
                        tv_time!!.text = "0$min:0$s"
                    else
                        tv_time!!.text = "0$min:$s"
                } else {
                    tv_time!!.text = "$min:$s"
                    if (s < 10)
                        tv_time!!.text = "$min:0$s"
                    else
                        tv_time!!.text = "$min:$s"
                }
                isFirst = false
                //                    video_loading.setVisibility(GONE);
                ib_pause.setBackgroundResource(R.mipmap.timeout)
            }
        }
    }


    init {
        if (!isInEditMode) {
            View.inflate(context, R.layout.item_daily_recording, this)

            ib_pause.setOnClickListener(this)
            sb_progress.setOnSeekBarChangeListener(SeekBarChangeEvent())
            player = Player(sb_progress, pb_loading, tv_time_current, Player.OnCompletionListener {
                //                if(mediaPlayer.isPlaying())
                ib_pause!!.setBackgroundResource(R.mipmap.playpasue)
            })
        }
    }

    /**
     * 设置音频播放路径
     *
     * @param url
     */
    fun setUrl(url: String?) {
        if (!TextUtils.isEmpty(url)) {

            this.url = url


            //            if(player.mediaPlayer.isPlaying()) {
            //                ib_pause.setBackgroundResource(R.drawable.qcloud_player_media_pause);
            //
            //            }

        }
    }

    /**
     * 设置url播放
     * @param url
     */
    fun playUrl(url: String) {
        if (!TextUtils.isEmpty(url)) {
            this.url = url
            player.playUrl(this.url)
            player.setOnPreparedListener { mediaPlayer ->
                ib_pause!!.isClickable = true
                val message = Message.obtain()
                message.what = PREPRE
                message.obj = mediaPlayer.duration
                handler.sendMessage(message)
            }
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.ib_pause) {

            val networkType = NetworkUtils.getNetworkType(context.applicationContext)
            if (NetworkType.NETWORK_NO == networkType) {
                AlertDialog.Builder(context).setTitle("请连接网络")
                    .setPositiveButton("是") { dialog, which -> (context as Activity).finish() }.show()
                return
            }

            if (isFirst) {//第一次播放音频
                ib_pause!!.isClickable = false
                if (!TextUtils.isEmpty(url)) {
                    //                    video_loading.setVisibility(VISIBLE);
                    player.playUrl(this.url)
                    player.setOnPreparedListener { mediaPlayer ->
                        ib_pause!!.isClickable = true
                        val message = Message.obtain()
                        message.what = PREPRE
                        message.obj = mediaPlayer.duration
                        handler.sendMessage(message)
                    }

                }

            } else {
                if (player.mediaPlayer.isPlaying) {//暂停
                    ib_pause!!.setBackgroundResource(R.mipmap.playpasue)
                    player.pause()
                } else {//播放
                    ib_pause!!.setBackgroundResource(R.mipmap.timeout)
                    player.play()
                }
            }


        }
    }

    internal inner class SeekBarChangeEvent : SeekBar.OnSeekBarChangeListener {
        var progress: Int = 0

        override fun onProgressChanged(
            seekBar: SeekBar, progress: Int,
            fromUser: Boolean
        ) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()

            this.progress = progress * player.mediaPlayer.duration / seekBar.max
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress)
        }
    }


    fun onPause() {
        if (player.mediaPlayer.isPlaying) {
            ib_pause!!.setBackgroundResource(R.mipmap.playpasue)
            player.mediaPlayer.pause()
        }
    }

    companion object {


        private val PREPRE = 0x02
    }

}
