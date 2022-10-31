package com.daomingedu.art.mvp.ui.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.daomingedu.art.R;


public class MyToast {

    private static Toast toast;
    private static ImageView toastImage;
    private static TextView toastText;
    private static final int NOICON = -1;

    public static void init(Context ctx) {
        if (toast == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.toast_dialog, null);

            toastImage = (ImageView) layout.findViewById(R.id.iv_toastImage);
            toastText = (TextView) layout.findViewById(R.id.toastText);
            //text.setText("完全自定义Toast完全自定义Toast");
            toast = new Toast(ctx);
//            toast.setGravity(Gravity.CENTER, 0, 5);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
        }
    }

    public static void show(String text, int toastIcon) {
        if (toastIcon != -1) {
            toastImage.setVisibility(View.VISIBLE);
            toastImage.setImageResource(toastIcon);
        }
        else{
            toastImage.setVisibility(View.GONE);
        }
//        toastImage.setBackgroundResource(toastIcon);
        toastText.setText(text);
        toast.show();
    }

    public static void showOk(String text) {
        show(text, R.mipmap.ok);
    }

    public static void showError(String text) {
        show(text, R.mipmap.error);
    }

    public static void show(String text) {
        show(text, NOICON);
    }
}
