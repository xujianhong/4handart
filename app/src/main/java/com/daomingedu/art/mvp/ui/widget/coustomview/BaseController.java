package com.daomingedu.art.mvp.ui.widget.coustomview;

import android.content.Context;

import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by jianhongxu on 2017/7/4.
 */

public abstract class BaseController extends FrameLayout {
    public BaseController(@NonNull Context context) {
        super(context);
    }

    public BaseController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void setPlayer(CustomPlayer player);


    public abstract void setPlayerState(int currentState);


    public abstract void setBufferingUpdate(int bufferingUpdate);

}
