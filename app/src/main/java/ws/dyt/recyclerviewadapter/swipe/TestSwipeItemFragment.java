package ws.dyt.recyclerviewadapter.swipe;


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
import ws.dyt.view.adapter.SuperAdapter;
import ws.dyt.view.adapter.core.base.HeaderFooterAdapter;
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
        final SuperAdapter<News> adapter = getAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Toast.makeText(getContext(), "item: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemLongClickListener(new HeaderFooterAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, int position) {

            }
        });

        adapter.setOnItemMenuClickListener(new OnItemMenuClickListener() {
            @Override
            public void onMenuClick(SwipeLayout swipeItemView, View itemView, View menuView, int position, int menuId) {
                if (menuId == 01) {
                    swipeItemView.closeMenuItem();
                    Log.d("DEBUG", "--menu: 删除 -> position: " + position + " , menuId: " + menuId);
                    Toast.makeText(getContext(), "删除", Toast.LENGTH_SHORT).show();
                } else if (menuId == 02) {
                    Log.d("DEBUG", "--menu: 关注 -> position: " + position + " , menuId: " + menuId);
                    Toast.makeText(getContext(), "加关注", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private SuperAdapter<News> getAdapter() {
        return new SuperAdapter<News>(getContext(), generate(), R.layout.item_swipe) {
            public List<MenuItem> onCreateMultiMenuItem(int viewType) {
                List<MenuItem> mm = new ArrayList<>();
                mm.add(new MenuItem(R.layout.menu_item_test_delete, MenuItem.EdgeTrack.RIGHT, 01));
                mm.add(new MenuItem(R.layout.menu_item_test_mark, MenuItem.EdgeTrack.RIGHT, 02));
                return mm;
            }

            @Override
            public void convert(BaseViewHolder holder, final int position) {
                final News news = getItem(position);
                holder.setText(R.id.tv_title, news.title)
                .setText(R.id.tv_from, news.from)
                .setText(R.id.tv_time, news.time)
                .setText(R.id.tv_menu_mark, "加关注");

                holder.setOnClickListener(R.id.btn_to, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "点击测试："+position, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public boolean isCloseOtherItemsWhenThisWillOpen() {
                return true;
            }
        };
    }

    private List<News> generate() {
        String json = FileUtils.readRawFile(getResources(), R.raw.news);
        List<News> data = new Gson().fromJson(json, new TypeToken<ArrayList<News>>(){}.getType());
        return data;
    }

}
