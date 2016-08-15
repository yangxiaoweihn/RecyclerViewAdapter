package ws.dyt.recyclerviewadapter;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ws.dyt.view.adapter.SuperAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;
import ws.dyt.recyclerviewadapter.utils.UnitUtils;


/**
 */
public class MultiSimpleItemV2ConvertSingleSimpleGridFragment extends BaseFragment {
    public MultiSimpleItemV2ConvertSingleSimpleGridFragment() {
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

        GridLayoutManager llm = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(llm);

        adapter = new SuperAdapter<String>(getContext(), list, R.layout.item_text_c) {
            @Override
            public int getItemViewLayout(int position) {
                super.getItemViewLayout(position);
                int type = position % 3;
                if (type == 0){
                    type = R.layout.item_text_l;
                }else if (type == 1){
                    type = R.layout.item_text_c;
                }else {
                    type = R.layout.item_text_r;
                }
                return type;
            }

            @Override
            public void convert(BaseViewHolder holder, int position) {
                holder.setText(R.id.tv_text, position+"  "+getItem(position));
            }
        };
        recyclerView.addItemDecoration(new D());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public SuperAdapter getAdapter() {
        return adapter;
    }


    class D extends RecyclerView.ItemDecoration {
        int padding;
        public D() {
            padding = UnitUtils.dip2Px(getContext(), 10);
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            super.getItemOffsets(outRect, itemPosition, parent);
            int aAll = adapter.getSysHeaderViewCount() + adapter.getHeaderViewCount();
            Log.e("GGGG", "all: " + aAll + " , index: " + itemPosition);
            outRect.left = 0;
            outRect.top = 0;
            outRect.bottom = 0;
            outRect.right = 0;
            if (0 != aAll && itemPosition < aAll) {

                return;
            }
            int index = itemPosition - aAll;
            if (index == 0) {
                outRect.set(0, padding, 0, 0);
            }

        }
    }
}
