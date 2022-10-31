package com.daomingedu.art.mvp.ui.widget.selectimagevideo.util;

import android.text.TextUtils;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * 文件bean
 * Created by jianhongxu on 2017/6/18.
 */

public class FileFolder implements Serializable {

    public static final String TOTAL_FOLDER = "TotalFolder";//所有资源
    public static final String TYPE_IMAGE = "image/jpeg";//图片类型
    public static final String TYPE_VIDEO = "video/mp4";//视频类型

    private String filePath; //文件路径
    private String fileName; //文件名称
    private String fileParentPath; //父类文件夹路径

    private String fileParentName;//父类文件夹名称

    private String fileType;//文件类型

    private String fileLength;//文件大小

    private boolean check;//是否选中

    private boolean showCamera;//是否显示相机

    public boolean isShowCamera() {
        return showCamera;
    }

    public void setShowCamera(boolean showCamera) {
        this.showCamera = showCamera;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileParentName() {
        return fileParentName;
    }

    public void setFileParentName(String fileParentName) {
        this.fileParentName = fileParentName;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getFileLength() {
        return fileLength;
    }

    public void setFileLength(String fileLength) {
        this.fileLength = fileLength;
    }

    public String getFileParentPath() {
        return fileParentPath;
    }

    public void setFileParentPath(String fileParentPath) {
        this.fileParentPath = fileParentPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
        if (TextUtils.isEmpty(filePath)) return;
        File file = new File(filePath);
        if (file.exists()) {
            setFileLength(getLength(file));
        }

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    /**
     * 文件大小
     *
     * @param videoFile
     * @return
     */
    private String getLength(File videoFile) {
        float videoLength = videoFile.length() / 1024f / 1024f;

        DecimalFormat df = new DecimalFormat("0.0#");
        return df.format(videoLength) + "M";


    }
}
