package ws.dyt.dialog.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by yangxiaowei on 16/5/31.
 */
public class UIUtils {
    /**
     * 获取屏幕的宽高
     *
     * @param context
     *            0: width 1: height
     * @return
     */
    public static int[] getScreenSize(Context context) {

        DisplayMetrics dm =context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        int[] widthAndHeight = {w_screen,h_screen };

        return widthAndHeight;
    }
}
