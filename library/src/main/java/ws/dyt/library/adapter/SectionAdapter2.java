package ws.dyt.library.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
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
public class SectionAdapter2<T> extends BaseHFAdapter<T> implements SectionMultiItemViewType {
    public SectionAdapter2(Context context, List<T> datas) {
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
     * @param aa
     */
    public SectionAdapter2(Context context, List<List<T>> sectionDatas, int aa, boolean isSupportHeader, boolean isSupportFooter) {
        super(context, sectionDatas, aa);
        this.isSupportHeader = isSupportHeader;
        this.isSupportFooter = isSupportFooter;
        if (null == sectionDatas || sectionDatas.isEmpty()) {
            return;
        }

        dataSectionRangeIndex = new int[sectionDatas.size()];
        int offset = 0;
        if (isSupportHeader && isSupportFooter) {
            offset = 2;
        }else if (!isSupportHeader && !isSupportFooter) {
            offset = 0;
        }else {
            offset = 1;
        }
        int sum = 0;
        for (int group = 0; group < sectionDatas.size(); group++) {
            List<T> e = sectionDatas.get(group);
            sum += e.size();
            sum += offset;
            dataSectionRangeIndex[group] = sum;
        }
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
                int index = rangeLeft - xxoo(group) + 1 + positionOfGroup;
                return index;
            }
        }
        return 0;
    }

    private int xxoo(int group) {
        int c = 0;
        if (isSupportHeader && isSupportFooter) {
            c = group * 2 + 1;
        }else if (isSupportHeader) {
            c = group * 2 - 1;
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
        DataSectionItemWrapper item = getDataSectionItemInfo(position - hAll);
        //是否为数据域中数据项
        return null != item && DataSectionItemWrapper.ItemType.ITEM_DATA == item.itemType;
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

        @Retention(RetentionPolicy.SOURCE)
        @IntDef({ItemType.ITEM_HEADER, ItemType.ITEM_DATA, ItemType.ITEM_FOOTER})
        @interface ItemTypeWhere {}
        interface ItemType {
            int ITEM_HEADER      = 0;
            int ITEM_DATA        = 1;
            int ITEM_FOOTER      = 2;
        }
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
                /*
                if (child == 0) {
                    dataSectionItemWrapper = new DataSectionItemWrapper(DataSectionItemWrapper.ItemType.ITEM_HEADER, group, child);
                }else if (position + 1 == rangeRight){
                    dataSectionItemWrapper = new DataSectionItemWrapper(DataSectionItemWrapper.ItemType.ITEM_FOOTER, group, child);
                }else {
                    dataSectionItemWrapper = new DataSectionItemWrapper(DataSectionItemWrapper.ItemType.ITEM_DATA, group, child - 1, position - (group * 2 + 1));
                }
                */

                if (isSupportHeader && isSupportFooter) {
                    if (child == 0) {
                        dataSectionItemWrapper = new DataSectionItemWrapper(DataSectionItemWrapper.ItemType.ITEM_HEADER, group, child);
                    }else if (position + 1 == rangeRight){
                        dataSectionItemWrapper = new DataSectionItemWrapper(DataSectionItemWrapper.ItemType.ITEM_FOOTER, group, child);
                    }else {
                        dataSectionItemWrapper = new DataSectionItemWrapper(DataSectionItemWrapper.ItemType.ITEM_DATA, group, child - 1, position - xxoo(group));
                    }
                }else if (isSupportHeader) {
                    if (child == 0) {
                        dataSectionItemWrapper = new DataSectionItemWrapper(DataSectionItemWrapper.ItemType.ITEM_HEADER, group, child);
                    }else {
                        dataSectionItemWrapper = new DataSectionItemWrapper(DataSectionItemWrapper.ItemType.ITEM_DATA, group, child - 1, position - xxoo(group));
                    }
                }else if (isSupportFooter) {
                    if (position + 1 == rangeRight){
                        dataSectionItemWrapper = new DataSectionItemWrapper(DataSectionItemWrapper.ItemType.ITEM_FOOTER, group, child);
                    }else {
                        dataSectionItemWrapper = new DataSectionItemWrapper(DataSectionItemWrapper.ItemType.ITEM_DATA, group, child - 0, position - xxoo(group));
                    }
                }else {
                    dataSectionItemWrapper = new DataSectionItemWrapper(DataSectionItemWrapper.ItemType.ITEM_DATA, group, child - 0, position - xxoo(group));
                }


                break;
            }
        }
        return dataSectionItemWrapper;
    }


    @Override
    final
    protected int convertDataSectionItemViewType(int position) {
        DataSectionItemWrapper info = getDataSectionItemInfo(position);
        if (null == info) {
            return super.convertDataSectionItemViewType(position);
        }
        int type = 0;
        int group = info.group;
        int positionOfGroup = info.positionOfGroup;
        switch (info.itemType) {
            case DataSectionItemWrapper.ItemType.ITEM_HEADER:{
                type = getSectionHeaderItemViewLayout(group/*, positionOfGroup*/);
                break;
            }
            case DataSectionItemWrapper.ItemType.ITEM_DATA:{
                type = getSectionDataItemViewLayout(group, positionOfGroup);
                break;
            }
            case DataSectionItemWrapper.ItemType.ITEM_FOOTER:{
                type = getSectionFooterItemViewLayout(group/*, positionOfGroup*/);
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
            case DataSectionItemWrapper.ItemType.ITEM_HEADER:{
                convertSectionHeader(holder, group/*, positionOfGroup*/);
                break;
            }
            case DataSectionItemWrapper.ItemType.ITEM_DATA:{
                convertSectionData(holder, group, positionOfGroup);
                convertSectionData(holder, info.positionOfData);
                break;
            }
            case DataSectionItemWrapper.ItemType.ITEM_FOOTER:{
                convertSectionFooter(holder, group/*, positionOfGroup*/);
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

