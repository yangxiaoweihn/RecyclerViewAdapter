package ws.dyt.view.adapter.core;

import android.support.annotation.LayoutRes;

public interface MultiItemViewType {
    @LayoutRes
    int getItemViewLayout(int position);
}