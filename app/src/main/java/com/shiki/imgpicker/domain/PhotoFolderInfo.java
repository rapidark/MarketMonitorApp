package com.shiki.imgpicker.domain;

import java.util.List;

/**
 * Created by Eric on 2016/2/25.
 */
public class PhotoFolderInfo {
    private int folderId;
    private String folderName;
    private PhotoInfo coverPhoto;
    private List<PhotoInfo> photoList;

    public List<PhotoInfo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<PhotoInfo> photoList) {
        this.photoList = photoList;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public PhotoInfo getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(PhotoInfo coverPhoto) {
        this.coverPhoto = coverPhoto;
    }
}
