package com.daomingedu.art.mvp.ui.widget.piano;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.daomingedu.art.mvp.ui.activity.PianoActivity;


/*
 * 每个按键
 * 按键的位置
 * 按键的大小
 * 按键的两幅背景图
 * 按钮的声音
 */
public class Piano_Key {
    private Activity activity;

    private int[][] area;
    public Bitmap keyBitmapUp;
    public Bitmap keyBitmapDown;
    private int soundId;
    public boolean isPushDown = false;
    private int id = -1;
    private int currPointerIndex;//当前按键的指针   0 首次  1 然后
    private boolean isInKeyArea;// 判断当前触控点是否在按键范围内

    int curPlay = -1;

    //---------------------构造器-------------------------
    public Piano_Key
    (
            Activity activity,
            int[][] area,
            Bitmap keyBitmapUp,
            Bitmap keyBitmapDown,
            int soundId
    ) {
        this.activity = activity;
        this.area = area;
        if (area.length == 2) {
            this.keyBitmapUp = Bitmap.createScaledBitmap(keyBitmapUp, area[1][2], area[0][3] + area[1][3], false);
            this.keyBitmapDown = Bitmap.createScaledBitmap(keyBitmapDown, area[1][2], area[0][3] + area[1][3], false);
        } else {
            this.keyBitmapUp = Bitmap.createScaledBitmap(keyBitmapUp, area[0][2], area[0][3], false);
            this.keyBitmapDown = Bitmap.createScaledBitmap(keyBitmapDown, area[0][2], area[0][3], false);
        }
        this.soundId = soundId;
    }

    //--------播放声音------------------------------------
    public int playKeySound() {
        return ((PianoActivity) activity).piano.playSound(soundId);
    }

    public void pauseKeySound(int curPlay) {
        ((PianoActivity) activity).piano.pasueSound(curPlay);
    }

    //当前点被按下
    public void keyDownAction(float pointX, float pointY) {
        if (isInArea(pointX, pointY)) {
            if (currPointerIndex == 0)//手指针
            {
                currPointerIndex = 1;
                isPushDown = true;
                //播放声音
                curPlay = playKeySound();
            }
        }
    }

    //判断当前点是否被抬起
    public void keyUpAction(float pointX, float pointY) {
        //判断当前点是否在该键范围内,并且已经按下
        if (isInArea(pointX, pointY)) {
            if (currPointerIndex == 1)//手指针
            {
                currPointerIndex = 0;
                isPushDown = false;
                if (curPlay != -1)
                    pauseKeySound(curPlay);
//                android.util.Log.e("======","curPlay"+curPlay);
            }
        }
    }

    //移动触摸事件
    public void keyMoveAction(float pointX, float pointY, int id) {
        //移入事件

        if (isInArea(pointX, pointY)) {
            this.id = id;
            if (!isInKeyArea) {
                isInKeyArea = true;
                //当按键指针为0时\
                if (currPointerIndex == 0) {
                    currPointerIndex = 1;
                    isPushDown = true;
                    //播放声音

                    playKeySound();
                }
            }
        }
        //移出事件
        if (!isInArea(pointX, pointY)) {
            if (isInKeyArea && (this.id == id)) {
                isInKeyArea = false;
                //当按键的指针为1时
                if (currPointerIndex == 1) {
                    currPointerIndex = 0;

                    isPushDown = false;
//					pauseKeySound();
                }
            }
        }
    }

    //绘制方法
    public void drawSelf(Canvas canvas) {
        if (area.length == 1) {
            if (isPushDown) {
                canvas.drawBitmap(keyBitmapDown, area[0][0], area[0][1], null);
            } else {
                canvas.drawBitmap(keyBitmapUp, area[0][0], area[0][1], null);
            }
        } else {
            if (isPushDown) {
                canvas.drawBitmap(keyBitmapDown, area[1][0], area[0][1], null);
            } else {
                canvas.drawBitmap(keyBitmapUp, area[1][0], area[0][1], null);
            }
        }
    }

    //判断当前触控点是否在按键范围内
    public boolean isInArea(float pointX, float pointY) {
        int length = area.length;
        for (int i = 0; i < length; i++) {
            if (pointX > area[i][0] && pointX < area[i][0] + area[i][2] && pointY > area[i][1] && pointY < area[i][1] + area[i][3]) {
                return true;
            }
        }
        return false;
    }
}
