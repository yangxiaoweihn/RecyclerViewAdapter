package ws.dyt.view.adapter;

import android.support.annotation.LayoutRes;

public interface MultiItemViewType {
    @LayoutRes
    int getItemViewLayout(int position);
}