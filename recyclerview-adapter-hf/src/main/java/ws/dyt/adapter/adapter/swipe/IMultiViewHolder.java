package ws.dyt.adapter.adapter.swipe;

import android.view.View;
import android.view.ViewGroup;

import ws.dyt.adapter.viewholder.BaseViewHolder;

/**
 * Created by yangxiaowei on 16/12/15.
 */

public interface IMultiViewHolder<VH extends BaseViewHolder> {
    /**
     * 重写该方法自定义ViewHolder
     * @param itemLayoutOfViewType  对应{@link SwipeAdapter#getItemViewLayout(int)}返回值
     * @param itemViewOfViewType    一般情况下为{@param itemLayoutOfViewType}对应加载后的view
     * @return
     */
    @Deprecated
    BaseViewHolder onCreateViewHolderWithMultiItemTypes(int itemLayoutOfViewType, View itemViewOfViewType);

    /**
     * 重写该方法自定义ViewHolder
     * @param itemLayoutOfViewType   对应{@link SwipeAdapter#getItemViewLayout(int)}返回值
     * @param parent
     * @return
     */
    VH onCreateViewHolderByItemType(int itemLayoutOfViewType, ViewGroup parent);
}
