package com.daomingedu.art.mvp.ui.widget.playrecording;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.daomingedu.art.R;
import com.daomingedu.art.mvp.ui.widget.coustomview.CustomPlayer;
import com.daomingedu.art.mvp.ui.widget.coustomview.PlayerUtils;
import me.jessyan.autosize.utils.LogUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jianhongxu on 2017/10/25.
 */

public class QuestionRecordingView extends LinearLayout implements CustomPlayer, View.OnClickListener {

    private final Context mContext;

    @BindView(R.id.tv_record_time)
    TextView tv_record_time;
    @BindView(R.id.iv_record_animation)
    ImageView iv_record_animation;
    @BindView(R.id.ll_record_main)
    LinearLayout ll_record_main;
    @BindView(R.id.pb_record)
    ProgressBar pb_record;


    private MediaPlayer mediaPlayer;
    int mCurrentState = STATE_IDLE;//当前状态
    private int mDuration = STATE_MEDIA_DATA_ERROR; //总的时长
    private int mCurrentPosition = STATE_MEDIA_DATA_ERROR;


    private String mUrlData; //数据源

    private Map<String, String> mHeaders; //请求头

    private Timer mUpdateTimer;
    private TimerTask mUpdateTimerTask;
    private AnimationDrawable animationDrawable;

    public QuestionRecordingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (!isInEditMode()) {
            View.inflate(context, R.layout.view_question_record, this);
            ButterKnife.bind(this);
            ll_record_main.setOnClickListener(this);
        }
    }


    public void initPlayer() {
        if (mCurrentState == STATE_IDLE && mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            reset();
        }
        mediaPlayer.setLooping(false);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //keep screen on
        ((AppCompatActivity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mediaPlayer.setOnPreparedListener(mOnPreparedListener);
        mediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mediaPlayer.setOnCompletionListener(mOnCompletionListener);
        mediaPlayer.setOnErrorListener(mOnErrorListener);
        mediaPlayer.setOnInfoListener(mOnInfoListener);

        refreshState(STATE_INITIALIZED);
        preparePlayer();
    }

    private void preparePlayer() {
        LogUtils.d("preparePlayer: mCurrentState:" + mCurrentState);
        try {
            if (mCurrentState == STATE_INITIALIZED) {
                mediaPlayer.setDataSource(mContext, Uri.parse(mUrlData), mHeaders);
            }

            mediaPlayer.prepareAsync();
            refreshState(STATE_PREPARE);


        } catch (IOException e) {
            LogUtils.e("perparePlayer: " + e.toString());
            e.printStackTrace();
            refreshState(STATE_ERROR);

        }

    }


    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (mCurrentState != STATE_PREPARE) return;

            mDuration = mp.getDuration(); //得到数据的总时长


            mp.setVolume(1f, 1f);//设置音量


            refreshState(STATE_PREPARE_END);


        }
    };

    private MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer MediaPlayer, int i) {
            LogUtils.d("onBufferingUpdate: " + i);

        }
    };

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer MediaPlayer) {

            LogUtils.d("onCompletion: media play Completion");
            if (mCurrentState != STATE_ERROR) {
                refreshState(STATE_COMPLETED);
            } else {
                refreshState(STATE_CURRENT_NULL);
            }
        }
    };
    private MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
            refreshState(STATE_ERROR);
            return false;
        }
    };

    private MediaPlayer.OnInfoListener mOnInfoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mediaPlayer, int what, int i1) {
            switch (what) {
                /*
                  未指定的媒体播放器信息
                 */
                case MediaPlayer.MEDIA_INFO_UNKNOWN:
                    LogUtils.d("onInfo: MEDIA_INFO_UNKNOWN");
                    break;
                /*
                  视频对于解码器来说太复杂了：它不能足够快地解码帧。 在这个阶段可能只有音频播放正常。
                 */
                case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                    LogUtils.d("onInfo: MEDIA_INFO_VIDEO_TRACK_LAGGING");
                    break;
                /*
                  播放器刚推出第一个视频帧进行渲染。
                 */
                case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    LogUtils.d("onInfo: MEDIA_INFO_VIDEO_RENDERING_START");
                    break;
                /*
                  MediaPlayer暂时暂停内部播放，以缓冲更多的数据。
                 */
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    LogUtils.d("onInfo: MEDIA_INFO_BUFFERING_START");
                    refreshState(STATE_BUFFING_START);
                    break;
                /*
                  填充缓冲区后MediaPlayer正在恢复播放。
                 */
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    LogUtils.d("onInfo: MEDIA_INFO_BUFFERING_END");

                    refreshState(STATE_BUFFING_END);
                    break;
                case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                    LogUtils.d("onInfo: MEDIA_INFO_BAD_INTERLEAVING");
                    break;
                case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                    LogUtils.d("onInfo: MEDIA_INFO_NOT_SEEKABLE");
                    break;
                case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                    LogUtils.d("onInfo: MEDIA_INFO_METADATA_UPDATE");
                    break;
                case MediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE:
                    LogUtils.d("onInfo: MEDIA_INFO_UNSUPPORTED_SUBTITLE");
                    break;
                case MediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT:
                    LogUtils.d("onInfo: MEDIA_INFO_SUBTITLE_TIMED_OUT");
                    break;
            }
            return false;
        }
    };


    public CustomPlayer setUrlData(String urlData, Map<String, String> headers) {
        mHeaders = headers;
        mUrlData = urlData;

        return this;
    }

    @Override
    public void start() {
        if (TextUtils.isEmpty(mUrlData)) {
            LogUtils.e("The Video Path can not be empty");
            return;
//            throw new RuntimeException("The Video Path can not be empty");

        }
        initPlayer();
    }

    private void refreshState(int currentState) {
        if (currentState != STATE_CURRENT_NULL) {
            mCurrentState = currentState;
        }
        if (isPlaying()) {
            if (animationDrawable == null)
                animationDrawable = (AnimationDrawable) iv_record_animation.getDrawable();
            animationDrawable.start();
        } else {
            if (animationDrawable != null)
                animationDrawable.stop();
        }
        switch (mCurrentState) {
            case CustomPlayer.STATE_IDLE:

                cancelUpdate();
                tv_record_time.setText(PlayerUtils.formatTime(0));
//                ib_screen.setImageResource(R.mipmap.icon_full_screen);
                break;
            case CustomPlayer.STATE_INITIALIZED:
                tv_record_time.setText(PlayerUtils.formatTime(0));

            case CustomPlayer.STATE_PREPARE:
                ll_record_main.setEnabled(false);
                pb_record.setVisibility(VISIBLE);
            case CustomPlayer.STATE_BUFFING_START:

                break;

            case CustomPlayer.STATE_PREPARE_END:
                pb_record.setVisibility(INVISIBLE);
                tv_record_time.setText(PlayerUtils.formatTime(mDuration));//设置最开始显示时长
                ll_record_main.setEnabled(true);
            case CustomPlayer.STATE_BUFFING_END:

                break;
            case CustomPlayer.PLAYER_STATE_PAUSE:
                cancelUpdate();

                break;
            case CustomPlayer.PLAYER_STATE_PLAYING:
                startUpdate();

                break;

            case CustomPlayer.STATE_COMPLETED:

                cancelUpdate();

                break;

            case CustomPlayer.STATE_ERROR:

                break;
            case CustomPlayer.STATE_END:

                break;
        }
    }

    private void cancelUpdate() {
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }
        if (mUpdateTimerTask != null) {
            mUpdateTimerTask.cancel();
            mUpdateTimerTask = null;
        }
    }

    private void startUpdate() {
        cancelUpdate();
        if (mUpdateTimer == null) {
            mUpdateTimer = new Timer();
        }
        if (mUpdateTimerTask == null) {
            mUpdateTimerTask = new TimerTask() {
                @Override
                public void run() {

                    QuestionRecordingView.this.post(new Runnable() {
                        @Override
                        public void run() {
                            updateTime();
                        }
                    });
                }
            };
        }

        mUpdateTimer.schedule(mUpdateTimerTask, 0, 300);
    }

    private void updateTime() {
//        Log.d(TAG, "updateTime: " + player.getCurrentState());

        //导航栏的时间显示跟着播放器走
        long position = getCurrentPosition();
        long duration = getDuration();

        if (position == CustomPlayer.STATE_MEDIA_DATA_ERROR
                || duration == CustomPlayer.STATE_MEDIA_DATA_ERROR) {
            cancelUpdate();
            return;
        }
        tv_record_time.setText(PlayerUtils.formatTime(duration - position));
//        Log.e(TAG, "updateTime: "+position);
    }

    @Override
    public void pause() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mCurrentPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
                if (!mediaPlayer.isPlaying())
                    refreshState(PLAYER_STATE_PAUSE);
                else {
                    refreshState(STATE_CURRENT_NULL);
                }
            } else {
                refreshState(STATE_CURRENT_NULL);
            }
        }
    }

    @Override
    public void resume() {
        if (mediaPlayer != null) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                if (mediaPlayer.isPlaying()) {
                    refreshState(PLAYER_STATE_PLAYING);
                } else {
                    refreshState(STATE_CURRENT_NULL);
                }
            } else {
                refreshState(STATE_CURRENT_NULL);
            }
        }
    }

    @Override
    public int getCurrentState() {
        return mCurrentState;
    }

    @Override
    public boolean isIdle() {
        return mCurrentState == STATE_IDLE;
    }

    @Override
    public boolean isPreparing() {
        return mCurrentState == STATE_PREPARE;
    }

    @Override
    public boolean isPrePared() {
        return mCurrentState == STATE_PREPARE_END;
    }

    @Override
    public boolean isPlaying() {
        if (mediaPlayer != null) {
            try {
                return mediaPlayer.isPlaying();
            } catch (IllegalStateException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean isBuffing() {
        return mCurrentState == STATE_BUFFING_START;
    }

    @Override
    public boolean isCompleted() {
        return mCurrentState == STATE_COMPLETED;
    }

    @Override
    public void seekTo(int position) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(position);
        }
    }

    @Override
    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            refreshState(STATE_END);
        }

    }

    @Override
    public void reset() {

        if (mediaPlayer != null) {
            mediaPlayer.reset();

        }
        refreshState(STATE_IDLE);

    }

    @Override
    public int getDuration() {

        return mDuration;

    }

    @Override
    public long getCurrentPosition() {
        return !isPlaying() ? mCurrentPosition : mediaPlayer.getCurrentPosition();
    }

    @Override
    public void setCurrentPosition(int currentPosition) {
        mCurrentPosition = currentPosition;
    }

    @Override
    public int getDisplayState() {
        return 0;
    }

    @Override
    public void fullScreen() {

    }

    @Override
    public void smallScreen() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_record_main) {
            if (!isPlaying())
                resume();
        }
    }
}
