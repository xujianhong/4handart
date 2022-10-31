package com.daomingedu.art.mvp.ui.widget.piano;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 钢琴黑键的位置
 * Created by xjh on 2016/7/11.
 */
public class BlackLayout extends ViewGroup {

    int  pScreenUnit;
    private List<Integer> mLeftPositions;

    public BlackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLeftPositions = new ArrayList<Integer>();
    }

    private void addPosition(int pScreenWidth, int pChildWidth) {
        if (this.getChildCount() < 5)
        pScreenUnit = pScreenWidth / 2;
        else
            pScreenUnit = pScreenWidth / 7;
        mLeftPositions.clear();
        mLeftPositions.add(pScreenUnit - 2 * pChildWidth / 3);
        mLeftPositions.add(2 * pScreenUnit - pChildWidth / 3);
        mLeftPositions.add(4 * pScreenUnit - 2 * pChildWidth / 3);
        mLeftPositions.add(5 * pScreenUnit - pChildWidth / 2);
        mLeftPositions.add(6 * pScreenUnit - pChildWidth / 3);
    }

    @Override
    protected void onLayout(boolean arg0, int l, int t, int r, int b) {

        int childCount = this.getChildCount();
        if (this.getChildCount() >= 1) {
            addPosition(this.getWidth(), this.getChildAt(0).getWidth());
        }
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            child.measure(r - l, b - t);
        }
        for (int j = 0; j < childCount; j++) {
            View child = this.getChildAt(j);
            child.setVisibility(View.VISIBLE);
            child.measure(r - l, b - t);
            int childWidth = child.getMeasuredWidth();
            int childHeight = ArmsUtils.getScreenHeidth(getContext()) / 4;
            if(j ==0)
            child.layout(mLeftPositions.get(j)+10, t, mLeftPositions.get(j) + childWidth, t + childHeight);
            else if(j == 1 || j == 4)
                child.layout(mLeftPositions.get(j)-10, t, mLeftPositions.get(j) + childWidth-20, t + childHeight);
            else if(j == 2)
                child.layout(mLeftPositions.get(j)+10, t, mLeftPositions.get(j) + childWidth+5, t + childHeight);
            else if(j == 3)
                child.layout(mLeftPositions.get(j)-5, t, mLeftPositions.get(j) + childWidth-10, t + childHeight);

        }
    }
}
