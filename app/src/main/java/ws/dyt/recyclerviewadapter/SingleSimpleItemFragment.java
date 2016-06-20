package ws.dyt.recyclerviewadapter;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ws.dyt.library.adapter.base.BaseHFAdapter;
import ws.dyt.library.adapter.deprecated.SingleAdapter;
import ws.dyt.library.viewholder.BaseViewHolder;


/**
 */
public class SingleSimpleItemFragment extends BaseFragment {
    public static final String TAG = "SSIF";

    public SingleSimpleItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        this.init();
        return rootView;
    }

    private String[] generate(){
        return new String[]{
                "AAA", "BBB", "CCC", "DDD"/*, "EEE", "FFF", "GGG", "HHH", "JJJ", "KKK", "LLL", "QQQ", "WWW",
                "EEE", "RRR", "TTT", "YYY", "UUU", "III", "OOO", "PPP", "ZZZ", "XXX", "NNN", "MMM", "###"*/};
    }

    SingleAdapter adapter;
    private void init(){
        final String[] datas = generate();
        List<String> list = new ArrayList<>(Arrays.asList(datas));

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        adapter = new SingleAdapter<String>(getContext(), list, R.layout.item_text_l) {
            @Override
            public void convert(BaseViewHolder holder, int position) {
                holder.setText(R.id.tv_text, position+"  "+getItem(position));
            }
        };

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseHFAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Log.e(TAG, "viewType: " + " , position: " + position);
            }
        });

        adapter.setOnItemLongClickListener(new BaseHFAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, int position) {
                String ss = (String) adapter.getItem(position);
                adapter.replace(position, ss+" -> replace");
            }
        });
    }

    @Override
    protected void onInputData(int position, List<String> data) {
//        if (data.isEmpty()) {
//            return;
//        }
//
//        if (data.size() == 1) {
//            if (position < 0) {
//                adapter.add(data.get(0));
//            }else {
//                adapter.add(position, data.get(0));
//            }
//        }else {
//            if (position < 0) {
//                adapter.addAll(data);
//            }else {
//                adapter.addAll(position, data);
//            }
//        }
    }

    @Override
    public SingleAdapter getAdapter() {
        return adapter;
    }
}
