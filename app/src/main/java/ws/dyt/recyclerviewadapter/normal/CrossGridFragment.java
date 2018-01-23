package ws.dyt.recyclerviewadapter.normal;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.adapter.adapter.ItemWrapper;
import ws.dyt.adapter.adapter.SuperAdapter;
import ws.dyt.adapter.adapter.core.base.HeaderFooterAdapter;
import ws.dyt.adapter.viewholder.BaseViewHolder;
import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.base.BaseFragment;
import ws.dyt.recyclerviewadapter.pinned.CourseResult;
import ws.dyt.recyclerviewadapter.utils.Data;

/**
 * Created by yangxiaowei on 16/6/22.
 *
 * 网格布局中，横跨列api测试
 */
public class CrossGridFragment extends BaseFragment<ItemWrapper> {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {

        return new GridLayoutManager(getContext(), 2);
    }

    @Override
    protected HeaderFooterAdapter<ItemWrapper> getAdapter() {
        return new CrossGridAdapter(getContext(), this.generateData());
    }

    static class CrossGridAdapter extends SuperAdapter<ItemWrapper> {
        public CrossGridAdapter(Context context, List data) {
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
            return getItem(position).data instanceof String;
        }
    }

    protected List<ItemWrapper> generateData() {

        List<CourseResult> results = Data.generateCourses(getResources());

        List<ItemWrapper> data = new ArrayList<>();
        for (CourseResult e:results) {
            data.add(new ItemWrapper<>(-1, e.title));
            for (CourseResult.Course cc:e.course_list) {
                cc.title = e.title;
                data.add(new ItemWrapper<>(-1, cc));
            }
        }
        return data;
    }

}
