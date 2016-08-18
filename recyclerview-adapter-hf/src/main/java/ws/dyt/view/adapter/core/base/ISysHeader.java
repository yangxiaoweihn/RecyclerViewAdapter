package ws.dyt.view.adapter.core.base;

import android.view.View;

/**
 * Created by yangxiaowei on 16/8/12.
 */
public interface ISysHeader {

    int getSysHeaderViewCount();

    void addSysHeaderView(View view);

    @Deprecated
    void addSysHeaderView(View view, boolean changeAllVisibleItems);

    void setSysHeaderView(View view);

    @Deprecated
    void setSysHeaderView(View view, boolean changeAllVisibleItems);

    void removeSysHeaderView(View view);

    @Deprecated
    void removeSysHeaderView(View view, boolean changeAllVisibleItems);
}
