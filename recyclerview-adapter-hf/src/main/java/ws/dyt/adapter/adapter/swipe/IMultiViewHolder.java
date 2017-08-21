package ws.dyt.adapter.adapter.swipe;

import android.view.View;

import ws.dyt.adapter.viewholder.BaseViewHolder;

/**
 * Created by yangxiaowei on 16/12/15.
 */

public interface IMultiViewHolder {
    /**
     * 重写该方法自定义ViewHolder
     * @param itemLayoutOfViewType  对应{@link SwipeAdapter#getItemViewLayout(int)}返回值
     * @param itemViewOfViewType    一般情况下为{@param itemLayoutOfViewType}对应加载后的view
     * @return
     */
    BaseViewHolder onCreateViewHolderWithMultiItemTypes(int itemLayoutOfViewType, View itemViewOfViewType);
}
