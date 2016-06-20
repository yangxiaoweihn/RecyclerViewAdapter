package ws.dyt.library.adapter;

import android.support.annotation.LayoutRes;

public interface MultiItemViewType {
    @LayoutRes
    int getItemViewLayout(int position);
}