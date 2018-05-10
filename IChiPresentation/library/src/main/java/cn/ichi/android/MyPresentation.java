package cn.ichi.android;

import android.app.Activity;
import android.app.Presentation;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.media.MediaRouter;
import android.view.Display;

/**
 * Created by mozj on 2018/5/9.
 */

public class MyPresentation {

    DifferentDisplay presentation = null;

    public MyPresentation() {
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
            if (displays.length > 1) {
                presentation = new DifferentDisplay(activity, displays[1]);
                return true;
            }
        }
        return false;
    }


    public void setOrder(Order order) {
        if (presentation != null) {
            presentation.setOrder(order);
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
