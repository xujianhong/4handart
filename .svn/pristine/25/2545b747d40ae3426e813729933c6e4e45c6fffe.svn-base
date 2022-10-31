package com.daomingedu.art.mvp.ui.widget.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.daomingedu.art.R;


/**
 * Created by xjh on 2016/7/13.
 */
public class MyDialog extends BaseDialog {

    Context context;

    public MyDialog(Context context) {
        super(context, R.style.Dialog);
    }

    public MyDialog(Context context, int layout) {
        super(context, layout, R.style.Dialog);
        this.context = context;
    }

    @Override
    public BaseDialog showNoNetWork() {
        return showCancle("没有链接网络");
    }


    @Override
    public BaseDialog showCancle(String msg) {
        return showCancle(msg, null);
    }


    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }

    @Override
    public BaseDialog setOnClickView(View view) {
        return super.setOnClickView(view);
    }

    @Override
    public BaseDialog setOnClickView(View view, ClickListener listener) {
        return super.setOnClickView(view, listener);
    }

    @Override
    public BaseDialog setViewText(int viewId, String text) {
        return super.setViewText(viewId, text);
    }

    @Override
    public BaseDialog showCancle(String msg, String btn_text) {
        if (!isShowing()) {
            setContentView(R.layout.dialog_msg_one);
            TextView tv_msg = findView(R.id.tv_dialog_msg);
            tv_msg.setText(msg);
            Button btn = findView(R.id.btn_mid);
            if (!TextUtils.isEmpty(btn_text))
                btn.setText(btn_text);
            setOnClickView(btn);
            show();
        }
        return this;
    }


    public BaseDialog showCancleAndSure(String title, String msg, String cancleBtnText, String sureBtnText) {
        if (!isShowing()) {
            setContentView(R.layout.dialog_msg_two);

            TextView tv_title = findView(R.id.tv_dialog_title);
            tv_title.setText(title);
            TextView tv_msg = findView(R.id.tv_dialog_msg);
            tv_msg.setText(msg);
            Button bt_cancle = findView(R.id.btn_cancle);
            Button bt_sure = findView(R.id.btn_sure);
            bt_cancle.setText(cancleBtnText);
            setOnClickView(bt_cancle);
            bt_sure.setText(sureBtnText);
            show();
        }
        return this;
    }

    @Override
    public BaseDialog showSelectCancle(String var1, String var2, String cancleBtnText) {
        if (!isShowing()) {
            setContentView(R.layout.dialog_msg_select);

            Button btn_selecttext = findView(R.id.btn_selecttext);
            btn_selecttext.setText(var1);
            Button btn_selectrecodong = findView(R.id.btn_selectrecodong);
            btn_selectrecodong.setText(var2);
            Button btn_mid = findView(R.id.btn_mid);
            btn_mid.setText(cancleBtnText);
            setOnClickView(btn_mid);
            show();
        }
        return this;
    }


    TextView tipTextView;

    @Override
    public BaseDialog showLoading(String msg) {
        if (!isShowing()) {
//            View v = LayoutInflater.from(context).inflate(R.layout.dialog_loading,(ViewGroup) null);
            setContentView(R.layout.dialog_loading);
            if (msg != null) {
                tipTextView = findView(R.id.tipTextView);// 提示文字
                tipTextView.setText(msg);// 设置加载信息
            }
            show();
        }
        return this;
    }

    @Override
    public BaseDialog showLoading(String msg, boolean isprogress) {
        if (!isShowing()) {
//            View v = LayoutInflater.from(context).inflate(R.layout.dialog_loading,(ViewGroup) null);
            setContentView(R.layout.dialog_loading_updata);
            if (msg != null) {
                textView = findView(R.id.tipTextView);// 提示文字
                textView.setText(msg);// 设置加载信息
            }
            if (isprogress) {
                pb_loading = findView(R.id.pb_loading);
                pb_loading.setMax(100);
            }

            show();
        }
        return this;
    }


}
