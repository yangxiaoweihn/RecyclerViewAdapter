package ws.dyt.view.adapter.core.base;

import android.view.View;

/**
 * Created by yangxiaowei on 16/8/12.
 */
public interface IUserHeader {
    int getHeaderViewCount();

    void addHeaderView(View view);

    void addHeaderView(View view, boolean changeAllVisibleItems);

    void removeHeaderView(View view);

    void removeHeaderView(View view, boolean changeAllVisibleItems);
}
