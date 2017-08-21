package ws.dyt.recyclerviewadapter.hf;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
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

import ws.dyt.recyclerviewadapter.BaseFragment;
import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.pinned.CourseResult;
import ws.dyt.recyclerviewadapter.utils.FileUtils;
import ws.dyt.view.adapter.ItemWrapper;
import ws.dyt.view.adapter.SuperAdapter;
import ws.dyt.view.adapter.SuperPinnedAdapter;
import ws.dyt.view.adapter.core.base.HeaderFooterAdapter;
import ws.dyt.view.adapter.swipe.MenuItem;
import ws.dyt.view.adapter.swipe.OnItemMenuClickListener;
import ws.dyt.view.adapter.swipe.SwipeLayout;
import ws.dyt.view.viewholder.BaseViewHolder;

/**
 */
public class OnlyHeaderFooterFragment extends BaseFragment {


    public OnlyHeaderFooterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mSectionInput.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    protected void onInputData(int position, List<String> data) {
        if (position < 0) {
            ((SuperAdapter) adapter).addAll(data);
        }else {
            ((SuperAdapter) adapter).addAll(position, data);
        }
    }

    @Override
    protected SuperAdapter<String> getAdapter() {
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        return new SuperAdapter<String>(getContext(), null, R.layout.item_text_c) {
            @Override
            public void convert(BaseViewHolder holder, int position) {
                holder.setText(R.id.tv_text, getItem(position));
            }
        };
    }
}
