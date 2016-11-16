package ws.dyt.view.adapter.pinned;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.List;

import ws.dyt.view.R;
import ws.dyt.view.adapter.ItemWrapper;
import ws.dyt.view.adapter.swipe.SwipeAdapter;
import ws.dyt.view.adapter.swipe.SwipeLayout;
import ws.dyt.view.viewholder.BaseViewHolder;

/**
 * Created by yangxiaowei on 16/8/8.
 *
 * 粘性item适配器,继承自{@link SwipeAdapter},所以支持{@link SwipeAdapter}所支持的所有功能,
 * 值得注意的一点是该适配器对数据有要求(因为粘性是根据数据的type决定的),所以数据都需要继承自{@link ItemWrapper}(其实是一个数据分组的概念)
 */
abstract
public class PinnedAdapter<T extends ItemWrapper> extends SwipeAdapter<T> implements IPinnedItemViewType, IConvert {
    public PinnedAdapter(Context context, List<T> datas) {
        super(context, datas);
    }

    public PinnedAdapter(Context context, List<T> datas, @LayoutRes int itemLayoutResId) {
        super(context, datas, itemLayoutResId);
    }

    private interface PinnedItemRange {
        int FIRST    = 1;
        int OTHER    = 2;
    }


    @Override
    final
    public BaseViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateHolder(parent, viewType);

        View headerView = inflater.inflate(getPinnedItemViewLayout(), parent, false);
        PinnedLayout pinnedLayout = new PinnedLayout(context);
        pinnedLayout.setUpView(parent, holder.itemView, headerView);

        //说明客户端设置了支持滑动菜单
        if (holder.itemView instanceof SwipeLayout) {
            View itemView = ((SwipeLayout) holder.itemView).getItemView();
            itemView.setClickable(true);

            return new BaseViewHolder(pinnedLayout, itemView);
        }else {
            return new BaseViewHolder(pinnedLayout);
        }
    }

    @Override
    final
    protected void onBindHolder(BaseViewHolder holder, int position) {
        PinnedLayout pinnedLayout = (PinnedLayout) holder.itemView;
        //每个组的第一条需要设置header可见
        if (0 == position || getItem(position).type != getItem(position - 1).type) {
            pinnedLayout.pinnedView.setVisibility(View.VISIBLE);
            holder.itemView.setTag(R.string.pinned_item_status, PinnedItemRange.FIRST);
        } else {
            pinnedLayout.pinnedView.setVisibility(View.GONE);
            holder.itemView.setTag(R.string.pinned_item_status, PinnedItemRange.OTHER);
        }

        final int type = getItem(position).type;
        holder.itemView.setTag(R.string.pinned_item_data_type, type);
        holder.itemView.setTag(R.string.item_index_in_datasection, position);
        super.onBindHolder(holder, position);

        this.convertPinnedHolder(holder, position, type);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.layoutManager = recyclerView.getLayoutManager();

        try {
            //父容器是FrameLayout,否则让粘性功能失效
            FrameLayout parent = (FrameLayout) recyclerView.getParent();
            if (null == this.pinnedViewHolder) {
                this.initPinnedView(parent, recyclerView);
            }
            recyclerView.addOnScrollListener(scrollListener);
        } catch (Exception e) {
            Log.e("DEBUG", "RecyclerView parent must be FrameLayout");
            e.printStackTrace();
            Toast.makeText(context, "RecyclerView parent must be FrameLayout", Toast.LENGTH_SHORT).show();
        }
    }


    private BaseViewHolder pinnedViewHolder;
    private View topPinnedView;

    private void initPinnedView(ViewGroup parent, RecyclerView recyclerView) {
        this.topPinnedView = inflater.inflate(getPinnedItemViewLayout(), recyclerView, false);
        this.pinnedViewHolder = new BaseViewHolder(topPinnedView);
        parent.addView(this.topPinnedView);
        //初始让不可见
        this.topPinnedView.setVisibility(View.GONE);
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if ( mFirstVisibleItemIndex == mFirstCompletelyVisibleItemIndex && 0 == mFirstVisibleItemIndex) {
                topPinnedView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            final int pinnedViewWidth = topPinnedView.getMeasuredWidth();
            final int pinnedViewHeight = topPinnedView.getMeasuredHeight();

            //获取Adapter中粘性头部位置上方的控件
            View headerView = recyclerView.findChildViewUnder(pinnedViewWidth / 2, 5);

            Object obj = null == headerView ? null : headerView.getTag(R.string.pinned_item_data_type);
            if (null != obj && obj instanceof Integer) {
                int position = (int) headerView.getTag(R.string.item_index_in_datasection);
                convertPinnedHolder(pinnedViewHolder, position, (int) obj);
            }

            //获取Adapter中粘性头部位置下方的控件
            headerView = recyclerView.findChildViewUnder(pinnedViewWidth / 2, pinnedViewHeight + 1);

            obj = null == headerView ? null : headerView.getTag(R.string.pinned_item_status);
            if (null == obj || !(obj instanceof Integer)) {
                return;
            }
            int transViewStatus = (int) obj;

            int translationY = headerView.getTop() - pinnedViewHeight;
            if (transViewStatus == PinnedItemRange.FIRST) {
                if (headerView.getTop() > 0) {
                    topPinnedView.setTranslationY(translationY);
                } else {
                    topPinnedView.setTranslationY(0);
                }
            } else if (transViewStatus == PinnedItemRange.OTHER) {
                topPinnedView.setTranslationY(0);

            }

            //在RecyclerView上贴了粘性头部后会遮挡效果，下面处理一下
            findFirstVisibleItemIndex();
            if ( mFirstVisibleItemIndex == mFirstCompletelyVisibleItemIndex && 0 == mFirstVisibleItemIndex) {
                if (View.GONE != topPinnedView.getVisibility()) {
                    topPinnedView.setVisibility(View.GONE);
                }
            }else {
                if (View.VISIBLE != topPinnedView.getVisibility()) {
                    topPinnedView.setVisibility(View.VISIBLE);
                }
            }
        }
    };


    private RecyclerView.LayoutManager layoutManager;
    private int mFirstVisibleItemIndex;
    private int mFirstCompletelyVisibleItemIndex;
    private void findFirstVisibleItemIndex() {
        if (null == layoutManager) {
            return ;
        }

        if (layoutManager instanceof GridLayoutManager) {
            mFirstVisibleItemIndex = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
            mFirstCompletelyVisibleItemIndex = ((GridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            mFirstVisibleItemIndex = ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(new int[1])[0];
            mFirstCompletelyVisibleItemIndex = ((StaggeredGridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPositions(new int[1])[0];

        } else if (layoutManager instanceof LinearLayoutManager) {
            mFirstVisibleItemIndex = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            mFirstCompletelyVisibleItemIndex = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
        }
    }
}