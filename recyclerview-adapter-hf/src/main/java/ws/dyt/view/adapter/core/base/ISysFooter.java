package ws.dyt.view.adapter.core.base;

import android.view.View;

/**
 * Created by yangxiaowei on 16/8/12.
 */
public interface ISysFooter {
    int getSysFooterViewCount();

    int setSysFooterView(View view);

    int removeSysFooterView(View view);

    boolean isSysFooterView(int position);

    int findSysFooterViewPosition(View view);

    void clearSysFooters();

    boolean isEmptyOfSysFooters();
}
