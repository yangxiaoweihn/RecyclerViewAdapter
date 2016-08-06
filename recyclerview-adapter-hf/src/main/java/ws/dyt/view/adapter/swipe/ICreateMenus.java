package ws.dyt.view.adapter.swipe;

import java.util.List;

/**
 * Created by yangxiaowei on 16/8/5.
 * 菜单创建接口
 */
public interface ICreateMenus {
    /**
     * 创建多个菜单
     * @param viewType  可以针对不同的item类型创建不同的菜单
     * @return
     */
    List<MenuItem> onCreateMultiMenuItem(int viewType);


    /**
     * 创建单个菜单
     * @param viewType  可以针对不同的item类型创建不同的菜单
     * @return
     */
    MenuItem onCreateSingleMenuItem(int viewType);

}
