package ws.dyt.recyclerviewadapter.test;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.recyclerviewadapter.R;
import ws.dyt.view.adapter.MultiAdapter;
import ws.dyt.view.adapter.base.HeaderFooterAdapter;
import ws.dyt.view.adapter.swipe.MenuItem;
import ws.dyt.view.adapter.swipe.OnItemMenuClickListener;
import ws.dyt.view.adapter.swipe.SwipeLayout;
import ws.dyt.view.viewholder.BaseViewHolder;

/**
 */
public class TestSwipeItemFragment extends Fragment {


    public TestSwipeItemFragment() {
        // Required empty public constructor
    }

    public static TestSwipeItemFragment newInstance(){
        return new TestSwipeItemFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recyclerview, container, false);
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
        final MultiAdapter<String> adapter = getAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new HeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Toast.makeText(getContext(), "index: "+position, Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemMenuClickListener(new OnItemMenuClickListener() {
            @Override
            public void onMenuClick(SwipeLayout swipeItemView, View itemView, View menuView, int position, int menuId) {
                if (menuId == 01) {
                    swipeItemView.closeMenuItem();
                    Log.d("DEBUG", "--menu: 删除 -> position: " + position + " , menuId: " + menuId);
                } else if (menuId == 02) {
                    Log.d("DEBUG", "--menu: 关注 -> position: " + position + " , menuId: " + menuId);

                }
            }
        });
    }

    private MultiAdapter<String> getAdapter() {
        return new MultiAdapter<String>(getContext(), generate(), R.layout.item_main) {
            public List<MenuItem> onCreateMultiMenuItem(int viewType) {
                List<MenuItem> mm = new ArrayList<>();
                mm.add(new MenuItem(R.layout.menu_item_test_delete, MenuItem.EdgeTrack.LEFT, 01));
                mm.add(new MenuItem(R.layout.menu_item_test_mark, MenuItem.EdgeTrack.LEFT, 02));
                return mm;
            }

            @Override
            public void convert(BaseViewHolder holder, int position) {
                holder.setText(R.id.tv_text, getItem(position));
                holder.setText(R.id.tv_menu_mark, (position % 2 == 0 ? "已关注" : "未关注"));
            }

        };
    }

    private List<String> generate() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add("关注状态 -> "+(i % 2 == 0 ? "已关注" : "未关注"));
        }
        return data;
    }

}
