package ws.dyt.view.adapter.pinned;

import ws.dyt.view.viewholder.BaseViewHolder;

/**
 * Created by yangxiaowei on 16/8/12.
 */
public interface IConvert {
    /**
     * 设置粘性控件数据
     * @param holder
     * @param position 数据域中的索引
     * @param type 数据所在的组
     */
    void convertPinnedHolder(BaseViewHolder holder, int position, int type);
}
