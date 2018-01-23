package ws.dyt.recyclerviewadapter.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;

import ws.dyt.adapter.adapter.core.base.HeaderFooterAdapter;
import ws.dyt.recyclerviewadapter.MyApplication;
import ws.dyt.recyclerviewadapter.R;

/**
 * Created by yangxiaowei on 16/6/9.
 */
abstract public class BaseFragment<T> extends Fragment{
    protected LayoutInflater layoutInflater;
    protected RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        View rootView = inflater.inflate(this.setContentViewId(), container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(this.getLayoutManager());
        recyclerView.setAdapter(adapter = this.getAdapter());

        adapter.setOnItemClickListener(new HeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                toast(position);
            }
        });

        adapter.setOnItemLongClickListener(new HeaderFooterAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, int position) {
                toast(position);
            }
        });

        this.setUpView();
        return rootView;
    }

    protected @LayoutRes int setContentViewId() {
        return R.layout.fragment_recycler_view;
    }

    protected void setUpView() {}

    abstract
    protected HeaderFooterAdapter<T> getAdapter();

    protected RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        return llm;
    }

    protected HeaderFooterAdapter<T> adapter;

    private void toast(int position){
        this.toast("" + position);
    }

    public void toast(String des) {
        Toast.makeText(getContext(), des, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        if(refWatcher != null) {

            refWatcher.watch(this);
        }
    }
}
