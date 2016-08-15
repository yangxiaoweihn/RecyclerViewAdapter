package ws.dyt.view.adapter.core.base;

import android.view.View;

/**
 * Created by yangxiaowei on 16/8/12.
 */
public interface IUserFooter {
    int getFooterViewCount();

    void addFooterView(View view);

    void addFooterView(View view, boolean changeAllVisibleItems);

    void removeFooterView(View view);

    void removeFooterView(View view, boolean changeAllVisibleItems);
}
