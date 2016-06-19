package ws.dyt.library.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
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
 * 其中 header footer 个数一致
 */
abstract
public class SectionAdapter<T> extends BaseHFAdapter<T> implements SectionMultiItemViewType {
    public SectionAdapter(Context context, List<T> datas) {
        super(context, datas);
    }

    //封装组右边界，已包括header、footer，右开区间
    private int[] dataSectionRangeIndex;

    /**
     *
     * @param context
     * @param sectionDatas 确保该参数中数据区域列表不能为空（确保都是真实的数据）
     * @param aa
     */
    public SectionAdapter(Context context, List<List<T>> sectionDatas, int aa) {
        super(context, sectionDatas, aa);
        if (null == sectionDatas || sectionDatas.isEmpty()) {
            return;
        }

        dataSectionRangeIndex = new int[sectionDatas.size()];
        int sum = 0;
        for (int group = 0; group < sectionDatas.size(); group++) {
            List<T> e = sectionDatas.get(group);
            sum += e.size();
            sum += 2;
            dataSectionRangeIndex[group] = sum;
        }
    }

    @Override
    public int getDataSectionItemCount() {
        return super.getDataSectionItemCount() + getSectionHeaderItemCount() + getSectionFooterItemCount();
    }

    private int getSectionHeaderItemCount() {
        return dataSectionRangeIndex.length;
    }

    private int getSectionFooterItemCount() {
        return dataSectionRangeIndex.length;
    }

    final
    public T getItem(int group, int positionOfGroup) {
        return getItem(getDataIndex(group, positionOfGroup));
    }

    /**
     * 转化到数据列表索引
     * @param group
     * @param positionOfGroup
     * @return
     */
    private int getDataIndex(int group, int positionOfGroup) {
        int len = dataSectionRangeIndex.length;
        for (int i = 0; i < len; i++) {
            if (group == i) {
                //计算section左边界
                int rangeLeft = i > 0 ? dataSectionRangeIndex[i - 1] : 0;

                //index = 左边界 - (section的header、footer总和) + 减法偏移量 + section数据索引
                int index = rangeLeft - (group * 2 + 1) + 1 + positionOfGroup;
                return index;
            }
        }
        return 0;
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
        DataSectionItemWrapper item = getDataSectionItemInfo(position - hAll);
        //是否为数据域中数据项
        return null != item && ItemType.ITEM_DATA == item.itemType;
    }

    /**
     * 数据域包装器 item_data {section_header - section_data - section_footer}
     */
    private static class DataSectionItemWrapper {
        //所在组
        int group;
        //在组中的位置(包括组header和footer)
        int positionOfGroup;
        //组内真实数据在数据表中的真实位置
        int positionOfData;
        //组内item类型
        @ItemTypeWhere
        int itemType;


        public DataSectionItemWrapper(@ItemTypeWhere int itemType, int group, int positionOfGroup) {
            this(itemType, group, positionOfGroup, -404);
        }

        public DataSectionItemWrapper(@ItemTypeWhere int itemType, int group, int positionOfGroup, int positionOfData) {
            this.itemType = itemType;
            this.group = group;
            this.positionOfGroup = positionOfGroup;
            this.positionOfData = positionOfData;
        }


    }
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ItemType.ITEM_HEADER, ItemType.ITEM_DATA, ItemType.ITEM_FOOTER})
    public @interface ItemTypeWhere {}
    public interface ItemType {
        int ITEM_HEADER      = 0;
        int ITEM_DATA        = 1;
        int ITEM_FOOTER      = 2;
    }

    /**
     * 获取section域每个item的信息
     * section结构为  section_header -> section_data -> setion_footer
     * @param position
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
                if (child == 0) {
                    dataSectionItemWrapper = new DataSectionItemWrapper(ItemType.ITEM_HEADER, group, child);
                }else if (position + 1 == rangeRight){
                    dataSectionItemWrapper = new DataSectionItemWrapper(ItemType.ITEM_FOOTER, group, child);
                }else {
                    dataSectionItemWrapper = new DataSectionItemWrapper(ItemType.ITEM_DATA, group, child - 1, position - (group * 2 + 1));
                }
                break;
            }
        }
        return dataSectionItemWrapper;
    }


    @Override
    final
    protected int convertDataSectionItemViewType(int position) {
        int type = 0;
        DataSectionItemWrapper info = this.getDataSectionItemInfo(position);
        if (null != info) {
            int group = info.group;
            int positionOfGroup = info.positionOfGroup;
            switch (info.itemType) {
                case ItemType.ITEM_HEADER:{
                    type = this.getSectionHeaderItemViewLayout(group/*, positionOfGroup*/);
                    break;
                }
                case ItemType.ITEM_DATA:{
                    type = getSectionDataItemViewLayout(group, positionOfGroup);
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
    protected void initListener(final BaseViewHolder holder, int viewType) {
        if (null == holder) {
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

    private int findGroup(int position) {
        int len = dataSectionRangeIndex.length;
        for (int group = 0; group < len; group++) {
            int rangeRight = dataSectionRangeIndex[group];
            if (position < rangeRight) {
                return group;
            }
        }
        return -404;
    }

    @Override
    final
    public void convert(BaseViewHolder holder, int position) {
        DataSectionItemWrapper info = this.getDataSectionItemInfo(position);
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
                this.convertSectionData(holder, group, positionOfGroup);
                convertSectionData(holder, info.positionOfData);
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
    public void convertSectionData(BaseViewHolder holder, int group, int position);
    abstract
    public void convertSectionData(BaseViewHolder holder, int position);
    abstract
    public void convertSectionFooter(BaseViewHolder holder, int group/*, int position*/);
}

interface SectionMultiItemViewType {
    @LayoutRes
    int getSectionDataItemViewLayout(int group, int position);

    @LayoutRes
    int getSectionHeaderItemViewLayout(int group/*, int position*/);

    @LayoutRes
    int getSectionFooterItemViewLayout(int group/*, int position*/);
}
