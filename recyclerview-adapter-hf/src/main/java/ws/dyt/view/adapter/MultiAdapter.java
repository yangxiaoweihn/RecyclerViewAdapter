package ws.dyt.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.view.R;
import ws.dyt.view.adapter.base.BaseAdapter;
import ws.dyt.view.adapter.swipe.MenuItem;
import ws.dyt.view.adapter.swipe.SwipeAdapter;
import ws.dyt.view.adapter.swipe.SwipeDragHelperDelegate;
import ws.dyt.view.adapter.swipe.SwipeLayout;
import ws.dyt.view.viewholder.BaseViewHolder;
import ws.dyt.view.viewholder.SwipeViewHolder;

/**
 * Created by yangxiaowei on 16/6/8.
 *
 * 支持一种item布局和多种item布局
 * 1. 采用{@link #MultiAdapter(Context, List, int)} 构造方法时，最后一个参数表示单一item的布局
 * 2. 采用{@link #MultiAdapter(Context, List)} 构造方法时需要重新 {@link #getItemViewLayout(int)} 设置每一项布局
 */
abstract
public class MultiAdapter<T> extends SwipeAdapter<T> implements MultiItemViewType {
    private @LayoutRes int itemLayoutResId;

    /**
     * 调用该构造方法时需要调用 {@link #getItemViewLayout(int)} 设置item布局
     * @param context
     * @param datas
     */
    public MultiAdapter(Context context, List<T> datas) {
        super(context, datas);
    }

    /**
     * 调用该构造方法时默认数据项都采用 itemLayoutResId 布局，同样可以调用 {@link #getItemViewLayout(int)} 重新设置item布局
     * @param context
     * @param datas
     * @param itemLayoutResId
     */
    public MultiAdapter(Context context, List<T> datas, @LayoutRes int itemLayoutResId) {
        this(context, datas);
        this.itemLayoutResId = itemLayoutResId;
    }

    @Override
    final
    public int convertDataSectionItemViewType(int position) {
        if (isEmpty()) {
            return super.convertDataSectionItemViewType(position);
        }
        return getItemViewLayout(position);
    }

//    @Override
//    final
//    public BaseViewHolder onCreateHolder(ViewGroup parent, int viewType) {
//        ViewGroup vg = (ViewGroup) inflater.inflate(viewType, parent, false);
//
//        MenuItem mi = this.onCreateSingleMenuItem(viewType);
//        List<MenuItem> mm = this.onCreateMultiMenuItem(viewType);
//        if (null == mi && (null == mm || mm.isEmpty())) {
//            return new BaseViewHolder(inflater.inflate(viewType, parent, false));
//        }
//
//        List<MenuItem> menuItems = new ArrayList<>();
//        if (null != mi) {
//            menuItems.add(mi);
//        }
//
//        if (null != mm && !mm.isEmpty()) {
//            menuItems.addAll(mm);
//        }
//
//        final SwipeLayout swipeLayout = new SwipeLayout(context);
//        swipeLayout.setUpView(parent, vg, menuItems);
//
//        vg.setClickable(true);
//        Log.e("KKKK", getClass().getSimpleName()+" -> "+vg.hashCode());
//
//        return new BaseViewHolder(swipeLayout, vg);
//    }


    @Override
    public int getItemViewLayout(int position) {
        return this.itemLayoutResId;
    }
}