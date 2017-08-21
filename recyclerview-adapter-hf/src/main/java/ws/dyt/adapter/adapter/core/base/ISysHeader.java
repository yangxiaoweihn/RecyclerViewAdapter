package ws.dyt.adapter.adapter.core.base;

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

    /**
     * 返回在sys-header section中view对应的position
     * @param view
     * @return
     */
    int findSysHeaderViewPosition(View view);

    void clearSysHeaders();

    boolean isEmptyOfSysHeaders();
}
