package com.daomingedu.ijkplayertest;

import android.content.res.Configuration;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.daomingedu.ijkplayertest.widget.PlayView;
import com.daomingedu.ijkplayertest.widget.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    IjkVideoView ijk;



    String path ="rtmp://live.hkstv.hk.lxdns.com/live/hks";


    Button btn_path1;

    Button btn_path2;


    private boolean mBackPressed;

    PlayView playView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        findViewById(R.id.btn_path1).setOnClickListener(this);

        findViewById(R.id.btn_path2).setOnClickListener(this);

        playView = new PlayView(this).setPath(path,false);


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        playView.onConfigurationChanged(newConfig);

        super.onConfigurationChanged(newConfig);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        playView.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        playView.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if(mBackPressed||!ijk.isBackgroundPlayEnabled()){
//            ijk.stopPlayback();
//            ijk.release(true);
//            ijk.stopBackgroundPlay();//关闭后台播放
//        }else{
//            ijk.enterBackground();//后台播放
//        }
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btn_path1){
            path = "/storage/emulated/0/DCIM/Video/V61101-152815.mp4";

        }else if(v.getId() == R.id.btn_path2){
            path ="http://gz.bcebos.com/v1/online-teaching/video/161006142629750.mp4?authorization=bce-auth-v1%2Fcc9788d7a80945358ea829b56516ad3c%2F2016-11-01T09%3A43%3A41Z%2F10800%2F%2F5a7e017eeecec6da525b881902eeabcadd2d3b671507ac753ab45fa33665d406";
        }
        playView.setPath(path,true);
    }
}
