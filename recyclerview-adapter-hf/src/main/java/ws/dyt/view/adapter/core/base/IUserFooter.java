package ws.dyt.view.adapter.core.base;

import android.view.View;

/**
 * Created by yangxiaowei on 16/8/12.
 */
public interface IUserFooter {
    int getFooterViewCount();

    void addFooterView(View view);

    void addFooterView(View view, int position);

    void removeFooterView(View view);

    void removeFooterView(int position);

    boolean isFooterItemView(int position);
}
