package com.daomingedu.art.mvp.ui.widget.playrecording;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.daomingedu.art.R;
import com.daomingedu.art.util.network.NetworkType;
import com.daomingedu.art.util.network.NetworkUtils;


/**
 * 播放录音View
 * Created by xjh on 2016/7/29.
 */
public class SingingRecordingView extends RelativeLayout implements View.OnClickListener {
    Context context;

    @BindView(R.id.ib_pause)
    ImageButton ib_pause;
    @BindView(R.id.tv_time_current)
    TextView tv_time_current;
    @BindView(R.id.sb_progress)
    SeekBar sb_progress;
    @BindView(R.id.tv_time)
    TextView tv_time;

    @BindView(R.id.pb_loading)
    ProgressBar pb_loading;

    @BindView(R.id.ll_bg)
    public LinearLayout ll_bg;

//    @ViewInject(R.id.video_loading)
//    FrameLayout video_loading;

    @BindView(R.id.ll_daily_recording)
    public LinearLayout ll_daily_recording;
    String url;//音频播放路径

    public Player player;

    boolean isFirst = true;


    private final static int PREPRE = 0x02;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==PREPRE){
                int min = ((int)msg.obj / 1000) / 60;//分钟
                int s = ((int)msg.obj / 1000) % 60;//秒
                //设置总的时长
                if (min < 10) {
                    tv_time.setText("0" + min + ":" + s);
                    if (s < 10)
                        tv_time.setText("0" + min + ":" + "0" + s);
                    else
                        tv_time.setText("0" + min + ":" + s);
                } else {
                    tv_time.setText(min + ":" + s);
                    if (s < 10)
                        tv_time.setText(min + ":" + "0" + s);
                    else
                        tv_time.setText(min + ":" + s);
                }
                isFirst = false;
//                    video_loading.setVisibility(GONE);
                ib_pause.setBackgroundResource(R.mipmap.timeout);
            }
        }
    };



    public SingingRecordingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if(!isInEditMode()) {
            View.inflate(context, R.layout.item_singing_recording, this);

            ButterKnife.bind(this);

            ib_pause.setOnClickListener(this);
            sb_progress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
            player = new Player(sb_progress, pb_loading,tv_time_current, new Player.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
//                if(mediaPlayer.isPlaying())
                    ib_pause.setBackgroundResource(R.mipmap.playpasue);
                }
            });
        }
    }

    /**
     * 设置音频播放路径
     *
     * @param url
     */
    public void setUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            isFirst = true;
            this.url = url;


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
    public void playUrl(String url){
        if (!TextUtils.isEmpty(url)) {
            this.url = url;
            if(player.mediaPlayer.isPlaying())
                player.mediaPlayer.stop();
            player.playUrl(this.url);
//            player.setOnPreparedListener(new Player.OnPreparedListener() {
//                @Override
//                public void OnPrepared(MediaPlayer mediaPlayer) {
//                    ib_pause.setClickable(true);
//                    Message message = Message.obtain();
//                    message.what = PREPRE;
//                    message.obj = mediaPlayer.getDuration();
//                    handler.sendMessage(message);
//
//                }
//            });
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ib_pause) {
            NetworkType networkType = NetworkUtils.getNetworkType(context.getApplicationContext());
            if(NetworkType.NETWORK_NO == networkType){
                new AlertDialog.Builder(context).setTitle("请连接网络")
                        .setPositiveButton("是", (dialog, which) -> {
                            ((Activity)context).finish();
                        }).show();
                return;
            }

            if(isFirst){//第一次播放音频
                ib_pause.setClickable(false);
                if (!TextUtils.isEmpty(url)) {
//                    video_loading.setVisibility(VISIBLE);
                    player.playUrl(this.url);
                    player.setOnPreparedListener(new Player.OnPreparedListener() {
                        @Override
                        public void OnPrepared(MediaPlayer mediaPlayer) {
                            ib_pause.setClickable(true);
                            Message message = Message.obtain();
                            message.what = PREPRE;
                            message.obj = mediaPlayer.getDuration();
                            handler.sendMessage(message);

                        }
                    });

                }

            }
            else {
                if (player.mediaPlayer.isPlaying()) {//暂停
                    ib_pause.setBackgroundResource(R.mipmap.playpasue);
                    player.pause();
                } else {//播放
                    ib_pause.setBackgroundResource(R.mipmap.timeout);
                    player.play();
                }
            }



        }
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()

            this.progress = progress * player.mediaPlayer.getDuration()
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress);
        }
    }


    public void onPause(){
        if(player.mediaPlayer.isPlaying()){
            ib_pause.setBackgroundResource(R.mipmap.playpasue);
            player.mediaPlayer.pause();
        }
    }

}
