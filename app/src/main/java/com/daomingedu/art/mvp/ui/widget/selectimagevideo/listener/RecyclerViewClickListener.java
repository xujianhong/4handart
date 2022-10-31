package com.daomingedu.art.mvp.ui.widget.selectimagevideo.listener;

import android.content.Context;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * recyclerView item 点击事件
 * Created by jianhongxu on 2017/6/19.
 */

public class RecyclerViewClickListener implements RecyclerView.OnItemTouchListener {


    private GestureDetector mGestureDetector;
    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);

        void onItemLongClick(View v, int position);
    }

    public RecyclerViewClickListener(Context context, final RecyclerView view, OnItemClickListener listener) {
        mItemClickListener = listener;
        mGestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {
                    //单击事件
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        View childView = view.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null && mItemClickListener != null) {
                            mItemClickListener.onItemClick(childView, view.getChildAdapterPosition(childView));
                            return true;
                        }
                        return false;
                    }

                    //长按事件
                    @Override
                    public void onLongPress(MotionEvent e) {
                        View childView = view.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null && mItemClickListener != null) {
                            mItemClickListener.onItemLongClick(childView, view.getChildAdapterPosition(childView));
                        }

                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mGestureDetector.onTouchEvent(e)) {
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
