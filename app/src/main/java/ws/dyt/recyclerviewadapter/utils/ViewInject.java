package ws.dyt.recyclerviewadapter.utils;

import android.view.View;

/**
 * Created by yangxiaowei on 16/12/11.
 */

public class ViewInject {
    public static <T extends View> T find(int id, View anchor) {
        View v = anchor.findViewById(id);
        return null == v ? null : (T) v;
    }
}
