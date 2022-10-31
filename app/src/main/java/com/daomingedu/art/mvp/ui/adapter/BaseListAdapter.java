package com.daomingedu.art.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据适配器基类
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected List<T> mList;

    protected Context mContext;

    protected LayoutInflater mInflater;

    public BaseListAdapter(Context context) {
        this.mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setList(List<T> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public List<T> getList() {
        return this.mList;
    }

    public void add(T t) {
        if (mList == null)
            mList = new ArrayList<T>();
        mList.add(t);
        notifyDataSetChanged();
    }

    public void addAll(List<T> list) {
        if (mList == null)
            mList = new ArrayList<T>();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void deleteAll() {
        if (mList != null) {
            mList.removeAll(mList);
            notifyDataSetChanged();
        }
    }

    public void delete(int position) {
        if (mList != null) {
            mList.remove(position);
            notifyDataSetChanged();
        }
    }

    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    public Object getItem(int arg0) {
        return mList == null ? null : mList.get(arg0);
    }

    public long getItemId(int arg0) {
        return arg0;
    }
}
