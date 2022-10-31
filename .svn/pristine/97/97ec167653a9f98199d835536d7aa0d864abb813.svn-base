package com.daomingedu.art.mvp.ui.widget.selectimagevideo.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查找数据（图片，视频）
 * Created by jianhongxu on 2017/6/18.
 */

public class FindFileUtil {



    private static final String TAG = "FindFileUtil";
    private static ContentResolver mContentResolver;

    public static Map<String, List<FileFolder>> findImageData(Context context) {
        if (mContentResolver == null) {
            mContentResolver = context.getContentResolver();
        }
        String[] projection = new String[]{   //得到数据的那些东西
                MediaStore.Images.ImageColumns.DATA, //路径到磁盘上的文件。
                MediaStore.Images.ImageColumns.DISPLAY_NAME, //文件显示的名称
                MediaStore.Images.ImageColumns.MIME_TYPE //文件类型

        };
        String selection = MediaStore.Images.ImageColumns.MIME_TYPE + "=?"; // 文件过滤器（类似与于sql语句的where）
        String[] selectionArgs = new String[]{    //查找过滤器
                "image/jpeg"
        };


        Cursor mCursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, //查找源
                projection,     //projection 是定义返回的数据，
                selection,     // selection 通常的sql 语句，
                selectionArgs,  // 例如  selection=MediaStore.Images.ImageColumns.MIME_TYPE+"=? " 那么 selectionArgs=new String[]{"jpg"};
                MediaStore.Images.ImageColumns.DATE_ADDED + " DESC" //查找结果的顺序 这里是按添加时间逆顺序排列 （DESC 逆顺  ASC 顺序）无关大小写
        );


        if (mCursor == null) {
            return null;
        }
        Map<String, List<FileFolder>> fileFolderMap = new HashMap<>();
        List<FileFolder> totalFolder= new ArrayList<>();
        while (mCursor.moveToNext()) {
            FileFolder folder = new FileFolder();
            folder.setFileName(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)));
            folder.setFilePath(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));
            folder.setFileType(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.MIME_TYPE)));
            File parentFile = new File(folder.getFilePath()).getParentFile();
            if (parentFile != null){
                folder.setFileParentPath(parentFile.getAbsolutePath());
            }else {
                continue;
            }

            totalFolder.add(folder);
            Log.d(TAG, "findImageData:   \nfileName:" + folder.getFileName() + " \nfilePath:" + folder.getFilePath() + " \nmimeType:");
            if(fileFolderMap.containsKey(folder.getFileParentPath())){
                List<FileFolder> floders = fileFolderMap.get(folder.getFileParentPath());
                floders.add(folder);
            }else{
                List<FileFolder> folders = new ArrayList<>();
                folders.add(folder);
                fileFolderMap.put(folder.getFileParentPath(),folders);
            }

            fileFolderMap.put(FileFolder.TOTAL_FOLDER,totalFolder);

        }
        mCursor.close();
        mCursor = null;

        return fileFolderMap;

    }

    public static Map<String, List<FileFolder>> findVideoData(Context context) {
        if (mContentResolver == null) {
            mContentResolver = context.getContentResolver();
        }
        String[] projection = new String[]{   //得到数据的那些东西
                MediaStore.Video.VideoColumns.DATA, //路径到磁盘上的文件。
                MediaStore.Video.VideoColumns.DISPLAY_NAME, //文件显示的名称
                MediaStore.Video.VideoColumns.MIME_TYPE //文件类型

        };
        String selection = MediaStore.Video.VideoColumns.MIME_TYPE + "=?"; // 文件过滤器（类似与于sql语句的where）
        String[] selectionArgs = new String[]{    //查找过滤器
                "video/mp4"
        };


        Cursor mCursor = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, //查找源
                projection,     //projection 是定义返回的数据，
                selection,     // selection 通常的sql 语句，
                selectionArgs,  // 例如  selection=MediaStore.Images.ImageColumns.MIME_TYPE+"=? " 那么 selectionArgs=new String[]{"jpg"};
                MediaStore.Video.VideoColumns.DATE_ADDED + " DESC" //查找结果的顺序 这里是按添加时间逆顺序排列 （DESC 逆顺  ASC 顺序）无关大小写
        );


        if (mCursor == null) {
            return null;
        }
        Map<String, List<FileFolder>> fileFolderMap = new HashMap<>();
        List<FileFolder> totalFolder= new ArrayList<>();
        while (mCursor.moveToNext()) {
            FileFolder folder = new FileFolder();
            folder.setFileName(mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME)));
            folder.setFilePath(mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA)));
            folder.setFileType(mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.VideoColumns.MIME_TYPE)));
            folder.setFileParentPath(new File(folder.getFilePath()).getParentFile().getAbsolutePath());

            totalFolder.add(folder);
            Log.d(TAG, "findImageData:   \nfileName:" + folder.getFileName() + " \nfilePath:" + folder.getFilePath() + " \nmimeType:"
            );
            if(fileFolderMap.containsKey(folder.getFileParentPath())){
                List<FileFolder> floders = fileFolderMap.get(folder.getFileParentPath());
                floders.add(folder);
            }
            else{
                List<FileFolder> folders = new ArrayList<>();
                folders.add(folder);
                fileFolderMap.put(folder.getFileParentPath(),folders);
            }

            fileFolderMap.put(FileFolder.TOTAL_FOLDER,totalFolder);

        }
        mCursor.close();
        mCursor = null;

        return fileFolderMap;

    }
}
