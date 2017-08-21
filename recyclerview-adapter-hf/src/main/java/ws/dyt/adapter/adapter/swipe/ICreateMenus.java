package ws.dyt.adapter.adapter.swipe;

import android.support.annotation.LayoutRes;

import java.util.List;

/**
 * Created by yangxiaowei on 16/8/5.
 * 菜单创建接口
 */
public interface ICreateMenus {
    /**
     * 创建多个菜单
     * @param viewType  可以针对不同的item类型创建不同的菜单 {@link ws.dyt.adapter.adapter.core.MultiItemViewType#getItemViewLayout(int)}
     * @return
     */
    List<MenuItem> onCreateMultiMenuItem(@LayoutRes int viewType);


    /**
     * 创建单个菜单
     * @param viewType  可以针对不同的item类型创建不同的菜单 {@link ws.dyt.adapter.adapter.core.MultiItemViewType#getItemViewLayout(int)}
     * @return
     */
    MenuItem onCreateSingleMenuItem( @LayoutRes int viewType);

}
