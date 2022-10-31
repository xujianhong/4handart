package com.daomingedu.art.mvp.ui.widget.selectimagevideo.listener;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.daomingedu.art.R;


/**
 * Created by jianhongxu on 2017/6/20.
 */

public class ItemClickSupport {
    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private View.OnClickListener mOnClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mOnItemClickListener!=null){
                RecyclerView.ViewHolder  holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView,holder.getAdapterPosition(),v);
            }
        }
    };
    private View.OnLongClickListener mOnLongClickListener =new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if(mOnItemLongClickListener !=null){
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
               return mOnItemLongClickListener.onItemLongClick(mRecyclerView,holder.getAdapterPosition(),v);
            }
            return false;
        }
    };
    private RecyclerView.OnChildAttachStateChangeListener mAttachStateChangeListener
            =new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if(mOnItemClickListener!=null){
                view.setOnClickListener(mOnClickListener);
            }
            if(mOnItemLongClickListener!=null){
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {
        }
    };

    private ItemClickSupport(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(R.id.item_click_support, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachStateChangeListener);
    }

    public static ItemClickSupport addTo(RecyclerView view){
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if(support ==null){
            support = new ItemClickSupport(view);
        }
        return support;
    }
    public static ItemClickSupport removeFrom(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachStateChangeListener);
        view.setTag(R.id.item_click_support, null);
    }

    public ItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
        return this;
    }


    public interface OnItemClickListener {

        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }
    public interface OnItemLongClickListener{
        boolean onItemLongClick(RecyclerView recyclerView, int position, View v);
    }
}
