package ws.dyt.recyclerviewadapter.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by yangxiaowei on 16/6/21.
 */
public class UnitUtils {
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

    public static int dip2Px(Context context,int dip){

        DisplayMetrics dm =context.getResources().getDisplayMetrics();

        int densityDpi =  dm.densityDpi;
        float density = dm.density;

        return (int) (density*dip);
    }

    public static float dip2Px(Context context, float dip){

        DisplayMetrics dm =context.getResources().getDisplayMetrics();

        int densityDpi =  dm.densityDpi;
        float density = dm.density;

        return density * dip;
    }

    public static float sp2Px(Context context, float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return sp*scaledDensity;
    }
}
