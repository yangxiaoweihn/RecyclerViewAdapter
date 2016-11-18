package ws.dyt.view.adapter.core.base;

import android.view.View;

/**
 * Created by yangxiaowei on 16/8/12.
 */
public interface ISysHeader {

    int getSysHeaderViewCount();

    void addSysHeaderView(View view);

    void addSysHeaderView(View view, int position);

    void setSysHeaderView(View view);

    void removeSysHeaderView(View view);

    boolean isSysHeaderItemView(int position);
}
