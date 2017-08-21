package ws.dyt.view.adapter.pinned;

import android.support.annotation.LayoutRes;

/**
 * Created by yangxiaowei on 16/8/8.
 */
public interface IPinnedItemViewType {

    /**
     * 设置粘性控件布局
     * @return
     */
    @LayoutRes
    int getPinnedItemViewLayout();
}
