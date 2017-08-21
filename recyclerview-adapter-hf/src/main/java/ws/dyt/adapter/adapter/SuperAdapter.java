package ws.dyt.adapter.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import java.util.List;

import ws.dyt.adapter.adapter.swipe.SwipeAdapter;
import ws.dyt.adapter.viewholder.BaseViewHolder;

/**
 * Created by yangxiaowei on 16/8/9.
 *
 * 客户端接口
 * 支持:
 * 1. 支持添加任意数量header footer
 * 2. 多item支持
 * 3. 支持item事件回调
 * 4. 菜单相关操作
 */
abstract
public class SuperAdapter<T> extends SwipeAdapter<T> {

    public SuperAdapter(Context context, List<T> data) {
        super(context, data);
    }

    public SuperAdapter(Context context, List<T> data, @LayoutRes int itemLayoutResId) {
        super(context, data, itemLayoutResId);
    }

    @Override
    final
    public BaseViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return super.onCreateHolder(parent, viewType);
    }

    @Override
    final
    protected void onBindHolder(BaseViewHolder holder, int position) {
        super.onBindHolder(holder, position);
    }

    @Override
    final
    public int getDataSectionItemCount() {
        return super.getDataSectionItemCount();
    }

}