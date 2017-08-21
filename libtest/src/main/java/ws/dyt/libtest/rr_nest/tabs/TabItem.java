package ws.dyt.recyclerviewadapter.rr_nest.tabs;

import android.support.annotation.DrawableRes;

/**
 * Created by yangxiaowei on 17/1/5.
 */

public class TabItem {
    public String Title;
    public int Id;
    public @DrawableRes
    int IconResId;

    public TabItem(String title) {
        Title = title;
    }

    public TabItem(int id, String title) {
        Id = id;
        Title = title;
    }

    public TabItem(int id, String title, int iconResId) {
        Id = id;
        Title = title;
        IconResId = iconResId;
    }
}
