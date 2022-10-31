package com.daomingedu.art.mvp.ui.widget.playrecording;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import me.jessyan.autosize.utils.LogUtils;

import java.io.IOException;
import java.util.Formatter;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 音频播放类
 * Created by xjh on 2016/7/29.
 */
public class Player implements MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnInfoListener,
        MediaPlayer.OnErrorListener {
    MediaPlayer mediaPlayer;
    private SeekBar skbProgress;
    private ProgressBar pb_loading;

    private TextView timecurrent;

    private OnCompletionListener listener;//完成监听
    private OnPreparedListener preparedListener;//加载监听
    private int mDuration; //总时长
    private OnErrorListener onErrorListener;
    private Timer mTimer;

    private TimerTask mTimerTask;

    Player(SeekBar skbProgress, ProgressBar pb_loading, TextView timeCurrent, OnCompletionListener listener) {
        this.skbProgress = skbProgress;
        this.pb_loading = pb_loading;
        this.timecurrent = timeCurrent;
        this.listener = listener;
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setLooping(false);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnInfoListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnCompletionListener(this);

        } catch (Exception e) {
            LogUtils.e("error" + e);
        }


    }



    private void startTimer() {
        cancelTimer();//每次timerTask都要重新初始化
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if (mediaPlayer == null)
                        return;
                    boolean isPlaying = false;
                    try {
                        isPlaying = mediaPlayer.isPlaying();
                    } catch (IllegalStateException e) {
                        mediaPlayer = null;
                        mediaPlayer = new MediaPlayer();
                    }
                    if (isPlaying && !skbProgress.isPressed()) {
                        handleProgress.sendEmptyMessage(0);
                    }
                }
            };
        }

        mTimer.schedule(mTimerTask, 0, 300);


    }

    private void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    private Handler handleProgress = new Handler() {
        public void handleMessage(Message msg) {
            if (mediaPlayer == null) {
                return;
            }
            int position = mediaPlayer.getCurrentPosition();
            timecurrent.setText(formatTime(position));
            if (mDuration > 0) {
                int pos = skbProgress.getMax() * position / mDuration;
                skbProgress.setProgress(pos);
            }
        }

        ;
    };
    //*****************************************************


    void playUrl(String videoUrl) {
        pb_loading.setVisibility(View.VISIBLE);
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(videoUrl);
            mediaPlayer.prepareAsync();//prepare之后自动播放
        } catch (IllegalArgumentException | IllegalStateException | IOException e) {

            e.printStackTrace();
        }

    }

    void play() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            startTimer();
        }
    }

    void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            cancelTimer();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int bufferingProgress) {
        skbProgress.setSecondaryProgress(bufferingProgress);
    }

    /**
     * 通过onPrepared播放
     */
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        pb_loading.setVisibility(View.GONE);
        mDuration = mediaPlayer.getDuration();
        mediaPlayer.start();
        LogUtils.e("mediaPlayer" + "onPrepared");
        preparedListener.OnPrepared(mediaPlayer);

        startTimer();
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        LogUtils.e("mediaPlayer" + "onCompletion");

        cancelTimer();
        listener.onCompletion(mediaPlayer);
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {

        return onErrorListener != null && onErrorListener.onError(mp, what, extra);
    }

    interface OnCompletionListener {
        void onCompletion(MediaPlayer mediaPlayer);
    }

    interface OnPreparedListener {
        void OnPrepared(MediaPlayer mediaPlayer);
    }

    interface OnErrorListener {
        boolean onError(MediaPlayer mp, int what, int extra);
    }

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        this.preparedListener = onPreparedListener;
    }

    public void srtOnErrorListener(OnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
    }


    /**
     * 将毫秒数格式化为"##:##"的时间
     *
     * @param milliseconds 毫秒数
     * @return ##:##
     */
    public String formatTime(long milliseconds) {
        if (milliseconds <= 0 || milliseconds >= 24 * 60 * 60 * 1000) {
            return "00:00";
        }
        long totalSeconds = milliseconds / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        StringBuilder stringBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(stringBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }
}
