package com.daomingedu.art.mvp.ui.widget.selectimagevideo;

import android.content.Context;
import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.daomingedu.art.R;
import com.daomingedu.art.mvp.ui.widget.selectimagevideo.util.FileFolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择图片视频适配器
 * Created by jianhongxu on 2017/6/19.
 */

public class SelectImageVideoAdapter extends RecyclerView.Adapter<SelectImageVideoAdapter.SelectViewHolder> {

    private static final String TAG = "SelectImageVideoAdapter";
    private Context context;
    private List<FileFolder> mFolder = new ArrayList<>();


    private int mSelectNum; //选择数量

    private boolean showCamera;//是否显示照相

    public SelectImageVideoAdapter(Context context) {
        this.context = context;
    }

    public List<FileFolder> getData() {
        return mFolder;
    }


    public void setData(List<FileFolder> mFloder) {
        if (showCamera) {

            FileFolder folder = new FileFolder();
            folder.setShowCamera(showCamera);
            if (!mFloder.get(0).isShowCamera()) {

                mFloder.add(0, folder);
            }

        }
        this.mFolder = mFloder;
    }

    public void setShowCamera(boolean showCamera) {
        this.showCamera = showCamera;
    }

    public void setSelectNum(int selectNum) {
        mSelectNum = selectNum;
    }

    @Override
    public SelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new SelectViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_select_image_video, parent, false));
    }

    @Override
    public void onBindViewHolder(final SelectViewHolder holder, int position) {
        FileFolder folder = mFolder.get(position);
        if (folder.isShowCamera()) {
            if (holder.vs_camera.getParent() != null && holder.vs_camera.getParent() instanceof ViewGroup) {
                holder.vs_camera.inflate();
            } else {
                holder.vs_camera.setVisibility(View.VISIBLE);
            }
        } else {
            holder.vs_camera.setVisibility(View.GONE);


            if (folder.getFileType().equals(FileFolder.TYPE_VIDEO)) {
                holder.tv_select_image.setVisibility(View.VISIBLE);
                holder.tv_select_image.setText(folder.getFileLength());
            } else if (folder.getFileType().equals(FileFolder.TYPE_IMAGE)) {
                holder.tv_select_image.setVisibility(View.GONE);
            }

            Glide.with(context)
                    .load(new File(folder.getFilePath()))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if (holder.vs_no_image.getParent() != null && holder.vs_no_image.getParent() instanceof ViewGroup) {
                                holder.vs_no_image.inflate();
                            } else {
                                holder.vs_no_image.setVisibility(View.VISIBLE);
                            }

                            return true;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.vs_no_image.setVisibility(View.GONE);
                            return false;
                        }
                    })

                    .into(holder.iv_select_image);
            if (mSelectNum <= 1) {
                holder.btn_select_image_video.setVisibility(View.GONE);
                holder.vs_select_image.setVisibility(View.GONE);
            } else {
                holder.btn_select_image_video.setVisibility(View.VISIBLE);
                if (folder.isCheck()) {
                    holder.btn_select_image_video.setImageResource(R.mipmap.ic_select_true);
                    if (holder.vs_select_image.getParent() != null && holder.vs_select_image.getParent() instanceof ViewGroup) {
                        holder.vs_select_image.inflate();
                    } else {
                        holder.vs_select_image.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.btn_select_image_video.setImageResource(R.mipmap.ic_select_false);
                    holder.vs_select_image.setVisibility(View.GONE);
                }
            }
        }

    }


    @Override
    public int getItemCount() {
        return mFolder == null ? 0 : mFolder.size();
    }


    public class SelectViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_select_image;
        TextView tv_select_image;
        ImageButton btn_select_image_video;

        ViewStub vs_select_image;
        ViewStub vs_camera;
        ViewStub vs_no_image;

        public SelectViewHolder(View itemView) {
            super(itemView);
            iv_select_image = (ImageView) itemView.findViewById(R.id.iv_select_image);
            tv_select_image = (TextView) itemView.findViewById(R.id.tv_select_image);
            btn_select_image_video = (ImageButton) itemView.findViewById(R.id.btn_select_image_video);
            vs_select_image = (ViewStub) itemView.findViewById(R.id.vs_select_image);
            vs_camera = (ViewStub) itemView.findViewById(R.id.vs_camera);
            vs_no_image = (ViewStub) itemView.findViewById(R.id.vs_no_image);


//            if (cbListener != null) {
//                btn_select_image_video.setClickable(true);
//                btn_select_image_video.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        cbListener.onSelect(getAdapterPosition());
//                    }
//                });
//            } else {
//                btn_select_image_video.setClickable(false);
//            }
        }


    }

//    public void addAdapterCBListener(AdapterCBListener cbListener) {
//        this.cbListener = cbListener;
//    }

}
