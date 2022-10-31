package com.daomingedu.art.mvp.ui.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.daomingedu.art.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import java.io.File;
import java.util.List;

/**
 * 图片详情适配器
 * Created by jianhongxu on 2016/11/30.
 */

public class ViewPagerAdapter extends PagerAdapter {

    Activity activity;
    List<String> images;
    private PhotoViewAttacher.OnViewTapListener onViewTapListener;

    public ViewPagerAdapter(Activity activity, List<String> images, PhotoViewAttacher.OnViewTapListener onViewTapListener) {
        this.activity = activity;
        this.images = images;
        this.onViewTapListener = onViewTapListener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(activity).inflate(
                R.layout.item_imagedetails, null);
        final PhotoView photo_image_view = (PhotoView) view.findViewById(R.id.photo_image_view);
        final ProgressBar pb = (ProgressBar) view.findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);


        if (images.get(position).startsWith("http")) {
            Glide.with(activity).load(images.get(position))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            pb.setVisibility(View.GONE);
                            return false;
                        }
                    })
                 .into(photo_image_view);
        } else
            Glide.with(activity).load(new File(images.get(position))).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    pb.setVisibility(View.GONE);
                    return false;
                }
            }).into(photo_image_view);
        photo_image_view.setOnViewTapListener(onViewTapListener);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return images.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

}
