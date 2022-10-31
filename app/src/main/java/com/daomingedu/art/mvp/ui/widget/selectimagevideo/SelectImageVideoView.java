package com.daomingedu.art.mvp.ui.widget.selectimagevideo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.AttributeSet;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daomingedu.art.R;
import com.daomingedu.art.mvp.ui.widget.selectimagevideo.itemdecoration.DividerGridItemDecoration;
import com.daomingedu.art.mvp.ui.widget.selectimagevideo.listener.GetFilePathListener;
import com.daomingedu.art.mvp.ui.widget.selectimagevideo.listener.ItemClickSupport;
import com.daomingedu.art.mvp.ui.widget.selectimagevideo.listener.OnSelectFolderListener;
import com.daomingedu.art.mvp.ui.widget.selectimagevideo.util.FileFolder;
import com.daomingedu.art.mvp.ui.widget.selectimagevideo.util.FindFileUtil;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jianhongxu on 2017/6/26.
 */

public class SelectImageVideoView extends LinearLayout {


    public static final int TYPE_IMAGE = 0x011; //选择图片
    public static final int TYPE_VIDEO = 0x012; //选择视频
    public static final int TYPE_AVATAR = 0x013;//选择头像
    public static final int SELECT_NUM = 6;//默认选择张数

    private RecyclerView rv_select_image_video;
    private TextView tv_folder;
    private Button btn_select_num;
    private ImageButton ib__folder_arrow_back;

    /**
     * 得到后退按钮
     * @return
     */
    public ImageButton getArrawBack() {
        return ib__folder_arrow_back;
    }


    private SelectImageVideoAdapter videoAdapter;
    private Map<String, List<FileFolder>> listMap; //总的数据
    private SelectFolderFragment fragment;

    private List<FileFolder> mSelectFileFolders; //选中的数据

    private int mSelectNum; //选择数量
    private int mSelectType = TYPE_VIDEO;
    private boolean mShowCamera;
    private File mFileCamera;//照相后得到文件

    private Context context;
    private GetFilePathListener getFilePathListener;

    public File getFileCamera() {
        return mFileCamera;
    }

    public void setFileCamera(File mFileCamera) {
        this.mFileCamera = mFileCamera;
    }

    public SelectImageVideoView(Context context) {
        super(context);
        initViews(context);
    }

    public SelectImageVideoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    private void initViews(Context context) {
        this.context = context;

        View.inflate(context, R.layout.view_select_image_video, this);
        rv_select_image_video = (RecyclerView) findViewById(R.id.rv_select_image_video);
        tv_folder = (TextView) findViewById(R.id.tv_folder);


        btn_select_num = (Button) findViewById(R.id.btn_select_num);

        ib__folder_arrow_back = (ImageButton)findViewById(R.id.ib__folder_arrow_back);

        rv_select_image_video.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3);
        rv_select_image_video.setLayoutManager(layoutManager);

        rv_select_image_video.addItemDecoration(new DividerGridItemDecoration(context));
        rv_select_image_video.setItemAnimator(new DefaultItemAnimator());


    }

    /**
     * @param selectType 图片{@link SelectImageVideoView#TYPE_IMAGE}   selectNum 1
     *                   视频{@link SelectImageVideoView#TYPE_VIDEO}   selectNum {@link SelectImageVideoView#SELECT_NUM}
     *                   头像{@link SelectImageVideoView#TYPE_AVATAR}  selectNum 1
     *                   <p>
     *                   showCamera false
     */
    public void setData(int selectType) {
        setData(selectType, false);
    }

    /**
     * @param selectType 图片{@link SelectImageVideoView#TYPE_IMAGE}   selectNum 1
     *                   视频{@link SelectImageVideoView#TYPE_VIDEO}   selectNum {@link SelectImageVideoView#SELECT_NUM}
     *                   头像{@link SelectImageVideoView#TYPE_AVATAR}  selectNum 1
     * @param showCamera 是否显示照相
     */
    public void setData(int selectType, boolean showCamera) {
        setData(selectType, selectType == TYPE_VIDEO ? 1 : selectType == TYPE_AVATAR ? 1 : SELECT_NUM, showCamera);
    }

    /**
     * @param selectType 图片{@link SelectImageVideoView#TYPE_IMAGE}
     *                   视频{@link SelectImageVideoView#TYPE_VIDEO}
     *                   头像{@link SelectImageVideoView#TYPE_AVATAR}
     * @param selectNum  选择数量
     * @param showCamera 是否显示照相
     */
    public void setData(int selectType, int selectNum, boolean showCamera) {
        mSelectType = selectType;
        mSelectNum = selectNum;

        mShowCamera = showCamera;

        if (videoAdapter == null) {
            videoAdapter = new SelectImageVideoAdapter(context);
            rv_select_image_video.setAdapter(videoAdapter);
        }

        videoAdapter.setShowCamera(mShowCamera);
        videoAdapter.setSelectNum(mSelectNum);

        ItemClickSupport.addTo(rv_select_image_video).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                if (getFilePathListener != null) {

                    if (mShowCamera && position == 0) {//打开相机
                        if (mSelectFileFolders.size() < mSelectNum) {
                            getFilePathListener.openCamera();
                        } else {
                            Toast.makeText(context, "最多只能选择" + mSelectNum + "个", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (mSelectNum <= 1) {
                            getFilePathListener.getFilePath(videoAdapter.getData().get(position).getFilePath());

                        } else {//多选
                            selectNumData(position);
                        }
                    }
                }
            }
        });

        new FindDataTask().execute(mSelectType);

        if (fragment == null) {
            fragment = new SelectFolderFragment();
        }
        fragment.addOnSelectFolderListener(new OnSelectFolderListener() {
            @Override
            public void onSelectFolder(String fileParentPath) {
                updateData(listMap, fileParentPath);
            }
        });
        if (mSelectFileFolders == null)
            mSelectFileFolders = new ArrayList<>();
        if (mSelectNum <= 1) {
            btn_select_num.setVisibility(GONE);
            btn_select_num.setClickable(false);
        } else {
            btn_select_num.setVisibility(VISIBLE);
            btn_select_num.setClickable(true);
            btn_select_num.setText("已选");
            btn_select_num.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mSelectFileFolders ==null||mSelectFileFolders.size()!=0){
                        ArrayList<String> list = new ArrayList<>();
                        for (FileFolder mSelectFileFolder : mSelectFileFolders) {
                            list.add(mSelectFileFolder.getFilePath());
                        }
                        getFilePathListener.getFilelistPath(list);
                    }
                }
            });
        }

    }


    public RecyclerView getRcyclerview() {
        return rv_select_image_video;
    }

    public void addGetFilePathListener(GetFilePathListener getFilePathListener) {
        this.getFilePathListener = getFilePathListener;
    }


    /**
     * 刷新数据
     *
     * @param stringListMap
     * @param key
     */
    private void updateData(Map<String, List<FileFolder>> stringListMap, @Nullable String key) {
        if (key == null) {
            key = FileFolder.TOTAL_FOLDER;
        }

        if (stringListMap != null) {
            List<FileFolder> list = null;
            for (String s : stringListMap.keySet()) {
                if (s.equals(key)) {
                    if (key.equals(FileFolder.TOTAL_FOLDER)) {
                        tv_folder.setText("所有");
                        tv_folder.setTag(FileFolder.TOTAL_FOLDER);

                    } else {
                        String[] strFolder = s.split("/");

                        tv_folder.setText(strFolder[strFolder.length - 1]);
                        tv_folder.setTag(s);
                    }
                    list = stringListMap.get(s);
                }

            }
            videoAdapter.setData(list);
            videoAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获得图片和视频资源
     */
    private class FindDataTask extends AsyncTask<Integer, Void, Map<String, List<FileFolder>>> {

        @Override
        protected Map<String, List<FileFolder>> doInBackground(Integer... params) {
            switch (params[0]) {
                case TYPE_AVATAR:
                case TYPE_IMAGE:
                    return FindFileUtil.findImageData(context);
                case TYPE_VIDEO:
                    return FindFileUtil.findVideoData(context);
                default:
                    return null;

            }
        }

        @Override
        protected void onPostExecute(Map<String, List<FileFolder>> stringListMap) {
            listMap = stringListMap;
            updateData(stringListMap, null);
            tv_folder.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFolder();
                }
            });


        }
    }


    private void showFolder() {

        if (listMap != null) {
            List<FileFolder> folders = new ArrayList<>();
            for (String s : listMap.keySet()) {
                FileFolder folder = new FileFolder();
                if (s.equals(tv_folder.getTag())) {
                    folder.setCheck(true);
                }
                folder.setFileParentPath(s);
                if (s.equals(FileFolder.TOTAL_FOLDER)) {
                    folder.setFileParentName("所有");
                    if (listMap.get(s).get(0).isShowCamera()) {
                        folder.setFilePath(listMap.get(s).get(1).getFilePath());
                    } else {
                        folder.setFilePath(listMap.get(s).get(0).getFilePath());
                    }
                    folders.add(0, folder);
                } else {
                    String[] strFolder = s.split("/");
                    folder.setFileParentName(strFolder[strFolder.length - 1]);
                    if (listMap.get(s).get(0).isShowCamera()) {
                        folder.setFilePath(listMap.get(s).get(1).getFilePath());
                    } else {
                        folder.setFilePath(listMap.get(s).get(0).getFilePath());
                    }
                    folders.add(folder);
                }


            }
            if (fragment == null) {
                fragment = new SelectFolderFragment();
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("folder", (Serializable) folders);
            fragment.setArguments(bundle);
            fragment.show(((FragmentActivity) context).getSupportFragmentManager(), "dialog");
        }


    }

    /**
     * 选择大于1的数据
     *
     * @param position
     */

    public void selectNumData(int position) {
        if (mSelectFileFolders == null) {
            mSelectFileFolders = new ArrayList<>();
        }
        List<FileFolder> mClassification = listMap.get(String.valueOf(tv_folder.getTag()));


        if (mSelectNum > 1) {//选择数量大于1
            mClassification.get(position).setCheck(!mClassification.get(position).isCheck());
            if (mClassification.get(position).isCheck()) { //为true是选中

                if (mSelectFileFolders.size() != 0) {
                    if (mSelectFileFolders.size() >= mSelectNum) {
                        Toast.makeText(context, "最多只能选择" + mSelectNum + "个", Toast.LENGTH_SHORT).show();
                        mClassification.get(position).setCheck(!mClassification.get(position).isCheck());
                        return;
                    }
                    if (!mSelectFileFolders.contains(mClassification.get(position))) {

                        mSelectFileFolders.add(mClassification.get(position));
                    }
                } else {
                    mSelectFileFolders.add(mClassification.get(position));
                }
            } else { //为false是未选中
                if (mSelectFileFolders.size() != 0) {
                    if (mSelectFileFolders.contains(mClassification.get(position))) {

                        mSelectFileFolders.remove(mClassification.get(position));
                    }
                }
            }
            btn_select_num.setText("已选(" + mSelectFileFolders.size() + ")");
            videoAdapter.notifyItemChanged(position);
        } else {//选择数量小于1
            Toast.makeText(context, mClassification.get(position).getFilePath(), Toast.LENGTH_SHORT).show();
        }

    }
}
