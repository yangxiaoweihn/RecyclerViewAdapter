package ws.dyt.recyclerviewadapter.databinding;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

import ws.dyt.adapter.adapter.ItemWrapper;
import ws.dyt.adapter.adapter.SuperPinnedAdapter;
import ws.dyt.adapter.viewholder.BaseViewHolder;
import ws.dyt.recyclerviewadapter.CourseChapterBinding;
import ws.dyt.recyclerviewadapter.CourseClassBinding;
import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.databinding.viewmodels.CourseViewModel;
import ws.dyt.recyclerviewadapter.pinned.CourseResult;
import ws.dyt.recyclerviewadapter.pinned.PinnedItemGridFragment;

/**
 * Created by yangxiaowei on 2018/1/22.
 *
 * DataBinding 支持
 */

public class DataBindingFragment extends PinnedItemGridFragment {

    @Override
    protected SuperPinnedAdapter<ItemWrapper> getAdapter() {
        return new DataBindingAdapter(getContext(), generateData());
    }

    static class DataBindingAdapter extends SuperPinnedAdapter<ItemWrapper> {
        public DataBindingAdapter(Context context, List data) {
            super(context, data);
        }

        //通过重写该方法设置多类型支持
        @Override
        public int getItemViewLayout(int position) {
            if (getItem(position).data instanceof String) {
                return R.layout.item_course_group_binding;
            }
            return R.layout.item_course_detail_grid_binding;
        }

        @Override
        public int getPinnedItemViewLayout() {
            return R.layout.item_course_group_binding;
        }

        @Override
        public void convertPinnedHolder(BaseViewHolder holder, int position, int type) {
            ItemWrapper obj = getItem(position);
            if (obj.data instanceof String) {
                holder.setText(R.id.tv_text_pinned, (String) obj.data);
            }
        }

        @Override
        public BaseViewHolder onCreateViewHolderByItemType(int itemLayoutOfViewType, ViewGroup parent) {

            BaseViewHolder holder = null;
            if (itemLayoutOfViewType == R.layout.item_course_group_binding) {

                CourseChapterBinding binding = CourseChapterBinding.inflate(mInflater);
                holder = new BaseViewHolder(binding.getRoot());
                holder.setHolderTag(binding);
            }else if (itemLayoutOfViewType == R.layout.item_course_detail_grid_binding) {

                CourseClassBinding binding = CourseClassBinding.inflate(mInflater);
                holder = new BaseViewHolder(binding.getRoot());
                holder.setHolderTag(binding);
            }

            return holder;
        }

        @Override
        public void convert(BaseViewHolder holder, int position) {

            Object data = getItem(position).data;
            Object obj = holder.holderTag;
            if (obj instanceof CourseChapterBinding) {

                CourseChapterBinding binding = (CourseChapterBinding) obj;
                binding.setModel(new CourseViewModel(new CourseResult.Course((String) data)));
            }else {

                CourseClassBinding binding = (CourseClassBinding) obj;
                binding.setModel(new CourseViewModel((CourseResult.Course) data));
            }
        }


        //设置组标题横跨整个列
        @Override
        public boolean isFullSpanWithItemView(int position) {
            return position == 0 || getItem(position).type != getItem(position - 1).type;
        }
    }
}
