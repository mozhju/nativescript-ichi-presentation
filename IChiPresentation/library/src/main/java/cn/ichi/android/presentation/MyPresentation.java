package cn.ichi.android.presentation;

import android.app.Activity;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.media.MediaRouter;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by mozj on 2018/5/9.
 */

public class MyPresentation {

    private static String TAG = "MyPresentation";

    DifferentDisplay presentation = null;
    BlackDisplay blackDisplay = null;

    int display = 0;


    public MyPresentation() {
    }


    public boolean generateBlack() {
        if (presentation != null) {
            presentation.cancel();
        }

        display = 1;

        if (blackDisplay != null) {
            return true;
        }

        Activity activity = Utils.getActivity();
        if (activity != null) {
            MediaRouter mMediaRouter = (MediaRouter) activity.getSystemService(Context.MEDIA_ROUTER_SERVICE);
            MediaRouter.RouteInfo route = mMediaRouter.getSelectedRoute(
                    MediaRouter.ROUTE_TYPE_LIVE_VIDEO);
            Display presentationDisplay = route != null ? route.getPresentationDisplay() : null;

            if (presentationDisplay != null) {
                blackDisplay = new BlackDisplay(activity, presentationDisplay);
                return true;
            }

//            DisplayManager  mDisplayManager;//屏幕管理类
//            Display[]  displays;//屏幕数组
//            mDisplayManager = (DisplayManager)activity.getSystemService(Context.DISPLAY_SERVICE);
//            displays = mDisplayManager.getDisplays();
//            if (displays.length > 1) {
//                presentation = new DifferentDisplay(activity, displays[1]);
//                return true;
//            }
        }
        return false;
    }


    public boolean generate() {
        if (blackDisplay != null) {
            blackDisplay.cancel();
        }

        display = 0;

        if (presentation != null) {
            return true;
        }

        Activity activity = Utils.getActivity();
        if (activity != null) {
            MediaRouter mMediaRouter = (MediaRouter) activity.getSystemService(Context.MEDIA_ROUTER_SERVICE);
            MediaRouter.RouteInfo route = mMediaRouter.getSelectedRoute(
                    MediaRouter.ROUTE_TYPE_LIVE_VIDEO);
            Display presentationDisplay = route != null ? route.getPresentationDisplay() : null;

            if (presentationDisplay != null) {
                presentation = new DifferentDisplay(activity, presentationDisplay);
                return true;
            }

//            DisplayManager  mDisplayManager;//屏幕管理类
//            Display[]  displays;//屏幕数组
//            mDisplayManager = (DisplayManager)activity.getSystemService(Context.DISPLAY_SERVICE);
//            displays = mDisplayManager.getDisplays();
//            if (displays.length > 1) {
//                presentation = new DifferentDisplay(activity, displays[1]);
//                return true;
//            }
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
            presentation.setImage(path);
        }
    }


    public void setVideo(String path) {
        if (presentation != null) {
            presentation.setVideo(path);
        }
    }


    public void showPresentation() {
        if (display == 0 && presentation != null) {
            presentation.show();
        } else if (display == 1 && blackDisplay != null) {
            blackDisplay.show();
        }
    }

    public void closePresentation() {
        if (display == 0 && presentation != null) {
            presentation.cancel();
        } else if (display == 1 && blackDisplay != null) {
            blackDisplay.cancel();
        }
    }
}
