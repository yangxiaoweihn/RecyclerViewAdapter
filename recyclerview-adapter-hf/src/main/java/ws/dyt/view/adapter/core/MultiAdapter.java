package ws.dyt.view.adapter.core;

import android.content.Context;
import android.support.annotation.LayoutRes;

import java.util.List;

import ws.dyt.view.adapter.core.base.BaseAdapter;
import ws.dyt.view.adapter.swipe.SwipeAdapter;

/**
 * Created by yangxiaowei on 16/6/8.
 *
 * 支持一种item布局和多种item布局
 * 1. 采用{@link #MultiAdapter(Context, List, int)} 构造方法时，最后一个参数表示单一item的布局
 * 2. 采用{@link #MultiAdapter(Context, List)} 构造方法时需要重新 {@link #getItemViewLayout(int)} 设置每一项布局
 */
abstract
public class MultiAdapter<T> extends BaseAdapter<T> implements MultiItemViewType {
    private @LayoutRes int mItemLayoutResId;

    /**
     * 调用该构造方法时需要调用 {@link #getItemViewLayout(int)} 设置item布局
     * @param context
     * @param data
     */
    public MultiAdapter(Context context, List<T> data) {
        super(context, data);
    }

    /**
     * 调用该构造方法时默认数据项都采用 mItemLayoutResId 布局，同样可以调用 {@link #getItemViewLayout(int)} 重新设置item布局
     * @param context
     * @param data
     * @param mItemLayoutResId
     */
    public MultiAdapter(Context context, List<T> data, @LayoutRes int mItemLayoutResId) {
        this(context, data);
        this.mItemLayoutResId = mItemLayoutResId;
    }

    @Override
    final
    public int mapDataSectionItemViewTypeToItemLayoutId(int position) {
        if (isEmpty()) {
            return super.mapDataSectionItemViewTypeToItemLayoutId(position);
        }
        return filterDataSectionItemViewTypeToItemLayoutId(position);
    }

    protected int filterDataSectionItemViewTypeToItemLayoutId(int position) {
        return getItemViewLayout(position);
    }

    @Override
    @LayoutRes
    public int getItemViewLayout(int position) {
        return this.mItemLayoutResId;
    }
}