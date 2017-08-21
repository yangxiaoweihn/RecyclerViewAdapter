package ws.dyt.recyclerviewadapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ws.dyt.adapter.adapter.core.base.HeaderFooterAdapter;
import ws.dyt.adapter.adapter.deprecated.SectionAdapter;
import ws.dyt.adapter.adapter.section.SectionMultiAdapter;

/**
 * Created by yangxiaowei on 16/6/9.
 */
abstract public class BaseFragment<T> extends Fragment{
    protected LayoutInflater layoutInflater;
    public RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
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
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
//        addListener();
    }


    abstract
    protected HeaderFooterAdapter<T> getAdapter();

    protected RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        return llm;
    }

    public HeaderFooterAdapter<T> adapter;

    private void addListener(){
        adapter = null == adapter ? getAdapter() : adapter;
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

        recyclerView.setAdapter(adapter);
        if (adapter instanceof SectionMultiAdapter) {
            SectionMultiAdapter sectionAdapter = (SectionMultiAdapter) adapter;
            sectionAdapter.setOnItemClickListener(new SectionMultiAdapter.OnItemClickListener() {
                @Override
                public void onClick(int group, int positionOfGroup, int positionOfData, @SectionAdapter.ItemTypeWhere int type) {
                    String a = "";
                    switch (type) {
                        case SectionMultiAdapter.ItemType.ITEM_HEADER: {
                            a = "H-> group: " + group + " , index: " + positionOfGroup;
                            break;
                        }
                        case SectionMultiAdapter.ItemType.ITEM_DATA: {
                            a = "D-> group: " + group + " , index: " + positionOfGroup + " , " + positionOfData;
                            break;
                        }
                        case SectionMultiAdapter.ItemType.ITEM_FOOTER: {
                            a = "F-> group: " + group + " , index: " + positionOfGroup;
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    tost(a);
                }
            });
        }
    }


    private void toast(int position){
        this.tost("" + position);
    }

    public void tost(String des) {
        Toast.makeText(getContext(), des, Toast.LENGTH_SHORT).show();
    }
}
