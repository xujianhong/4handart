package com.daomingedu.art.db;

import org.litepal.crud.LitePalSupport;

/**
 * Description
 * Created by jianhongxu on 2021/11/23
 */
public class VideoList extends LitePalSupport {

    private String path;
    private int id;
    private String folder;

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public VideoList(String path,String folder) {
        this.path = path;
        this.folder = folder;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
