package cn.ichi.android.network;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HttpUtils {
    public static String getResponse(String urlParam) {
        try {
            URL url = new URL(urlParam);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE6.0; Windows NT 5.1; SV1)");
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                return inputStream2String(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    private static String inputStream2String(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] array = new byte[1024];
            int len;
            while ((len = is.read(array, 0, array.length)) != -1) {
                baos.write(array, 0, len);
            }
            return baos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static AdInfoResult parseResult(String result) {
        if (!TextUtils.isEmpty(result)) {
            try {
                AdInfoResult adInfoResult = new AdInfoResult();
                JSONObject jsonObject = new JSONObject(result);

                int mediaType = jsonObject.getInt("mediaType");
                adInfoResult.setMediaType(mediaType);

                if (jsonObject.has("images")) {
                    JSONArray images = jsonObject.getJSONArray("images");
                    List<String> lstImage = new ArrayList<>();
                    for (int i = 0; i < images.length(); i++) {
                        String url = images.getString(i);
                        lstImage.add(url);
                    }
                    adInfoResult.setImages(lstImage);
                }

                if (jsonObject.has("videos")) {
                    JSONArray videos = jsonObject.getJSONArray("videos");
                    List<String> lstVideo = new ArrayList<>();
                    for (int i = 0; i < videos.length(); i++) {
                        String url = videos.getString(i);
                        lstVideo.add(url);
                    }
                    adInfoResult.setVideos(lstVideo);
                }

                return adInfoResult;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

}
