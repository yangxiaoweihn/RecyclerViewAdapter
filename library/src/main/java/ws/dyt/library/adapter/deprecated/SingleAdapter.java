package ws.dyt.library.adapter.deprecated;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import java.util.List;

import ws.dyt.library.adapter.base.BaseAdapter;
import ws.dyt.library.viewholder.BaseViewHolder;

/**
 * Created by yangxiaowei on 16/6/9.
 * 单类型适配器
 */
abstract
public class SingleAdapter<T> extends BaseAdapter<T> {
    private @LayoutRes int itemLayoutResId;

    /**
     * @param context
     * @param datas
     * @param itemLayoutResId
     */
    public SingleAdapter(Context context, List<T> datas, @LayoutRes int itemLayoutResId) {
        super(context, datas);
        this.itemLayoutResId = itemLayoutResId;
    }

    @Override
    final
    public BaseViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return itemLayoutResId > 0 ? new BaseViewHolder(inflater.inflate(itemLayoutResId, parent, false)) : null;
    }

}
