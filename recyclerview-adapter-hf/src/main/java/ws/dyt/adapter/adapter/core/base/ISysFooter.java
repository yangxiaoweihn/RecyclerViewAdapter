package ws.dyt.adapter.adapter.core.base;

import android.view.View;

/**
 * Created by yangxiaowei on 16/8/12.
 */
public interface ISysFooter {
    int getSysFooterViewCount();

    int setSysFooterView(View view);

    int removeSysFooterView(View view);

    boolean isSysFooterView(int position);

    /**
     * 返回在sys-footer section中view对应的position
     * @param view
     * @return
     */
    int findSysFooterViewPosition(View view);

    void clearSysFooters();

    boolean isEmptyOfSysFooters();
}
