package ws.dyt.view.adapter.swipe;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.view.adapter.core.MultiAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;

/**
 * Created by yangxiaowei on 16/8/2.
 *
 * 滑动菜单适配器
 * 1. 支持左右菜单任意个数添加(目前不能同时支持)
 * 2. 菜单支持点击事件回调
 */
abstract
public class SwipeAdapter<T> extends MultiAdapter<T> implements ICreateMenus, IMenuSupport, IMultiViewHolder, IMenusManage {

    public SwipeAdapter(Context context, List<T> data) {
        super(context, data);
    }

    public SwipeAdapter(Context context, List<T> data, @LayoutRes int itemLayoutResId) {
        super(context, data, itemLayoutResId);
    }

    @Override
    public BaseViewHolder onCreateViewHolderWithMultiItemTypes(int itemLayoutOfViewType, View itemViewOfViewType) {
        return new BaseViewHolder(itemViewOfViewType);
    }

    @Override
    public BaseViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(viewType, parent, false);

        List<MenuItem> menuItems = this.collectMenus(viewType);
        //客户端没有设置菜单支持
        if (null == menuItems || menuItems.isEmpty()) {
            return generateViewHolder(viewType, itemView);
        }

        final SwipeLayout swipeLayout = new SwipeLayout(context);
        swipeLayout.setUpView(parent, itemView, menuItems);
        swipeLayout.setIsCloseOtherItemsWhenThisWillOpen(this.isCloseOtherItemsWhenThisWillOpen());

        itemView.setClickable(true);

//        final BaseViewHolder holder = new BaseViewHolder(swipeLayout, itemView);
        BaseViewHolder holder = generateViewHolder(viewType, swipeLayout);
        holder.eventItemView = itemView;
        this.initMenusListener(holder);
        return holder;
    }

    private BaseViewHolder generateViewHolder(int itemLayoutOfViewType, View itemViewOfViewType) {
        BaseViewHolder viewHolder = onCreateViewHolderWithMultiItemTypes(itemLayoutOfViewType, itemViewOfViewType);
        if (null == viewHolder) {
            viewHolder = new BaseViewHolder(itemViewOfViewType);
        }
        return viewHolder;
    }

    private List<MenuItem> collectMenus(int viewType) {
        MenuItem mi = this.onCreateSingleMenuItem(viewType);
        List<MenuItem> mm = this.onCreateMultiMenuItem(viewType);

        List<MenuItem> menuItems = null;
        //客户端没有设置菜单支持
        if (null == mi && (null == mm || mm.isEmpty())) {}else {
            menuItems = new ArrayList<>();
            if (null != mi) {
                menuItems.add(mi);
            }

            if (null != mm && !mm.isEmpty()) {
                menuItems.addAll(mm);
            }
        }

        return menuItems;
    }

    @Override
    final
    protected int filterDataSectionItemViewTypeToItemLayoutId(int position) {
        final @LayoutRes int layoutIdMap2ViewType = super.filterDataSectionItemViewTypeToItemLayoutId(position);
        return layoutIdMap2ViewType;
    }


    @Override
    public int getItemViewLayout(int position) {
        return super.getItemViewLayout(position);
    }

    @Override
    public List<MenuItem> onCreateMultiMenuItem(@LayoutRes int viewType) {
        return null;
    }

    @Override
    public MenuItem onCreateSingleMenuItem(@LayoutRes int viewType) {
        return null;
    }

    @Override
    public boolean isCloseOtherItemsWhenThisWillOpen() {
        return true;
    }

    /**
     * 添加菜单点击监听器
     * @param holder
     */
    private void initMenusListener(final BaseViewHolder holder) {
        if (null == holder) {
            return;
        }
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
        if (!isMenuOpened(holder)) {
            super.onItemClick(holder, view);
        }
    }

    @Override
    final
    protected boolean onItemLongClick(BaseViewHolder holder, View view) {
        if (!isMenuOpened(holder)) {
            return super.onItemLongClick(holder, view);
        }
        return false;
    }

    /**
     * 菜单状态
     * @param holder
     * @return
     */
    private boolean isMenuOpened(BaseViewHolder holder) {
        boolean isOpened = false;
        //处理当前菜单的关闭
        if (holder.itemView instanceof SwipeLayout) {
            SwipeLayout swipeLayout = (SwipeLayout) holder.itemView;
            if (swipeLayout.isOpenedMenu()/*SwipeDragHelperDelegate.MenuStatus.OPEN == swipeLayout.getMenuStatus()*/) {
                swipeLayout.closeMenuItem();
                isOpened = true;
            }
        }
        return isOpened;
    }

    @Override
    final
    public boolean hasOpenedMenuItems() {
        return SwipeDragHelperDelegate.hasOpenedMenuItems();
    }

    @Override
    final
    public void closeAllMenuItems() {
        SwipeDragHelperDelegate.closeAllMenuItems();
    }

    private OnItemMenuClickListener onItemMenuClickListener = null;

    public void setOnItemMenuClickListener(OnItemMenuClickListener onItemMenuClickListener) {
        this.onItemMenuClickListener = onItemMenuClickListener;
    }

    @Override
    public void release() {
        super.release();

        SwipeDragHelperDelegate.release();
    }
}
