package com.daomingedu.art.mvp.ui.adapter;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.daomingedu.art.R;
import com.daomingedu.art.mvp.model.LocalWork;

import java.sql.Date;
import java.text.SimpleDateFormat;


/**
 * 本地作品适配器
 * Created by xjh on 2016/8/17.
 */
public class LocalAdapter extends BaseListAdapter<LocalWork> {
    private Context context;
    LocalWork work;

    private OnShowListener listener;
    public LocalAdapter(Context context) {
        super(context);
        this.context = context;
    }
    @SuppressLint("SimpleDateFormat")
    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        if(v == null){
            v = mInflater.inflate(R.layout.item_local_work,null);
        }
        //添加布局动画
        LayoutTransition lt = new LayoutTransition();
        lt.setAnimator(LayoutTransition.APPEARING, ObjectAnimator.ofFloat(v, "scaleX",0, 1));
        lt.setDuration(300);
        ((LinearLayout)v).setLayoutTransition(lt);

        if(i>=mList.size())return v;

        work = (LocalWork)mList.get(i);
        TextView tv_text = (TextView)v.findViewById(R.id.tv_text);
        tv_text.setText(work.getName());
        if(work.getType()==1){//视频
            Drawable nav_up=context.getResources().getDrawable(R.mipmap.localvideo);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            tv_text.setCompoundDrawables(nav_up,null,null, null);
            tv_text.setCompoundDrawablePadding(20);
        }
        else if(work.getType() == 2){//录音
            Drawable nav_up=context.getResources().getDrawable(R.mipmap.recording);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            tv_text.setCompoundDrawables(nav_up,null,null, null);
            tv_text.setCompoundDrawablePadding(20);
        }
        else if(work.getType() == 3){//视唱
            Drawable nav_up=context.getResources().getDrawable(R.mipmap.ksong);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            tv_text.setCompoundDrawables(nav_up,null,null, null);
            tv_text.setCompoundDrawablePadding(20);
        }
        TextView tv_time = (TextView)v.findViewById(R.id.tv_time);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date resultdate = new Date(work.getCreatetime());
        tv_time.setText(sdf.format(resultdate));

        CheckBox cb_del =  (CheckBox) v.findViewById(R.id.cb_del);
        cb_del.setChecked(work.isChecked());
        listener.show(cb_del);
        return v;
    }

    public void addOnShowListner(OnShowListener listener){
        this.listener = listener;
    }
    public interface OnShowListener{
        void show(CheckBox cb_del);
    }
}
