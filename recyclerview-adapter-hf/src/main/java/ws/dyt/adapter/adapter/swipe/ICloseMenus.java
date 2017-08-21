package ws.dyt.view.adapter.swipe;

/**
 * Created by yangxiaowei on 16/8/6.
 */
public interface ICloseMenus /*extends IMenuStatus*/{
    /**
     * 关闭菜单
     */
    void closeMenuItem();

    /**
     * 关闭其他打开菜单的item
     * @return true:标识当前item menu打开状态
     */
    boolean closeOtherMenuItems();

}
