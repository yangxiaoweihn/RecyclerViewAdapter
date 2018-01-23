package ws.dyt.recyclerviewadapter.pinned;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.adapter.adapter.ItemWrapper;
import ws.dyt.adapter.adapter.SuperPinnedAdapter;
import ws.dyt.adapter.viewholder.BaseViewHolder;
import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.base.BaseFragment;
import ws.dyt.recyclerviewadapter.utils.Data;

/**
 *
 * 网格布局的粘性头部测试
 */
public class PinnedItemGridFragment extends BaseFragment<ItemWrapper> {


    public PinnedItemGridFragment() {
        // Required empty public constructor
    }

    @Override
    protected int setContentViewId() {
        return R.layout.recyclerview;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {

        return new GridLayoutManager(getContext(), 2);
    }

    @Override
    protected SuperPinnedAdapter<ItemWrapper> getAdapter() {
        return new StickyGridAdapter(getContext(), generateData());
    }

    static class StickyGridAdapter extends SuperPinnedAdapter<ItemWrapper> {
        public StickyGridAdapter(Context context, List data) {
            super(context, data);
        }

        //通过重写该方法设置多类型支持
        @Override
        public int getItemViewLayout(int position) {
            if (getItem(position).data instanceof String) {
                return R.layout.item_course_group;
            }
            return R.layout.item_course_detail_grid;
        }

        @Override
        public int getPinnedItemViewLayout() {
            return R.layout.item_course_group;
        }

        @Override
        public void convertPinnedHolder(BaseViewHolder holder, int position, int type) {
            ItemWrapper obj = getItem(position);
            Log.d("DEBUG", "sticky: "+obj.data);
            if (obj.data instanceof String) {
                holder.setText(R.id.tv_text_pinned, (String) obj.data);
            }
        }

        @Override
        public void convert(BaseViewHolder holder, int position) {
            Object obj = getItem(position).data;
            if (obj instanceof String) {
                this.bindCourseTitle(holder, (String) obj);
                return;
            }
            this.bindCourse(holder, (CourseResult.Course) obj);
        }

        //绑定课程组信息
        private void bindCourseTitle(BaseViewHolder holder, String title) {
            Log.d("DEBUG", "groupTitle: "+title);
            holder.setText(R.id.tv_text_pinned, title);
        }

        //绑定课程信息
        private void bindCourse(BaseViewHolder holder, CourseResult.Course course) {
            holder.setText(R.id.tv_name, course.name)
                    .setText(R.id.tv_length, course.length);
        }

        //设置组标题横跨整个列
        @Override
        public boolean isFullSpanWithItemView(int position) {
            return position == 0 || getItem(position).type != getItem(position - 1).type;
        }
    }

    protected List<ItemWrapper> generateData() {

        List<CourseResult> results = Data.generateCourses(getResources());

        List<ItemWrapper> data = new ArrayList<>();
        for (CourseResult e:results) {
            int type = e.title.hashCode();
            data.add(new ItemWrapper<>(type, e.title));
            for (CourseResult.Course cc:e.course_list) {
                cc.title = e.title;
                data.add(new ItemWrapper<>(type, cc));
            }
        }
        return data;
    }

}
