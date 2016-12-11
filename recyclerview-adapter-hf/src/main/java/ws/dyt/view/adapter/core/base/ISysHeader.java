package ws.dyt.view.adapter.core.base;

import android.view.View;

/**
 * Created by yangxiaowei on 16/8/12.
 */
public interface ISysHeader {

    int getSysHeaderViewCount();

    int addSysHeaderView(View view);

    void addSysHeaderView(View view, int position);

    int setSysHeaderView(View view);

    int removeSysHeaderView(View view);

    boolean isSysHeaderItemView(int position);

    int findSysHeaderViewPosition(View view);

    void clearSysHeaders();

    boolean isEmptyOfSysHeaders();
}
