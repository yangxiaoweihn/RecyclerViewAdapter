package ws.dyt.view.adapter.core.base;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import ws.dyt.view.adapter.Log.L;
import ws.dyt.view.viewholder.BaseViewHolder;

/**
 * Created by yangxiaowei on 16/6/8.
 *
 * 带有头部、尾部的 {@link RecyclerView} 适配器，item结构如下
 * {item_sys_header - item_header - item_data - item_footer - item_sys_footer}
 * 1. 系统尾部 sys_footer_item 目前只支持设置一个view
 */
abstract
public class HeaderFooterAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> implements ISysHeader, IUserHeader, ISysFooter, IUserFooter{
    protected Context context;
    protected LayoutInflater inflater;
    protected RecyclerView recyclerView;
    protected List<View> headerViews = new ArrayList<>();
    protected List<View> footerViews = new ArrayList<>();
    //逻辑上设计为系统头部也可以是多个 ，但是实现上系统头部实现为仅有一个
    private List<View> sysHeaderViews = new ArrayList<>();
    private View sysFooterView;
    //真实的数据部分
    protected List<T> realDatas;

    public HeaderFooterAdapter(Context context, List<T> realDatas) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.realDatas = null == realDatas ? new ArrayList<T>() : realDatas;
    }

    public HeaderFooterAdapter(Context context, List<List<T>> sectionDatas, int unused) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        if (null == sectionDatas) {
            this.realDatas = new ArrayList<T>();
        }else {
            if (sectionDatas.isEmpty()) {
                return;
            }
            for (List<T> e:sectionDatas) {
                if (null == e || e.isEmpty()) {
                    continue;
                }
                this.realDatas.addAll(e);
            }
        }
    }

    /**
     * 针对数据项
     *
     * @return
     */
    final
    protected boolean isEmpty() {
        return null == this.realDatas || this.realDatas.isEmpty();
    }

    final
    public T getItem(int position) {
        return isEmpty() ? null : this.realDatas.get(position);
    }

    /**
     * 所有数据项
     * @return
     */
    @Override
    final
    public int getItemCount() {
        return
                this.getSysHeaderViewCount() +
                this.getHeaderViewCount() +
                this.getDataSectionItemCount() +
                this.getFooterViewCount() +
                this.getSysFooterViewCount();
    }

    /**
     * 只针对数据区域项
     *
     * @return
     */
    public int getDataSectionItemCount() {
        return isEmpty() ? 0 : this.realDatas.size();
    }

    @Override
    final
    public int getSysHeaderViewCount(){
        return this.sysHeaderViews.size();
    }

    @Override
    final
    public int getHeaderViewCount() {
        return this.headerViews.size();
    }

    @Override
    final
    public int getFooterViewCount() {
        return this.footerViews.size();
    }

    @Override
    //系统添加footer数量
    final
    public int getSysFooterViewCount() {
        return null == this.sysFooterView ? 0 : 1;
    }

    public int getAllHeaderViewCount() {
        return this.getSysHeaderViewCount() + this.getHeaderViewCount();
    }

    public int getAllFooterViewCount() {
        return this.getSysFooterViewCount() + this.getFooterViewCount();
    }

    @Override
    final
    public int getItemViewType(int position) {
        int shc = this.getSysHeaderViewCount();
        int hc = this.getHeaderViewCount();
        int fc = this.getFooterViewCount();
        int dc = this.getDataSectionItemCount();

        int hAll = shc + hc;

        //处理数据项
        if ((position >= hAll) && (position < (hAll + dc))) {
            position = position - hAll;
            return this.mapDataSectionItemViewTypeToItemLayoutId(position);
        }

        //处理系统头部
        if (shc > 0 && position < shc) {
            return this.sysHeaderViews.get(position).hashCode();
        }

        //处理头部
        if (hc > 0 && position >= shc && position < hAll) {
            position = position - shc;
            return this.headerViews.get(position).hashCode();
        }

        //处理尾部
        if (fc > 0 && position >= (hAll + dc) && position < (hAll + dc + fc)) {
            position = position - (hAll + dc);
            return this.footerViews.get(position).hashCode();
        }

        int sfc = this.getSysFooterViewCount();
        //处理系统尾部
        if (sfc > 0 && position >= (getItemCount() - sfc)) {
            return this.sysFooterView.hashCode();
        }
        return super.getItemViewType(position);
    }

    /**
     * 初始化数据域类型,总是从0开始，已经除去头部
     * @param positionOffsetHeaders
     * @return
     */
    protected int mapDataSectionItemViewTypeToItemLayoutId(int positionOffsetHeaders) {
        return 0;
    }

    private View getSysFooterViewByHashCode(int hashCode) {
        return this.getViewByHashCodeFromList(this.sysHeaderViews, hashCode);
    }

    private View getHeaderViewByHashCode(int hashCode) {
        return this.getViewByHashCodeFromList(this.headerViews, hashCode);
    }

    private View getFooterViewByHashCode(int hashCode) {
        return this.getViewByHashCodeFromList(this.footerViews, hashCode);
    }

    private View getViewByHashCodeFromList(List<View> views, int hashCode) {
        if (null == views) {
            return null;
        }
        for (View v : views) {
            if (v.hashCode() == hashCode) {
                return v;
            }
        }
        return null;
    }

    @Override
    final
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        //处理系统头部
        View sysHeaderView = this.getSysFooterViewByHashCode(viewType);
        if (null != sysHeaderView) {
            return new BaseViewHolder(sysHeaderView);
        }

        //处理头部
        View headerView = this.getHeaderViewByHashCode(viewType);
        if (null != headerView) {
            return new BaseViewHolder(headerView);
        }

        //处理尾部
        View footerView = this.getFooterViewByHashCode(viewType);
        if (null != footerView) {
            return new BaseViewHolder(footerView);
        }

        //处理系统尾部
        View sysFooterView = this.sysFooterView;
        if (null != sysFooterView && viewType == sysFooterView.hashCode()) {
            return new BaseViewHolder(sysFooterView);
        }

        //事件只针对正常数据项
        final BaseViewHolder holder = this.onCreateHolder(parent, viewType);
        this.initItemListener(holder/*, viewType*/);
        return holder;
    }

    protected void initItemListener(final BaseViewHolder holder/*, final int viewType*/){
        if (null == holder) {
            return;
        }
        holder.eventItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeaderFooterAdapter.this.onItemClick(holder, v);
            }
        });

        holder.eventItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return HeaderFooterAdapter.this.onItemLongClick(holder, v);
            }
        });
    }


    protected void onItemClick(final BaseViewHolder holder, View view){
        if (null == this.onItemClickListener) {
            return;
        }
        int hAll = this.getHeaderViewCount() + this.getSysHeaderViewCount();
        this.onItemClickListener.onItemClick(view, holder.getAdapterPosition() - hAll);
    }

    protected boolean onItemLongClick(final BaseViewHolder holder, View view){
        if (null == this.onItemLongClickListener) {
            return false;
        }
        int hAll = this.getHeaderViewCount() + this.getSysHeaderViewCount();
        this.onItemLongClickListener.onItemLongClick(view, holder.getAdapterPosition() - hAll);
        return true;
    }

    /**
     * [for extend] 重新定义data_section域的item生成方式
     * @param parent
     * @param viewType
     * @return
     */
    abstract
    public BaseViewHolder onCreateHolder(ViewGroup parent, int viewType);

    @Override
    final
    public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    final
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        int shc = this.getSysHeaderViewCount();
        //item_sys_header
        if (0 != shc && position < shc) {
            return;
        }

        int hc = this.getHeaderViewCount();
        int hAll = shc + hc;
        //item_header
        if (0 != hc && position < hAll) {
            return;
        }

        int fc = this.getFooterViewCount();
        int dc = this.getDataSectionItemCount();
        //item_footer
        if (0 != fc && position >= (hAll + dc) && position < (hAll + dc + fc)) {
            return;
        }

        int sfc = this.getSysFooterViewCount();
        //item_sys_footer
        if (0 != sfc && position >= (this.getItemCount() - sfc)) {
            return;
        }

        //item_data
        this.onBindHolder(holder, position - hAll);
    }

    /**
     * [for extend]
     * @param holder
     * @param position
     */
    @CallSuper
    protected void onBindHolder(BaseViewHolder holder, int position) {
        this.convert(holder, position);
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position  数据域索引从0开始，已经除去头部
     */
    abstract
    public void convert(BaseViewHolder holder, int position);

    /**
     * 参数校验
     *
     * @param view
     * @param position
     * @param views
     * @return
     */
    private boolean validateAddViewParams(View view, int position, List<View> views) {
        if (null == view) {
            return false;
        }
        if (null == views) {
            views = new ArrayList<>();
        }
        if (views.contains(view)) {
            L.w("Adapter had contains view");
            return false;
        }

        if (position < 0 || position > views.size()) {
            throw new IndexOutOfBoundsException("header or footer position out of bounds");
        }
        return true;
    }

    @Override
    final
    public void addSysHeaderView(View view) {
        this.addSysHeaderView(view, null == sysHeaderViews ? 0 : sysHeaderViews.size());
    }

    @Override
    final
    public void addSysHeaderView(View view, int position) {
        if (!this.validateAddViewParams(view, position, this.sysHeaderViews)) {
            return;
        }

        this.sysHeaderViews.add(position, view);

        notifyItemInserted(position);
    }

    /**
     * 清除所有sys_header
     * @param view
     */
    @Override
    final
    public void setSysHeaderView(View view) {
        if (null == view) {
            return;
        }

        int index = getSysHeaderViewCount();
        if (index > 0) {
            this.sysHeaderViews.clear();
            notifyItemRangeRemoved(0, index);
        }

        this.sysHeaderViews.add(view);
        index = 0;
        notifyItemInserted(index);
    }

    @Override
    final
    public void removeSysHeaderView(View view) {
        if (null == view || !this.sysHeaderViews.contains(view)) {
            return;
        }
        int index = this.sysHeaderViews.indexOf(view);
        this.sysHeaderViews.remove(view);
        notifyItemRemoved(index);
    }

    /**
     * 判断item是否为系统头部
     * @param position
     * @return
     */
    @Override
    final
    public boolean isSysHeaderItemView(int position) {
        int shc = this.getSysHeaderViewCount();
        return position >= 0 && 0 != shc && position < shc;
    }

    @Override
    final
    public void addHeaderView(View view) {
        this.addHeaderView(view, null == headerViews ? 0 : headerViews.size());
    }

    @Override
    final
    public void addHeaderView(View view, int position) {
        if (!this.validateAddViewParams(view, position, this.headerViews)) {
            return;
        }

        this.headerViews.add(position, view);
        int shc = getSysHeaderViewCount();
        int index = shc + position;
        notifyItemInserted(index);
    }

    @Override
    final
    public void removeHeaderView(View view) {
        if (null == view || !this.headerViews.contains(view)) {
            return;
        }
        int shc = this.getSysHeaderViewCount();
        int index = shc + this.headerViews.indexOf(view);
        this.headerViews.remove(view);
        notifyItemRemoved(index);
    }

    /**
     * 用来判断item是否为头部
     * @param position
     * @return
     */
    @Override
    final
    public boolean isHeaderItemView(int position) {
        int shc = this.getSysHeaderViewCount();
        int hc = shc + this.getHeaderViewCount();
        return position >= 0 && 0 != hc && position < hc;
    }

    @Override
    final
    public void addFooterView(View view) {
        this.addFooterView(view, null == footerViews ? 0 : footerViews.size());
    }

    @Override
    final
    public void addFooterView(View view, int position) {
        if (!this.validateAddViewParams(view, position, this.footerViews)) {
            return;
        }

        this.footerViews.add(position, view);
        int shc = this.getSysHeaderViewCount();
        int hc = shc + this.getHeaderViewCount();
        int dc = this.getDataSectionItemCount();
        notifyItemInserted(hc + dc + position);
    }

    @Override
    final
    public void removeFooterView(View view) {
        if (null == view) {
            return;
        }
        int index = this.footerViews.indexOf(view);
        this.removeFooterView(index);
    }

    @Override
    final
    public void removeFooterView(int position) {
        if (position < 0 || position >= footerViews.size()) {
            return;
        }
        int shc = this.getSysHeaderViewCount();
        int hc = this.getHeaderViewCount();
        int hAll = shc + hc;
        int dc = this.getDataSectionItemCount();
        int index = position;
        this.footerViews.remove(index);
        notifyItemRemoved(hAll + dc + index);
    }

    /**
     * 用来判断item是否为尾部
     * @param position
     * @return
     */
    @Override
    final
    public boolean isFooterItemView(int position) {
        int shc = this.getSysHeaderViewCount();
        int hc = shc + this.getHeaderViewCount();
        int dc = this.getDataSectionItemCount();
        int fc = this.getFooterViewCount();
        return position >= 0 && fc != 0 && position >= (hc + dc) && position < (hc + dc + fc);
    }

    @Override
    final
    public void setSysFooterView(View view) {
        if (null == view) {
            return;
        }
        if (this.sysFooterView == view) {
            L.w("Adapter had contains view");
            return;
        }
        this.sysFooterView = view;
        notifyItemChanged(getItemCount());
    }

    @Override
    final
    public void removeSysFooterView(View view) {
        if (null != this.sysFooterView && null != view && this.sysFooterView == view) {
            this.sysFooterView = null;
            notifyItemRemoved(getItemCount());
        }
    }

    /**
     * 用来判断item是否为系统尾部
     * @param position
     * @return
     */
    @Override
    final
    public boolean isSysFooterView(int position) {
        int sfc = this.getSysFooterViewCount();
        int ic = this.getItemCount();
        return position >= 0 && sfc != 0 && position >= (ic - sfc);
    }

    /**
     * 用来判断item是否为真实数据项，除了头部、尾部、系统尾部等非真实数据项，结构为:
     * item_header - item_data - item_footer - item_sys_footer
     * @param position
     * @return true:将保留LayoutManager的设置  false:该item将会横跨整行(对GridLayoutManager,StaggeredLayoutManager将很有用)
     */
    private boolean isDataItemView(int position) {
        int shc = this.getSysHeaderViewCount();
        int hc = this.getHeaderViewCount();
        int hAll = shc + hc;
        int dc = this.getDataSectionItemCount();
        boolean isHeaderOrFooter = position >= 0 && position >= hAll && position < (hAll + dc);
        if (!isHeaderOrFooter) {
            return isHeaderOrFooter;
        }
        return this.isFullSpanWithItemView(position - hAll);
    }

    /**
     * 设置数据域item是否横跨
     * @param position
     * @return
     */
    protected boolean isFullSpanWithItemView(int position) {
        return false;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (this.recyclerView == recyclerView) {
            return;
        }
        this.recyclerView = recyclerView;
        this.adapterGridLayoutManager();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        this.adapterStaggeredGridLayoutManager(holder);
    }

    private void adapterGridLayoutManager() {
        final RecyclerView.LayoutManager layoutManager = null == recyclerView ? null : recyclerView.getLayoutManager();
        if (null == layoutManager) {
            return;
        }
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager glm = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup ssl = glm.getSpanSizeLookup();
            glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return !isDataItemView(position) ? glm.getSpanCount() : ssl.getSpanSize(position);
                }
            });
        }
    }

    private void adapterStaggeredGridLayoutManager(BaseViewHolder holder) {
        final RecyclerView.LayoutManager layoutManager = null == recyclerView ? null : recyclerView.getLayoutManager();
        if (null == layoutManager) {
            return;
        }

        if (layoutManager instanceof StaggeredGridLayoutManager) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            int position = holder.getAdapterPosition();
            if (null != lp && lp instanceof StaggeredGridLayoutManager.LayoutParams && !isDataItemView(position)) {
                ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
            }
        }
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            ItemTypeSummary.HEADER_SYS,
            ItemTypeSummary.HEADER_USR,
            ItemTypeSummary.DATA,
            ItemTypeSummary.FOOTER_USR,
            ItemTypeSummary.FOOTER_SYS
    })
    public @interface ItemTypeSummaryWhere{}
    protected interface ItemTypeSummaryPrivate {
        int HEADER_SYS = 0;
        int HEADER_USR = 1;
        int FOOTER_USR = 3;
        int FOOTER_SYS = 4;
    }
    public interface ItemTypeSummary extends ItemTypeSummaryPrivate{
        int DATA       = 2;
    }

    /**
     * 根据item view位置获取对应类型
     * @param position
     * @return
     */
    @ItemTypeSummaryWhere
    public int getItemTypeByPosition(int position) {
        if (isSysHeaderItemView(position)) {
            return ItemTypeSummary.HEADER_SYS;
        }

        if (isHeaderItemView(position)) {
            return ItemTypeSummary.HEADER_USR;
        }

        if (isFooterItemView(position)) {
            return ItemTypeSummary.FOOTER_USR;
        }

        if (isSysFooterView(position)) {
            return ItemTypeSummary.FOOTER_SYS;
        }

        return ItemTypeSummary.DATA;
    }

    //##------------------------->>>

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 点击事件
     */
    public interface OnItemClickListener {
        /**
         * @param itemView
         * @param position
         */
        void onItemClick(View itemView, int position);
    }

    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 长按事件
     */
    public interface OnItemLongClickListener {
        /**
         * @param itemView
         * @param position
         */
        void onItemLongClick(View itemView, int position);
    }
}
