package ws.dyt.recyclerviewadapter;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ws.dyt.view.adapter.SuperAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;


/**
 */
public class MultiSimpleItemV2Fragment extends BaseFragment {
    public MultiSimpleItemV2Fragment() {
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
                "AAA", "BBB", "CCC", "DDD", "EEE", "FFF", "GGG", "HHH", "JJJ", "KKK", "LLL", "QQQ", "WWW",
                "EEE", "RRR", "TTT", "YYY", "UUU", "III", "OOO", "PPP", "ZZZ", "XXX", "NNN", "MMM", "###"};
    }

    SuperAdapter adapter;
    private void init(){
        final String[] datas = generate();
        List<String> list = new ArrayList<>(Arrays.asList(datas));

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        adapter = new SuperAdapter<String>(getContext(), list) {
            @Override
            public int getItemViewLayout(int position) {
                int type = position % 3;
                if (type == 0 || type == 2){
                    type = R.layout.item_text_l;
                }else if (type == 1){
                    type = R.layout.item_text_c;
                }
                else {
                    type = R.layout.item_text_r;
                }
                return type;
            }

            @Override
            public void convert(BaseViewHolder holder, int position) {
                holder.setText(R.id.tv_text, position+"  "+getItem(position));
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public SuperAdapter getAdapter() {
        return adapter;
    }
}
