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

                if (jsonObject.has("mediaFiles")) {
                    JSONArray mediaFiles = jsonObject.getJSONArray("mediaFiles");
                    List<String> lstMediaFiles = new ArrayList<>();
                    for (int i = 0; i < mediaFiles.length(); i++) {
                        String url = mediaFiles.getString(i);
                        lstMediaFiles.add(url);
                    }
                    adInfoResult.setMediaFiles(lstMediaFiles);
                }

                if (jsonObject.has("menus")) {
                    JSONArray menus = jsonObject.getJSONArray("menus");
                    List<String> lstMenu = new ArrayList<>();
                    for (int i = 0; i < menus.length(); i++) {
                        String url = menus.getString(i);
                        lstMenu.add(url);
                    }
                    adInfoResult.setMenus(lstMenu);
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
