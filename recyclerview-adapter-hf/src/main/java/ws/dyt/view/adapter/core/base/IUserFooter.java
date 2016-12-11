package ws.dyt.view.adapter.core.base;

import android.view.View;

/**
 * Created by yangxiaowei on 16/8/12.
 */
public interface IUserFooter {
    int getFooterViewCount();

    int addFooterView(View view);

    void addFooterView(View view, int position);

    int removeFooterView(View view);

    void removeFooterView(int position);

    boolean isFooterItemView(int position);

    int findFooterViewPosition(View view);

    void clearFooters();

    boolean isEmptyOfFooters();
}
