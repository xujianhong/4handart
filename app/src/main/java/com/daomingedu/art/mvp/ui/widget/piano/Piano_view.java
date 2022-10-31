package com.daomingedu.art.mvp.ui.widget.piano;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.daomingedu.art.R;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjh on 2016/9/21.
 */

public class Piano_view extends View {

    Activity activity;
    Context context;
    public List<Piano_Key> keySet = new ArrayList<>();
    public MusicUitls uitls;

    public Piano_view(Context context) {
        super(context);
        activity = (Activity) context;
        initSound();
        initBitmap();
        initKeySet();

    }

    public Piano_view(Context context, AttributeSet attrs) {
        super(context, attrs);
        activity = (Activity) context;
        this.context = context;
        initSound();
        initBitmap();
        initKeySet();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 绘制区域
         *
         */
        canvas.drawColor(Color.BLACK);
        //绘制按键
        for (Piano_Key temp : keySet) {
            temp.drawSelf(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int index = event.getActionIndex();
        float x = event.getX(index);
        float y = event.getY(index);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                for (Piano_Key temp : keySet) {
                    temp.keyDownAction(x, y);
                    postInvalidate();//刷新界面
                }
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    float tempX = event.getX(i);
                    float tempY = event.getY(i);
                    int id = event.getPointerId(i);
                    for (Piano_Key temp : keySet) {
                        temp.keyMoveAction(tempX, tempY, id);

                        postInvalidate();//刷新界面
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                for (Piano_Key temp : keySet) {
//                    temp.isPushDown = false;
                    temp.keyUpAction(x, y);

                    postInvalidate();//刷新界面
                }
                default:

                break;
        }
        getParent().requestDisallowInterceptTouchEvent(true);//防止与ScrollView 手势冲突
        return true;
    }


    //------------图片
    Bitmap pic_black[] = new Bitmap[2];
    Bitmap pic_white[] = new Bitmap[7];

    //初始化图片
    public void initBitmap() {
        Resources res = getResources();

        pic_black[0] = BitmapFactory.decodeResource(res, R.drawable.black);
        pic_black[1] = BitmapFactory.decodeResource(res, R.drawable.black_pressed);

        pic_white[0] = BitmapFactory.decodeResource(res, R.drawable.white);
        pic_white[1] = BitmapFactory.decodeResource(res, R.drawable.white_pressed);
        pic_white[2] = BitmapFactory.decodeResource(res, R.drawable.white_c);
        pic_white[3] = BitmapFactory.decodeResource(res, R.drawable.white_xiaoc);
        pic_white[4] = BitmapFactory.decodeResource(res, R.drawable.white_c1);
        pic_white[5] = BitmapFactory.decodeResource(res, R.drawable.white_c2);
        pic_white[6] = BitmapFactory.decodeResource(res, R.drawable.white_c3);
    }

    //初始化按键的方法
    public void initKeySet() {

        //========黑剑
        int width_black = (int) (ArmsUtils.getScreenHeidth(getContext()) / 8);
        int height_black = ArmsUtils.getScreenHeidth(getContext()) / 3;
        int currY_black = 0;
        //----------白键--------
        int width_white = (int) (ArmsUtils.getScreenHeidth(getContext()) / 11.5);
        int height_white = ArmsUtils.getScreenHeidth(getContext()) / 3 * 2;

        //----------------黑剑
        int black_area1[][] = {{width_white - 40, currY_black, width_black, height_black}};//x,y,width,height


        //大字一组
        int black_area2[][] = {{width_white * 3 - 40, currY_black, width_black, height_black}};//x,y,width,height

        int black_area3[][] = {{width_white * 4 - 40, currY_black, width_black, height_black}};//x,y,width,height


        int black_area4[][] = {{width_white * 6 - 40, currY_black, width_black, height_black}};//x,y,width,height

        int black_area5[][] = {{width_white * 7 - 40, currY_black, width_black, height_black}};//x,y,width,height

        int black_area6[][] = {{width_white * 8 - 40, currY_black, width_black, height_black}};//x,y,width,height


        //--------------------白
        int white_area1[][] = {{0, currY_black, width_white, height_black},
                {0, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height

        int white_area2[][] = {{width_white + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height

        //大字一组
        int white_area3[][] = {{width_white * 2, currY_black, width_white, height_black},
                {width_white * 2, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height

        int white_area4[][] = {{width_white * 3 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 3, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height

        int white_area5[][] = {{width_white * 4 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 4, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height

        int white_area6[][] = {{width_white * 5, currY_black, width_white, height_black},
                {width_white * 5, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height

        int white_area7[][] = {{width_white * 6 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 6, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height

        int white_area8[][] = {{width_white * 7 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 7, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height

        int white_area9[][] = {{width_white * 8 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 8, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        //------------白键-------------------------
        keySet.add(new Piano_Key(activity, white_area1, pic_white[0], pic_white[1], 0));

        //------------白键-------------------------
        keySet.add(new Piano_Key(activity, white_area2, pic_white[0], pic_white[1], 2));

        //大字组
        keySet.add(new Piano_Key(activity, white_area3, pic_white[0], pic_white[1], 3));
        keySet.add(new Piano_Key(activity, white_area4, pic_white[0], pic_white[1], 5));
        keySet.add(new Piano_Key(activity, white_area5, pic_white[0], pic_white[1], 7));
        keySet.add(new Piano_Key(activity, white_area6, pic_white[0], pic_white[1], 8));
        keySet.add(new Piano_Key(activity, white_area7, pic_white[0], pic_white[1], 10));
        keySet.add(new Piano_Key(activity, white_area8, pic_white[0], pic_white[1], 12));
        keySet.add(new Piano_Key(activity, white_area9, pic_white[0], pic_white[1], 14));

        //-------------黑剑
        keySet.add(new Piano_Key(activity, black_area1, pic_black[0], pic_black[1], 1));
        keySet.add(new Piano_Key(activity, black_area2, pic_black[0], pic_black[1], 4));
        keySet.add(new Piano_Key(activity, black_area3, pic_black[0], pic_black[1], 6));
        keySet.add(new Piano_Key(activity, black_area4, pic_black[0], pic_black[1], 9));
        keySet.add(new Piano_Key(activity, black_area5, pic_black[0], pic_black[1], 11));
        keySet.add(new Piano_Key(activity, black_area6, pic_black[0], pic_black[1], 13));

        //大字组
        // 白
        int white_area10[][] = {{width_white * 9, currY_black, width_white, height_black},
                {width_white * 9, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height


        keySet.add(new Piano_Key(activity, white_area10, pic_white[2], pic_white[1], 15));

        int white_area11[][] = {{width_white * 10 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 10, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area11, 17, true);
        int white_area12[][] = {{width_white * 11 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 11, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area12, 19, true);
        int white_area13[][] = {{width_white * 12, currY_black, width_white, height_black},
                {width_white * 12, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area13, 20, true);
        int white_area14[][] = {{width_white * 13 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 13, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area14, 22, true);
        int white_area15[][] = {{width_white * 14 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 14, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area15, 24, true);
        int white_area16[][] = {{width_white * 15 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 15, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area16, 26, true);
        //黑键
        int black_area7[][] = {{width_white * 10 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area7, 16, false);
        int black_area8[][] = {{width_white * 11 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area8, 18, false);

        int black_area9[][] = {{width_white * 13 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area9, 21, false);
        int black_area10[][] = {{width_white * 14 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area10, 23, false);
        int black_area11[][] = {{width_white * 15 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area11, 25, false);


        //小字组
        int white_area17[][] = {{width_white * 16, currY_black, width_white, height_black},
                {width_white * 16, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height

        keySet.add(new Piano_Key(activity, white_area17, pic_white[3], pic_white[1], 27));
        int white_area18[][] = {{width_white * 17 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 17, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area18, 29, true);
        int white_area19[][] = {{width_white * 18 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 18, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area19, 31, true);
        int white_area20[][] = {{width_white * 19, currY_black, width_white, height_black},
                {width_white * 19, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area20, 32, true);
        int white_area21[][] = {{width_white * 20 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 20, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area21, 34, true);
        int white_area22[][] = {{width_white * 21 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 21, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area22, 36, true);
        int white_area23[][] = {{width_white * 22 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 22, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area23, 38, true);
        //黑键
        int black_area12[][] = {{width_white * 17 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area12, 28, false);
        int black_area13[][] = {{width_white * 18 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area13, 30, false);

        int black_area14[][] = {{width_white * 20 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area14, 33, false);
        int black_area15[][] = {{width_white * 21 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area15, 35, false);
        int black_area16[][] = {{width_white * 22 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area16, 37, false);

        //小字一组
        int white_area24[][] = {{width_white * 23, currY_black, width_white, height_black},
                {width_white * 23, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height

        keySet.add(new Piano_Key(activity, white_area24, pic_white[4], pic_white[1], 39));
        int white_area25[][] = {{width_white * 24 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 24, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area25, 41, true);
        int white_area26[][] = {{width_white * 25 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 25, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area26, 43, true);
        int white_area27[][] = {{width_white * 26, currY_black, width_white, height_black},
                {width_white * 26, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area27, 44, true);
        int white_area28[][] = {{width_white * 27 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 27, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area28, 46, true);
        int white_area29[][] = {{width_white * 28 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 28, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area29, 48, true);
        int white_area30[][] = {{width_white * 29 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 29, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area30, 50, true);
        //黑键
        int black_area17[][] = {{width_white * 24 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area17, 40, false);
        int black_area18[][] = {{width_white * 25 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area18, 42, false);

        int black_area19[][] = {{width_white * 27 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area19, 45, false);
        int black_area20[][] = {{width_white * 28 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area20, 47, false);
        int black_area21[][] = {{width_white * 29 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area21, 49, false);


        //小字二组
        int white_area31[][] = {{width_white * 30, currY_black, width_white, height_black},
                {width_white * 30, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height

        keySet.add(new Piano_Key(activity, white_area31, pic_white[5], pic_white[1], 51));
        int white_area32[][] = {{width_white * 31 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 31, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area32, 53, true);
        int white_area33[][] = {{width_white * 32 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 32, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area33, 55, true);
        int white_area34[][] = {{width_white * 33, currY_black, width_white, height_black},
                {width_white * 33, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area34, 56, true);
        int white_area35[][] = {{width_white * 34 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 34, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area35, 58, true);
        int white_area36[][] = {{width_white * 35 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 35, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area36, 60, true);
        int white_area37[][] = {{width_white * 36 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 36, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area37, 62, true);
        //黑键
        int black_area22[][] = {{width_white * 31 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area22, 52, false);
        int black_area23[][] = {{width_white * 32 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area23, 54, false);

        int black_area24[][] = {{width_white * 34 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area24, 57, false);
        int black_area25[][] = {{width_white * 35 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area25, 59, false);
        int black_area26[][] = {{width_white * 36 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area26, 61, false);


        //小字三组
        int white_area38[][] = {{width_white * 37, currY_black, width_white, height_black},
                {width_white * 37, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height

        keySet.add(new Piano_Key(activity, white_area38, pic_white[6], pic_white[1], 63));
        int white_area39[][] = {{width_white * 38 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 38, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area39, 65, true);
        int white_area40[][] = {{width_white * 39 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 39, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area40, 67, true);
        int white_area41[][] = {{width_white * 40, currY_black, width_white, height_black},
                {width_white * 40, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area41, 68, true);
        int white_area42[][] = {{width_white * 41 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 41, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area42, 70, true);
        int white_area43[][] = {{width_white * 42 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 42, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area43, 72, true);
        int white_area44[][] = {{width_white * 43 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 43, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area44, 74, true);
        //黑键
        int black_area27[][] = {{width_white * 38 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area27, 64, false);
        int black_area28[][] = {{width_white * 39 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area28, 66, false);

        int black_area29[][] = {{width_white * 41 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area29, 69, false);
        int black_area30[][] = {{width_white * 42 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area30, 71, false);
        int black_area31[][] = {{width_white * 43 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area31, 73, false);


        //小字四组
        int white_area45[][] = {{width_white * 44, currY_black, width_white, height_black},
                {width_white * 44, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area45, 75, true);
        int white_area46[][] = {{width_white * 45 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 45, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area46, 77, true);
        int white_area47[][] = {{width_white * 46 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 46, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area47, 79, true);
        int white_area48[][] = {{width_white * 47, currY_black, width_white, height_black},
                {width_white * 47, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area48, 80, true);
        int white_area49[][] = {{width_white * 48 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 48, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area49, 82, true);
        int white_area50[][] = {{width_white * 49 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 49, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area50, 84, true);
        int white_area51[][] = {{width_white * 50 + width_black / 2, currY_black, width_white - width_black / 2, height_black},
                {width_white * 50, currY_black + height_black, width_white, height_white - height_black}};//x,y,width,height
        initKey(white_area51, 86, true);
        //黑键
        int black_area32[][] = {{width_white * 45 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area32, 76, false);
        int black_area33[][] = {{width_white * 46 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area33, 78, false);

        int black_area34[][] = {{width_white * 48 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area34, 81, false);
        int black_area35[][] = {{width_white * 49 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area35, 83, false);
        int black_area36[][] = {{width_white * 50 - 40, currY_black, width_black, height_black}};//x,y,width,height
        initKey(black_area36, 85, false);

    }

    public void initKey(int[][] area, int soudid, boolean iswhite) {
        if (iswhite) {
            keySet.add(new Piano_Key(activity, area, pic_white[0], pic_white[1], soudid));
        } else {
            keySet.add(new Piano_Key(activity, area, pic_black[0], pic_black[1], soudid));
        }
    }

    public void initSound()//加载声音资源
    {
        uitls = new MusicUitls(activity);
    }

    //播放声音的方法
    public int playSound(int sound) {
        return uitls.soundPlay(sound);
    }

    //播放声音的方法
    public void pasueSound(int curplay) {
        uitls.soundPasue(curplay);
    }


    //回收bitamp
    public void recycle() {

        for (Bitmap bit : pic_black) {
            if (bit != null && !bit.isRecycled()) {
                bit.recycle();
                bit = null;
            }
        }

        for (Bitmap bit : pic_white) {
            if (bit != null && !bit.isRecycled()) {
                bit.recycle();
                bit = null;
            }
        }
        for (Piano_Key key : keySet) {
            if (key.keyBitmapDown != null && !key.keyBitmapDown.isRecycled()) {
                key.keyBitmapDown.recycle();
                key.keyBitmapDown = null;
            }
            if (key.keyBitmapUp != null && !key.keyBitmapUp.isRecycled()) {
                key.keyBitmapUp.recycle();
                key.keyBitmapUp = null;
            }
        }
    }
}
