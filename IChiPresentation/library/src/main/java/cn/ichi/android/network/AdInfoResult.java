package cn.ichi.android.network;

import java.util.List;

/**
 * Created by mozj on 2018/6/28.
 */

public class AdInfoResult {
//    private int mediaType;
    private List<String> mediaFiles;
    private List<String> menus;

//    public int getMediaType() {
//        return mediaType;
//    }

    public List<String> getMediaFiles() {
        return mediaFiles;
    }

    public List<String> getMenus() {
        return menus;
    }

//    public void setMediaType(int mediaType) {
//        this.mediaType = mediaType;
//    }

    public void setMediaFiles(List<String> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    public void setMenus(List<String> menus) {
        this.menus = menus;
    }
}
