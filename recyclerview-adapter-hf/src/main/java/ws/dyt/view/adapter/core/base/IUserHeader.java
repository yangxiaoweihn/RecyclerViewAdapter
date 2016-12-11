package ws.dyt.view.adapter.core.base;

import android.view.View;

/**
 * Created by yangxiaowei on 16/8/12.
 */
public interface IUserHeader {
    int getHeaderViewCount();

    int addHeaderView(View view);

    void addHeaderView(View view, int position);

    int removeHeaderView(View view);

    boolean isHeaderItemView(int position);

    int findHeaderViewPosition(View view);

    void clearHeaders();

    boolean isEmptyOfHeaders();
}
