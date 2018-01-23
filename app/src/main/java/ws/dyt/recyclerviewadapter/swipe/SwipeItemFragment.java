package ws.dyt.recyclerviewadapter.swipe;


import android.view.View;
import android.widget.Toast;

import ws.dyt.adapter.adapter.SuperAdapter;
import ws.dyt.adapter.adapter.swipe.OnItemMenuClickListener;
import ws.dyt.adapter.adapter.swipe.SwipeDragHelperDelegate;
import ws.dyt.adapter.adapter.swipe.SwipeLayout;
import ws.dyt.recyclerviewadapter.base.BaseFragment;
import ws.dyt.recyclerviewadapter.utils.Data;

/**
 */
public class SwipeItemFragment extends BaseFragment<News> {


    public SwipeItemFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setUpView() {
        super.setUpView();

        recyclerView.addItemDecoration(new SwipeDivider(getContext()));

        final SuperAdapter<News> adapter = (SuperAdapter) super.adapter;

        adapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Toast.makeText(getContext(), "item: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemMenuClickListener(new OnItemMenuClickListener() {
            @Override
            public void onMenuClick(SwipeLayout swipeItemView, View itemView, View menuView, int position, int menuId) {
                if (menuId == 01) {
                    adapter.remove(position);
                    SwipeDragHelperDelegate.release();
                    Toast.makeText(getContext(), "删除", Toast.LENGTH_SHORT).show();
                } else if (menuId == 02) {
                    swipeItemView.closeMenuItem();
                    Toast.makeText(getContext(), "加关注", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected SuperAdapter<News> getAdapter() {
        return new SwipeAdapter(getContext(), Data.generateNews(getResources()));
    }

    @Override
    public void onDestroy() {
        if (null != adapter) {
            adapter.release();
        }
        super.onDestroy();
    }

}
