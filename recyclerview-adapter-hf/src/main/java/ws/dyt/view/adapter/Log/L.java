package ws.dyt.view.adapter.Log;

import android.util.Log;

import ws.dyt.view.BuildConfig;

/**
 * Created by yangxiaowei on 16/7/16.
 */
public class L {
    public static boolean DEBUG = true;//BuildConfig.DEBUG;
    private static final String TAG = "Lib-SuperAdapter";

    public static void d(String msg) {
        if (!DEBUG) {
            return;
        }
        Log.d(TAG, msg);
    }

    public static void w(String msg){
        if (!DEBUG) {
            return;
        }
        Log.w(TAG, msg);
    }

    public static void e(String msg){
        if (!DEBUG) {
            return;
        }
        Log.e(TAG, msg);
    }
}
