package com.daomingedu.art.mvp.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by xjh on 2016/7/13.
 */
public abstract class BaseDialog extends Dialog {
    //    public static String msgError = "服务器正忙……";
//    public static String titleError = "网络错误";
//    public static String jsonError = "服务器返回的数据有误";
//    public static String leftBtnText = "取消";
//    public static String rightBtnText = "重试";
    ProgressBar pb_loading;
    TextView textView;

    public BaseDialog(Context context, int style) {
        super(context, style);
    }

    public BaseDialog(Context context, int layout, int style) {
        super(context, style);
        this.setContentView(layout);
    }

    public BaseDialog setOnClickView(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseDialog.this.dismiss();
            }
        });
        return this;
    }

    public BaseDialog setOnClickView(int viewId, final ClickListener listener) {
        View v = this.findViewById(viewId);
        if (v != null) {
            v.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    BaseDialog.this.dismiss();
                    listener.onClick(v);
                }
            });
        }

        return this;
    }

    public BaseDialog setOnClickView(View view, final ClickListener listener) {
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BaseDialog.this.dismiss();
                listener.onClick(v);
            }
        });
        return this;
    }

    public BaseDialog setViewText(int viewId, String text) {
        TextView v = (TextView) this.findView(viewId);
        if (v != null) {
            v.setText(text);
        }

        return this;
    }

    public abstract BaseDialog showNoNetWork();

    public abstract BaseDialog showLoading(String var1);

    public abstract BaseDialog showLoading(String var1, boolean var2);

    public abstract BaseDialog showCancle(String var1);

    public abstract BaseDialog showCancle(String var1, String var2);

    public abstract BaseDialog showCancleAndSure(String var1, String var2, String var3, String var4);

    public abstract BaseDialog showSelectCancle(String var1, String var2, String var3);

    public ProgressBar getShowLoading(){
        return this.pb_loading !=null? this.pb_loading:null;

    }
    public TextView getTextView(){
        return this.textView !=null?this.textView:null;
    }
    public <T extends View> T findView(int resId) {
        return (T) this.findViewById(resId);
    }
}
