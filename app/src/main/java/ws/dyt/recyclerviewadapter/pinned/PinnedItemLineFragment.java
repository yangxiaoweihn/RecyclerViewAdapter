package ws.dyt.recyclerviewadapter.pinned;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.adapter.adapter.ItemWrapper;
import ws.dyt.adapter.adapter.SuperPinnedAdapter;
import ws.dyt.adapter.adapter.swipe.MenuItem;
import ws.dyt.adapter.adapter.swipe.OnItemMenuClickListener;
import ws.dyt.adapter.adapter.swipe.SwipeLayout;
import ws.dyt.adapter.viewholder.BaseViewHolder;
import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.base.BaseFragment;
import ws.dyt.recyclerviewadapter.utils.Data;

/**
 *
 * 线性布局的粘性头部测试
 */
public class PinnedItemLineFragment extends BaseFragment<ItemWrapper<CourseResult.Course>> {


    public PinnedItemLineFragment() {
        // Required empty public constructor
    }

    @Override
    protected int setContentViewId() {
        return R.layout.recyclerview;
    }

    @Override
    protected void setUpView() {
        super.setUpView();

        final SuperPinnedAdapter<ItemWrapper<CourseResult.Course>> adapter = (SuperPinnedAdapter<ItemWrapper<CourseResult.Course>>) super.adapter;
        adapter.setOnItemClickListener(new SuperPinnedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Toast.makeText(getContext(), "item: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemMenuClickListener(new OnItemMenuClickListener() {
            @Override
            public void onMenuClick(SwipeLayout swipeItemView, View itemView, View menuView, int position, int menuId) {
                if (menuId == 01 || menuId == 02) {
                    swipeItemView.closeMenuItem();
                    Log.d("DEBUG", "--menu: 喜欢 -> position: " + position + " , menuId: " + menuId);
                    Toast.makeText(getContext(), "喜欢", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected SuperPinnedAdapter<ItemWrapper<CourseResult.Course>> getAdapter() {
        return new StickyLineAdapter(getContext(), generate());
    }

    public static class StickyLineAdapter extends SuperPinnedAdapter<ItemWrapper<CourseResult.Course>> {
        public StickyLineAdapter(Context context, List<ItemWrapper<CourseResult.Course>> data) {
            super(context, data);
        }

        @Override
        public int getItemViewLayout(int position) {
            return R.layout.item_course_detail_line;
        }

        @Override
        public int getPinnedItemViewLayout() {
            return R.layout.item_course_group;
        }

        @Override
        public void convert(BaseViewHolder holder, int position) {
            CourseResult.Course e = getItem(position).data;
            holder.setText(R.id.tv_name, e.name)
                    .setText(R.id.tv_length, e.length);
        }

        @Override
        public void convertPinnedHolder(BaseViewHolder holder, int position, int type) {
            holder.setText(R.id.tv_text_pinned, getItem(position).data.title);
        }

        @Override
        public List<MenuItem> onCreateMultiMenuItem(@LayoutRes int viewType) {
            List<MenuItem> mm = new ArrayList<>();
            mm.add(new MenuItem(R.layout.menu_item_test_like, MenuItem.EdgeTrack.LEFT, 01));
            mm.add(new MenuItem(R.layout.menu_item_test_like, MenuItem.EdgeTrack.RIGHT, 02));
            return mm;
        }

        @Override
        public boolean isCloseOtherItemsWhenThisWillOpen() {
            return true;
        }
    }

    protected List<ItemWrapper<CourseResult.Course>> generate() {

        List<CourseResult> results = Data.generateCourses(getResources());

        List<ItemWrapper<CourseResult.Course>> data = new ArrayList<>();
        for (CourseResult e:results) {
            for (CourseResult.Course cc:e.course_list) {
                cc.title = e.title;
                data.add(new ItemWrapper<>(e.title.hashCode(), cc));
            }
        }

        return data;
    }

}
