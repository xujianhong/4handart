package com.daomingedu.art.mvp.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import com.daomingedu.art.R;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.internal.CancelAdapt;

/**
 * Created by jianhongxu on 2017/5/2.
 */

public class PopUploadMenu extends PopupWindow implements CancelAdapt {

    Button pop_upload_photos, pop_upload_video,pop_upload_recording;

    View v;

    public PopUploadMenu(Activity act, final View.OnClickListener listener){
        super(act);
        AutoSize.cancelAdapt(act);
        LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.pop_upload_menu, null);
        pop_upload_photos = (Button)v.findViewById(R.id.pop_upload_photos);
        pop_upload_video = (Button)v.findViewById(R.id.pop_upload_video);
        pop_upload_recording = (Button) v.findViewById(R.id.pop_upload_recording);

        if(listener!=null){
            pop_upload_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    listener.onClick(v);
                }
            });
            pop_upload_photos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    listener.onClick(v);
                }
            });
            pop_upload_recording.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    listener.onClick(v);
                }
            });
        }
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

    }
}
