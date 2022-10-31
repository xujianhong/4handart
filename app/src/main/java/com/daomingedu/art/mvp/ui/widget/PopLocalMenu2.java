package com.daomingedu.art.mvp.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.daomingedu.art.R;

import me.jessyan.autosize.AutoSize;

/**
 * 本地作品添加菜单
 * Created by xjh on 2016/7/25.
 */
public class PopLocalMenu2 extends PopupWindow {


    Button pop_video, pop_recording;
    View v;

    public PopLocalMenu2(Activity act, View.OnClickListener listener, boolean isShare) {
        super(act);
        AutoSize.cancelAdapt(act);
        LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.pop_local_menu2, null);
        pop_video = (Button) v.findViewById(R.id.pop_video);
        pop_video.setOnClickListener(listener);
        pop_recording = (Button) v.findViewById(R.id.pop_recording);
//        pop_ksong = (Button)v.findViewById(R.id.pop_ksong);

        if (isShare) {
            pop_video.setVisibility(View.GONE);
//            pop_ksong.setVisibility(View.GONE);
        }
        else {
            pop_video.setVisibility(View.VISIBLE);
//            pop_ksong.setVisibility(View.VISIBLE);

        }
//        pop_ksong.setOnClickListener(listener);
        pop_recording.setOnClickListener(listener);

        //设置SelectPicPopupWindow的View
        this.setContentView(v);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点�?
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背�?
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在�?�择框外面则�?毁弹出框
        v.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = v.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
}
