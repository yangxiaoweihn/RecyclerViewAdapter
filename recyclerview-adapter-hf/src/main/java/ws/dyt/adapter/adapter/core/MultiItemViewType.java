package ws.dyt.adapter.adapter.core;

import android.support.annotation.LayoutRes;

/**
 * 多item类型支持
 */
public interface MultiItemViewType {
    /**
     * 为指定item设置布局
     * @param position
     * @return
     */
    @LayoutRes
    int getItemViewLayout(int position);
}