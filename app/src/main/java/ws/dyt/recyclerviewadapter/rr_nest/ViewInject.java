package ws.dyt.recyclerviewadapter.rr_nest;

import android.view.View;

/**
 * Created by yangxiaowei on 16/7/1.
 */
public class ViewInject {

    public static  <V extends View> V findView(int id, View parent) {
        if (null == parent) {
            return null;
        }
        View v = parent.findViewById(id);
        return null == v ? null : (V) v;
    }
}
