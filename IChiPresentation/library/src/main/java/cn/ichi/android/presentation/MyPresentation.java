package cn.ichi.android.presentation;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.display.DisplayManager;
import android.media.MediaRouter;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;

import com.lijunhuayc.downloader.downloader.DownloadProgressListener;
import com.lijunhuayc.downloader.downloader.DownloaderConfig;
import com.lijunhuayc.downloader.downloader.FileDownloader;
import com.lijunhuayc.downloader.downloader.HistoryCallback;
import com.lijunhuayc.downloader.downloader.WolfDownloader;
import com.lijunhuayc.downloader.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.ichi.android.network.AdInfoResult;
import cn.ichi.android.network.HttpUtils;

/**
 * Created by mozj on 2018/5/9.
 */

public class MyPresentation {

    private static String TAG = "MyPresentation";

    private DifferentDisplay presentation = null;

    public MyPresentation() {
    }


    public boolean generateBlack() {
        if (generate()) {
            setShowType(0);
            return true;
        }
        return false;
    }


    public boolean generate() {

        if (presentation != null) {
            return true;
        }

        Activity activity = Utils.getActivity();
        if (activity != null) {
//            MediaRouter mMediaRouter = (MediaRouter) activity.getSystemService(Context.MEDIA_ROUTER_SERVICE);
//            MediaRouter.RouteInfo route = mMediaRouter.getSelectedRoute(
//                    MediaRouter.ROUTE_TYPE_LIVE_VIDEO);
//            Display presentationDisplay = route != null ? route.getPresentationDisplay() : null;
//
//            if (presentationDisplay != null) {
//                presentation = new DifferentDisplay(activity, presentationDisplay);
//                return true;
//            }

            DisplayManager  mDisplayManager;//屏幕管理类
            Display[]  displays;//屏幕数组
            mDisplayManager = (DisplayManager)activity.getSystemService(Context.DISPLAY_SERVICE);
            displays = mDisplayManager.getDisplays();
            //if (displays.length > 1)
            {
                presentation = new DifferentDisplay(activity, displays[0]);
                return true;
            }
        }
        return false;
    }


    private JSONArray getJSONArray(JSONObject jsonObject, String key) {
        try {
            JSONArray value = jsonObject.getJSONArray(key);
            return  value;
        } catch (Exception e) {
            Log.e(TAG, "getJSONArray (key=" + key + "), Exception: " + e.getMessage());
        }
        return new JSONArray();
    }


    @Nullable
    private String getString(JSONObject jsonObject, String key) {
        try {
            String value = jsonObject.getString(key);
            return  value;
        } catch (Exception e) {
            Log.e(TAG, "getString (key=" + key + "), Exception: " + e.getMessage());
        }
        return null;
    }


    @Nullable
    private Integer getInt(JSONObject jsonObject, String key) {
        try {
            Integer value = jsonObject.getInt(key);
            return  value;
        } catch (Exception e) {
            Log.e(TAG, "getInt (key=" + key + "), Exception: " + e.getMessage());
        }
        return null;
    }


    @Nullable
    private Double getDouble(JSONObject jsonObject, String key) {
        try {
            Double value = jsonObject.getDouble(key);
            return  value;
        } catch (Exception e) {
            Log.e(TAG, "getDouble (key=" + key + "), Exception: " + e.getMessage());
        }
        return null;
    }


    private void download(Context mContext, String downloadUrl, File saveDir, String fileName, int threadNum) {
        File apkFile = new File(saveDir, fileName);
        if (apkFile.exists()) {
            presentation.addDownLoadFile(apkFile.getAbsolutePath());
            return;
        }

        DownloaderConfig config = new DownloaderConfig()
                .setDownloadUrl(downloadUrl)
                .setThreadNum(threadNum)
                .setSaveDir(saveDir)//saveDir = /storage/emulated/0/Android/data/com.echi.future/files
                .setFileName(fileName)
                .setDownloadListener(new DownloadProgressListener() {
                    @Override
                    public void onDownloadTotalSize(int totalSize) {
                    }

                    @Override
                    public void updateDownloadProgress(int size, float percent, float speed) {
                    }

                    @Override
                    public void onDownloadSuccess(String apkPath) {
                        presentation.addDownLoadFile(apkPath);
                    }

                    @Override
                    public void onDownloadFailed() {

                    }

                    @Override
                    public void onPauseDownload() {
                    }

                    @Override
                    public void onStopDownload() {
                    }
                });

        final WolfDownloader wolfDownloader = config.buildWolf(mContext);

        wolfDownloader.readHistory(new HistoryCallback() {
            @Override
            public void onReadHistory(int downloadLength, int fileSize) {
            }
        });

        wolfDownloader.startDownload();
    }


    public void downloadAndShow(final String url) {
        new Thread() {
            @Override
            public void run() {
                Application application = Utils.getApplication();
                File saveDir = application.getExternalFilesDir("");

                AdInfoResult infoResult = HttpUtils.parseResult(HttpUtils.getResponse(url));

                int mediaType = infoResult.getMediaType();
                if (presentation != null) {
                    List<String> files = null;
                    int threadNum = 1;
                    switch (mediaType)
                    {
                        case 0:
                            if (infoResult.getImages() != null) {
                                files = infoResult.getImages();
                                threadNum = 1;
                            }

                            break;

                        case 1:
                            if (infoResult.getVideos() != null) {
                                files = infoResult.getVideos();
                                threadNum = 3;
                            }

                            break;

                        default:
                            break;
                    }

                    if (files != null) {
                        presentation.clearFiles();
                        for(String fileUrl : files) {
                            String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
                            download(application, fileUrl, saveDir, fileName, threadNum);
                        }
                    }
                }
            }
        }.start();
    }


    public void setShowType(int showType){
        if (presentation != null) {
            presentation.setShowType(showType);
        }
    }


    public void setOrder(String orderJson) {
        if (presentation != null) {
            try {
                Log.d(TAG, "setOrder orderJson: " + orderJson);
                JSONObject jsonObject = new JSONObject(orderJson);
                Order order = new Order();

                JSONArray items = getJSONArray(jsonObject, "items");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    OrderItem orderItem = new OrderItem();

                    orderItem.name = getString(item, "name");
                    orderItem.qty = getInt(item, "qty");
                    orderItem.fee = getDouble(item, "fee");
                    orderItem.price = getDouble(item, "price");

                    order.items.add(orderItem);
                }

                JSONArray coupons = getJSONArray(jsonObject, "coupons");
                for (int i = 0; i < coupons.length(); i++) {
                    JSONObject item = coupons.getJSONObject(i);
                    OrderItem couponItem = new OrderItem();
                    couponItem.name = getString(item, "name");
                    couponItem.qty = getInt(item, "qty");
                    couponItem.fee = getDouble(item, "fee");
                    couponItem.price = getDouble(item, "price");

                    order.coupons.add(couponItem);
                }

                order.finalFee = getDouble(jsonObject, "finalFee");

                presentation.setOrder(order);
            } catch (Exception e) {
                Log.e(TAG, "setOrder  Exception: " + e.getMessage(), e);
            }
        }
    }


    public void setImage(String path) {
        if (presentation != null) {
            File file = new File(path);
            if (file.isDirectory()) {
                presentation.setImageDir(path);
            } else if (file.isFile()){
                presentation.setImageFile(path);
            }
        }
    }


    public void setVideo(String path) {
        if (presentation != null) {
            File file = new File(path);
            if (file.isDirectory()) {
                presentation.setVideoDir(path);
            } else if (file.isFile()){
                presentation.setVideoFile(path);
            }
        }
    }


    public void showPresentation() {
        if (presentation != null) {
            presentation.show();
        }
    }


    public void closePresentation() {
        if (presentation != null) {
            presentation.cancel();
        }
    }
}
