package ws.dyt.recyclerviewadapter.pinned;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.utils.FileUtils;
import ws.dyt.view.adapter.ItemWrapper;
import ws.dyt.view.adapter.SuperPinnedAdapter;
import ws.dyt.view.adapter.swipe.MenuItem;
import ws.dyt.view.adapter.swipe.OnItemMenuClickListener;
import ws.dyt.view.adapter.swipe.SwipeLayout;
import ws.dyt.view.viewholder.BaseViewHolder;

/**
 */
public class TestPinnedItemFragment extends Fragment {


    public TestPinnedItemFragment() {
        // Required empty public constructor
    }

    public static TestPinnedItemFragment newInstance(){
        return new TestPinnedItemFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.init();
    }

    private void init() {
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        final SuperPinnedAdapter<ItemWrapper<CourseResult.Course>> adapter = getAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new SuperPinnedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Toast.makeText(getContext(), "item: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemMenuClickListener(new OnItemMenuClickListener() {
            @Override
            public void onMenuClick(SwipeLayout swipeItemView, View itemView, View menuView, int position, int menuId) {
                if (menuId == 01) {
                    swipeItemView.closeMenuItem();
                    Log.d("DEBUG", "--menu: 喜欢 -> position: " + position + " , menuId: " + menuId);
                    Toast.makeText(getContext(), "喜欢", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private SuperPinnedAdapter<ItemWrapper<CourseResult.Course>> getAdapter() {
        return new SuperPinnedAdapter<ItemWrapper<CourseResult.Course>>(getContext(), generate()) {

            @Override
            public int getItemViewLayout(int position) {
                return R.layout.item_pinned_data;
            }

            @Override
            public int getPinnedItemViewLayout() {
                return R.layout.item_pinned;
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

//            @Override
//            public List<MenuItem> onCreateMultiMenuItem(int viewType) {
//                List<MenuItem> mm = new ArrayList<>();
//                mm.add(new MenuItem(R.layout.menu_item_test_like, MenuItem.EdgeTrack.LEFT, 01));
//                return mm;
//            }
//
//            @Override
//            public boolean isCloseOtherItemsWhenThisWillOpen() {
//                return true;
//            }
        };
    }

    private List<ItemWrapper<CourseResult.Course>> generate() {

        String json = FileUtils.readRawFile(getResources(), R.raw.classes_pinned);

        List<CourseResult> results = new Gson().fromJson(json, new TypeToken<ArrayList<CourseResult>>() {
        }.getType());

        List<ItemWrapper<CourseResult.Course>> data = new ArrayList<>();
        for (CourseResult e:results) {
            for (CourseResult.Course cc:e.course_list) {
                cc.title = e.title;
                data.add(new ItemWrapper<CourseResult.Course>(e.title.hashCode(), cc));
            }
        }

        return data;
    }

}
