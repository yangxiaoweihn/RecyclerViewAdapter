package ws.dyt.view.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import java.util.List;

import ws.dyt.view.adapter.core.MultiAdapter;
import ws.dyt.view.adapter.swipe.SwipeAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;

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

    public SuperAdapter(Context context, List<T> datas) {
        super(context, datas);
    }

    public SuperAdapter(Context context, List<T> datas, @LayoutRes int itemLayoutResId) {
        super(context, datas, itemLayoutResId);
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