package ws.dyt.adapter.adapter.swipe;

import android.view.View;

/**
 * Created by yangxiaowei on 16/8/5.
 *
 * 菜单监听器
 */
public interface OnItemMenuClickListener {

    /**
     * 菜单点击回调
     * @param swipeItemView
     * @param itemView  客户端所创建的itemview
     * @param menuView
     * @param position  列表中item所在索引(数据区域)
     * @param menuId    客户端创建item时指定的id
     */
    void onMenuClick(SwipeLayout swipeItemView, View itemView, View menuView, int position, int menuId);
}
