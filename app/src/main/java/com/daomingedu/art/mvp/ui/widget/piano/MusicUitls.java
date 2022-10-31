package com.daomingedu.art.mvp.ui.widget.piano;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import com.daomingedu.art.R;

import java.util.HashMap;

/**
 * 音效播放类
 * Created by xjh on 2016/7/11.
 */
public class MusicUitls {
   public  int music[] = {R.raw.as_0
            ,R.raw.b_0
            ,R.raw.c_1
            ,R.raw.cs_1
            ,R.raw.d_1
            ,R.raw.ds_1
            ,R.raw.e_1
            ,R.raw.f_1
            ,R.raw.fs_1
            ,R.raw.g_1
            ,R.raw.gs_1
            ,R.raw.a_2
            ,R.raw.as_2
            ,R.raw.b_2
            ,R.raw.c_2
            ,R.raw.cs_2
            ,R.raw.d_2
            ,R.raw.ds_2
            ,R.raw.e_2
            ,R.raw.f_2
            ,R.raw.fs_2
            ,R.raw.g_2
            ,R.raw.gs_2
            ,R.raw.a_3
            ,R.raw.as_3
            ,R.raw.b_3
            ,R.raw.c_3
            ,R.raw.cs_3
            ,R.raw.d_3
            ,R.raw.ds_3
            ,R.raw.e_3
            ,R.raw.f_3
            ,R.raw.fs_3
            ,R.raw.g_3
            ,R.raw.gs_3
            ,R.raw.a_4
            ,R.raw.as_4
            ,R.raw.b_4
            ,R.raw.c_4
            ,R.raw.cs_4
            ,R.raw.d_4
            ,R.raw.ds_4
            ,R.raw.e_4
            ,R.raw.f_4
            ,R.raw.fs_4
            ,R.raw.g_4
            ,R.raw.gs_4
            ,R.raw.a_5
            ,R.raw.as_5
            ,R.raw.b_5
            ,R.raw.c_5
            ,R.raw.cs_5
            ,R.raw.d_5
            ,R.raw.ds_5
            ,R.raw.e_5
            ,R.raw.f_5
            ,R.raw.fs_5
            ,R.raw.g_5
            ,R.raw.gs_5
            ,R.raw.a_6
            ,R.raw.as_6
            ,R.raw.b_6
            ,R.raw.c_6
            ,R.raw.cs_6
            ,R.raw.d_6
            ,R.raw.ds_6
            ,R.raw.e_6
            ,R.raw.f_6
            ,R.raw.fs_6
            ,R.raw.g_6
            ,R.raw.gs_6
            ,R.raw.a_7
            ,R.raw.as_7
            ,R.raw.b_7
            ,R.raw.c_7
            ,R.raw.cs_7
            ,R.raw.d_7
            ,R.raw.ds_7
            ,R.raw.e_7
            ,R.raw.f_7
            ,R.raw.fs_7
            ,R.raw.g_7
            ,R.raw.gs_7
            ,R.raw.a_8
            ,R.raw.as_8
            ,R.raw.b_8
            ,R.raw.c_8
    };

    public SoundPool soundPool;
    HashMap<Integer, Integer> soundPoolMap;
    Context context ;

    public MusicUitls(final Context context){
        this.context = context;

        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC,0);
        soundPoolMap  = new HashMap<>();

    }

    int currpaly = -1;
    public int  soundPlay(final int sound) {

        AudioManager am =(AudioManager)context.getSystemService(context.AUDIO_SERVICE);
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        final float volumnRatio = audioCurrentVolumn/audioMaxVolumn;
        if(!soundPoolMap.containsKey(sound)) {
            soundPoolMap.put(sound, soundPool.load(context, music[sound], 1));
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                    currpaly = soundPool.play(soundPoolMap.get(sound),volumnRatio,0,1,0,1);


                }
            });
        }
        else
            currpaly = soundPool.play(soundPoolMap.get(sound),volumnRatio,0,1,0,1);
        return currpaly;

    }
    public void soundPasue(int currpaly){

        soundPool.pause(currpaly);
    }
    public int soundOver() {
        return soundPool.play(soundPoolMap.get(1), 100, 100, 1, 0, 1.0f);
    }
    @Override
    protected void finalize() throws Throwable {
        if (soundPool != null) {
            soundPool.release();
        }
        super.finalize();
    }

}
