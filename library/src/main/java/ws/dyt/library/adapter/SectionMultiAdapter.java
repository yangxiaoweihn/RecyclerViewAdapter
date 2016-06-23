package ws.dyt.library.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import ws.dyt.library.adapter.base.BaseHFAdapter;
import ws.dyt.library.viewholder.BaseViewHolder;

/**
 * Created by yangxiaowei on 16/6/13.
 *
 * 扩展 data_item 数据域结构，每一个data_item 结构都包括 头部、数据、尾部，结构如下
 * item_data {section_header - section_data - section_footer}
 * 其中 header footer 可有可无
 */
abstract
public class SectionMultiAdapter<T> extends BaseHFAdapter<T> implements SectionMultiItemViewType {
    public SectionMultiAdapter(Context context, List<T> datas) {
        super(context, datas);
    }


    private boolean isSupportHeader;
    private boolean isSupportFooter;
    //封装组右边界，已包括header、footer，右开区间
    private int[] dataSectionRangeIndex;

    /**
     *
     * @param context
     * @param sectionDatas 确保该参数中数据区域列表不能为空（确保都是真实的数据）
     * @param isSupportHeader
     * @param isSupportFooter
     */
    public SectionMultiAdapter(Context context, List<List<T>> sectionDatas, boolean isSupportHeader, boolean isSupportFooter) {
        super(context, sectionDatas, -1);
        this.isSupportHeader = isSupportHeader;
        this.isSupportFooter = isSupportFooter;
        if (null == sectionDatas || sectionDatas.isEmpty()) {
            return;
        }

        dataSectionRangeIndex = new int[sectionDatas.size()];
        int offset = calSectionItemOffset(isSupportHeader, isSupportFooter);
        int sum = 0;
        for (int group = 0; group < sectionDatas.size(); group++) {
            List<T> e = sectionDatas.get(group);
            sum += e.size();
            sum += offset;
            dataSectionRangeIndex[group] = sum;
        }

    }

    /**
     * 初始化数据项偏移
     * @param isSupportHeader
     * @param isSupportFooter
     * @return
     */
    private int calSectionItemOffset(boolean isSupportHeader, boolean isSupportFooter) {
        int offset;
        if (isSupportHeader && isSupportFooter) {
            offset = 2;
        } else if (!isSupportHeader && !isSupportFooter) {
            offset = 0;
        }else {
            offset = 1;
        }
        return offset;
    }

    @Override
    public int getDataSectionItemCount() {
        return super.getDataSectionItemCount() + getSectionHeaderItemCount() + getSectionFooterItemCount();
    }

    private int getSectionHeaderItemCount() {
        return isSupportHeader ? dataSectionRangeIndex.length : 0;
    }

    private int getSectionFooterItemCount() {
        return isSupportFooter ? dataSectionRangeIndex.length : 0;
    }

    final
    public T getItem(int group, int positionOfGroup) {
        return this.getItem(calDataIndex(group, positionOfGroup));
    }

    /**
     * 转化到数据列表索引 （组数据都被转化到一个list中）
     * @param group
     * @param positionOfGroup
     * @return
     */
    private int calDataIndex(int group, int positionOfGroup) {
        int len = dataSectionRangeIndex.length;
        for (int i = 0; i < len; i++) {
            if (group == i) {
                //计算section左边界
                int rangeLeft = i > 0 ? dataSectionRangeIndex[i - 1] : 0;

                //index = 左边界 - (section的header、footer总和) + 减法偏移量 + section数据索引
                int index = rangeLeft - calSectionItemByThisGroup(group) + 1 + positionOfGroup;

                Log.e("PPPPP", group+" , index: "+positionOfGroup+" , real: "+index);
                return index;
            }
        }
        return 0;
    }

    /**
     * 计算当前组之前有几个非数据项  即{section_header - section_footer}的数量
     * @param group
     * @return
     */
    private int calSectionItemByThisGroup(int group) {
        int c = 0;
        if (isSupportHeader && isSupportFooter) {
            c = group * 2 + 1;
        }else if (isSupportHeader) {
            c = group * 1 + 1;
        }else if (isSupportFooter) {
            c = group;
        }
        return c;
    }

    @Override
    final
    protected boolean isDataItemView(int position) {
        //非数据域
        boolean b = super.isDataItemView(position);
        if (b == false) {
            return b;
        }

        int hAll = getSysHeaderViewCount() + getHeaderViewCount();
        DataSectionItemWrapper item = this.getDataSectionItemInfo(position - hAll);
        //是否为数据域中数据项
        return null != item && ItemType.ITEM_DATA == item.itemType;
    }

    private static class DataWrapper {
        //所在组
        public int group;
        //在组中的位置(包括组header和footer)
        public int positionOfGroup;
        //组内真实数据在数据表中的真实位置
        public int positionOfData;

        public DataWrapper(int group, int positionOfGroup, int positionOfData) {
            this.group = group;
            this.positionOfGroup = positionOfGroup;
            this.positionOfData = positionOfData;
        }
    }
    /**
     * 数据域包装器 item_data {section_header - section_data - section_footer}
     */
    public static class DataSectionItemWrapper extends DataWrapper{
        //组内item类型
        @ItemTypeWhere
        public int itemType;


        public DataSectionItemWrapper(@ItemTypeWhere int itemType, int group, int positionOfGroup) {
            this(itemType, group, positionOfGroup, -404);
        }

        public DataSectionItemWrapper(@ItemTypeWhere int itemType, int group, int positionOfGroup, int positionOfData) {
            super(group, positionOfGroup, positionOfData);
            this.itemType = itemType;
        }
    }
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ItemType.ITEM_HEADER, ItemType.ITEM_DATA, ItemType.ITEM_FOOTER})
    @interface ItemTypeWhere {}
    public interface ItemType {
        int ITEM_HEADER      = -1;
        int ITEM_DATA        = -1 + ITEM_HEADER;
        int ITEM_FOOTER      = -1 + ITEM_DATA;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            ItemTypeSummaryPrivate.HEADER_SYS,
            ItemTypeSummaryPrivate.HEADER_USR,
            ItemTypeSummaryPrivate.FOOTER_USR,
            ItemTypeSummaryPrivate.FOOTER_SYS,
            ItemTypeSectionSummary.ITEM_HEADER,
            ItemTypeSectionSummary.ITEM_DATA,
            ItemTypeSectionSummary.ITEM_FOOTER
    })
    public @interface ItemTypeSectionSummaryWhere{}
    public interface ItemTypeSectionSummary extends ItemTypeSummaryPrivate , ItemType{}

    /**
     * 获取section域每个item的信息
     * section结构为  section_header -> section_data -> setion_footer
     * @param position  除去 item_header_sys 和 item_header_usr
     * @return
     */
    private DataSectionItemWrapper getDataSectionItemInfo(int position) {
        DataSectionItemWrapper dataSectionItemWrapper = null;
        int len = dataSectionRangeIndex.length;
        for (int group = 0; group < len; group++) {
            int rangeRight = dataSectionRangeIndex[group];
            if (position < rangeRight) {
                int rangeLeft = group > 0 ? dataSectionRangeIndex[group - 1] : 0;
                //child为每一个section中的索引，包括header和footer,
                // section结构为  section_header -> section_data -> setion_footer
                int child = position - rangeLeft;

                if (isSupportHeader && isSupportFooter) {
                    if (child == 0) {
                        dataSectionItemWrapper = new DataSectionItemWrapper(ItemType.ITEM_HEADER, group, child);
                    }else if (position + 1 == rangeRight){
                        dataSectionItemWrapper = new DataSectionItemWrapper(ItemType.ITEM_FOOTER, group, child);
                    }else {
                        dataSectionItemWrapper = new DataSectionItemWrapper(ItemType.ITEM_DATA, group, child - 1, position - calSectionItemByThisGroup(group));
                    }
                }else if (isSupportHeader) {
                    if (child == 0) {
                        dataSectionItemWrapper = new DataSectionItemWrapper(ItemType.ITEM_HEADER, group, child);
                    }else {
                        dataSectionItemWrapper = new DataSectionItemWrapper(ItemType.ITEM_DATA, group, child - 1, position - calSectionItemByThisGroup(group));
                    }
                }else if (isSupportFooter) {
                    if (position + 1 == rangeRight){
                        dataSectionItemWrapper = new DataSectionItemWrapper(ItemType.ITEM_FOOTER, group, child);
                    }else {
                        dataSectionItemWrapper = new DataSectionItemWrapper(ItemType.ITEM_DATA, group, child - 0, position - calSectionItemByThisGroup(group));
                    }
                }else {
                    dataSectionItemWrapper = new DataSectionItemWrapper(ItemType.ITEM_DATA, group, child - 0, position - calSectionItemByThisGroup(group));
                }
                break;
            }
        }
        return dataSectionItemWrapper;
    }


    public static class DataItemWrapper extends DataWrapper {
        @ItemTypeSectionSummaryWhere
        public int itemType;

        public DataItemWrapper(@ItemTypeSectionSummaryWhere int itemType) {
            this(itemType, 0, 0, 0);
        }

        public DataItemWrapper(@ItemTypeSectionSummaryWhere int itemType, int group, int positionOfGroup, int positionOfData) {
            super(group, positionOfGroup, positionOfData);
            this.itemType = itemType;
        }
    }

    /**
     * 针对非线性布局管理器，在decoration时可能需要更详细的分组信息
     * @param position
     * @return
     */
//    @SuppressWarnings("unchecked")
    public DataItemWrapper getItemInfo(int position) {
        @ItemTypeSummaryWhere int type = super.getItemTypeByPosition(position);
        //系统提供非data部分
        if (ItemTypeSummary.DATA != type) {
            @ItemTypeSectionSummaryWhere int e = type;
            return new DataItemWrapper(e);
        }
        position -= (getHeaderViewCount() + getSysHeaderViewCount());
        DataSectionItemWrapper wrapper = getDataSectionItemInfo(position);
        return new DataItemWrapper(wrapper.itemType, wrapper.group, wrapper.positionOfGroup, wrapper.positionOfData);
    }


    @Override
    final
    protected int convertDataSectionItemViewType(int position) {
        DataSectionItemWrapper info = this.getDataSectionItemInfo(position);
        if (null == info) {
            return super.convertDataSectionItemViewType(position);
        }
        int type;
        int group = info.group;
        int positionOfGroup = info.positionOfGroup;
        switch (info.itemType) {
            case ItemType.ITEM_HEADER:{
                type = this.getSectionHeaderItemViewLayout(group/*, positionOfGroup*/);
                break;
            }
            case ItemType.ITEM_DATA:{
                type = this.getSectionDataItemViewLayout(group, positionOfGroup);
                break;
            }
            case ItemType.ITEM_FOOTER:{
                type = this.getSectionFooterItemViewLayout(group/*, positionOfGroup*/);
                break;
            }
            default:{
                type = super.convertDataSectionItemViewType(position);
                break;
            }
        }
        return type;
    }

    @Override
    final
    public BaseViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(inflater.inflate(viewType, parent, false));
    }

    @Override
    final
    public void convert(BaseViewHolder holder, int position) {
        DataSectionItemWrapper info = getDataSectionItemInfo(position);
        if (null == info) {
            return;
        }
        int group = info.group;
        int positionOfGroup = info.positionOfGroup;
        switch (info.itemType) {
            case ItemType.ITEM_HEADER:{
                this.convertSectionHeader(holder, group/*, positionOfGroup*/);
                break;
            }
            case ItemType.ITEM_DATA:{
                Log.e("GGGG", ""+group+" , "+positionOfGroup+" , "+info.positionOfData);
                this.convertSectionData(holder, group, positionOfGroup, info.positionOfData);
                break;
            }
            case ItemType.ITEM_FOOTER:{
                this.convertSectionFooter(holder, group/*, positionOfGroup*/);
                break;
            }
        }
    }

    abstract
    public void convertSectionHeader(BaseViewHolder holder, int group/*, int position*/);
    abstract
    public void convertSectionData(BaseViewHolder holder, int group, int positionOfGroup, int positionOfData);
    abstract
    public void convertSectionFooter(BaseViewHolder holder, int group/*, int position*/);

    @Override
    final
    protected void initItemListener(final BaseViewHolder holder, int viewType) {
        if (null == holder || null == holder.itemView) {
            return;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null  == onItemClickListener) {
                    return;
                }
                int hAll = getHeaderViewCount() + getSysHeaderViewCount();
                int position = holder.getAdapterPosition() - hAll;

                DataSectionItemWrapper info = getDataSectionItemInfo(position);

                int group = info.group;
                onItemClickListener.onClick(group, info.positionOfGroup, info.positionOfData, info.itemType);
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null == onItemLongClickListener) {
                    return false;
                }
                int hAll = getHeaderViewCount() + getSysHeaderViewCount();
                int position = holder.getAdapterPosition() - hAll;

                DataSectionItemWrapper info = getDataSectionItemInfo(position);

                int group = info.group;
                onItemLongClickListener.onItemLongClick(group, info.positionOfGroup, info.positionOfData, info.itemType);
                return true;
            }
        });

    }

    @Override
    @ItemTypeSectionSummaryWhere
    public int getItemTypeByPosition(int position) {
        @ItemTypeSummaryWhere int type = super.getItemTypeByPosition(position);
        @ItemTypeSectionSummaryWhere int e = type;
        if (ItemTypeSummary.DATA != type) {
            return e;
        }

        position -= (getHeaderViewCount() + getSysHeaderViewCount());
        DataSectionItemWrapper wrapper = getDataSectionItemInfo(position);

        if (ItemType.ITEM_HEADER == wrapper.itemType) {
            return ItemTypeSectionSummary.ITEM_HEADER;
        }

        if (ItemType.ITEM_FOOTER == wrapper.itemType) {
            return ItemTypeSectionSummary.ITEM_FOOTER;
        }

        return ItemTypeSectionSummary.ITEM_DATA;
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener{
        void onClick(int group, int positionOfGroup, int positionOfData, @ItemTypeWhere int type);
    }

    private OnItemLongClickListener onItemLongClickListener;
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int group, int positionOfGroup, int positionOfData, @ItemTypeWhere int type);
    }
}

