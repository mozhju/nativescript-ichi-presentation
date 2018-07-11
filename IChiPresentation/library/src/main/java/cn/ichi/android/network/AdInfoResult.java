package cn.ichi.android.network;

import java.util.List;

/**
 * Created by mozj on 2018/6/28.
 */

public class AdInfoResult {
    private int mediaType;
    private List<String> images;
    private List<String> videos;

    public int getMediaType() {
        return mediaType;
    }

    public List<String> getImages() {
        return images;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }
}
