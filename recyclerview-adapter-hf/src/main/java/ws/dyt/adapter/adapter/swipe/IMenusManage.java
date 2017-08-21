package ws.dyt.adapter.adapter.swipe;

/**
 * Created by yangxiaowei on 17/2/13.
 *
 * 菜单状态管理
 */

public interface IMenusManage {
    /**
     * 是否有其他打开菜单的item项(不包含当前客户端触摸的item)
     * @return
     */
    boolean hasOpenedMenuItems();

    /**
     * 关闭所有打开菜单的item
     */
    void closeAllMenuItems();

}
