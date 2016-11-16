package ws.dyt.view.adapter.swipe;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.view.adapter.core.MultiAdapter;
import ws.dyt.view.adapter.core.base.BaseAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;

/**
 * Created by yangxiaowei on 16/8/2.
 *
 * 滑动菜单适配器
 * 1. 支持左右菜单任意个数添加(目前不能同时支持)
 * 2. 菜单支持点击事件回调
 */
abstract
public class SwipeAdapter<T> extends MultiAdapter<T> implements ICreateMenus, IMenuSupport{

    public SwipeAdapter(Context context, List<T> datas) {
        super(context, datas);
    }

    public SwipeAdapter(Context context, List<T> datas, @LayoutRes int itemLayoutResId) {
        super(context, datas, itemLayoutResId);
    }

    @Override
    public BaseViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(viewType, parent, false);

        MenuItem mi = this.onCreateSingleMenuItem(viewType);
        List<MenuItem> mm = this.onCreateMultiMenuItem(viewType);
        //客户端没有设置菜单支持
        if (null == mi && (null == mm || mm.isEmpty())) {
            return new BaseViewHolder(itemView);
        }

        List<MenuItem> menuItems = new ArrayList<>();
        if (null != mi) {
            menuItems.add(mi);
        }

        if (null != mm && !mm.isEmpty()) {
            menuItems.addAll(mm);
        }

        final SwipeLayout swipeLayout = new SwipeLayout(context);
        swipeLayout.setUpView(parent, itemView, menuItems);
        swipeLayout.setIsCloseOtherItemsWhenThisWillOpen(this.isCloseOtherItemsWhenThisWillOpen());

        itemView.setClickable(true);

        BaseViewHolder holder = new BaseViewHolder(swipeLayout, itemView);
        this.initMenusListener(holder);
        return holder;
    }

    @Override
    public List<MenuItem> onCreateMultiMenuItem(int viewType) {
        return null;
    }

    @Override
    public MenuItem onCreateSingleMenuItem(int viewType) {
        return null;
    }

    @Override
    public boolean isCloseOtherItemsWhenThisWillOpen() {
        return false;
    }

    /**
     * 添加菜单点击监听器
     * @param holder
     */
    private void initMenusListener(final BaseViewHolder holder) {
        if (! (holder.itemView instanceof SwipeLayout)) {
            return;
        }
        final SwipeLayout swipeLayout = (SwipeLayout) holder.itemView;

        List<Pair<View, MenuItem>> menus = swipeLayout.getMenus();
        if (null == menus || menus.isEmpty()) {
            return;
        }

        if (null == this.onItemMenuClickListener) {
            return;
        }
        for (final Pair<View, MenuItem> pair:menus) {
            pair.first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int hAll = getHeaderViewCount() + getSysHeaderViewCount();
                    final int position = holder.getAdapterPosition() - hAll;
                    onItemMenuClickListener.onMenuClick(swipeLayout, holder.eventItemView, v, position, pair.second.getMenuId());
                }
            });
        }
    }


    @Override
    final
    protected void onItemClick(BaseViewHolder holder, View view) {
        if (!isMenuOpend(holder)) {
            super.onItemClick(holder, view);
        }
    }

    @Override
    final
    protected boolean onItemLongClick(BaseViewHolder holder, View view) {
        if (!isMenuOpend(holder)) {
            return super.onItemLongClick(holder, view);
        }
        return false;
    }

    @Override
    final
    public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    /**
     * 菜单状态
     * @param holder
     * @return
     */
    private boolean isMenuOpend(BaseViewHolder holder) {
        boolean isOpend = false;
        //处理当前菜单的关闭
        if (holder.itemView instanceof SwipeLayout) {
            SwipeLayout swipeLayout = (SwipeLayout) holder.itemView;
            if (SwipeDragHelperDelegate.MenuStatus.OPEN == swipeLayout.getMenuStatus()) {
                swipeLayout.closeMenuItem();
                isOpend = true;
            }
        }
        return isOpend;
    }

    private OnItemMenuClickListener onItemMenuClickListener = null;

    public void setOnItemMenuClickListener(OnItemMenuClickListener onItemMenuClickListener) {
        this.onItemMenuClickListener = onItemMenuClickListener;
    }
}
