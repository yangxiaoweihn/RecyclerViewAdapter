package ws.dyt.recyclerviewadapter;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ws.dyt.library.Delegate;
import ws.dyt.library.adapter.SectionAdapter;
import ws.dyt.library.viewholder.BaseViewHolder;


/**
 */
public class SectionGridFragment extends BaseFragment {
    public static final String TAG = "Section";
    public SectionGridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        mSectionInput.setVisibility(View.GONE);
        this.init();
        return rootView;
    }

    private List<List<String>> generate(){
        List<List<String>> data = new ArrayList<>();
        data.add(new ArrayList(Arrays.asList(new String[]{"AAA"})));
        data.add(new ArrayList(Arrays.asList(new String[]{"BBB", "CCC", "DDD", "EEE"})));
        data.add(new ArrayList(Arrays.asList(new String[]{"FFF", "GGG"})));
//        return new String[]{
//                "AAA", "BBB", "CCC", "DDD", "EEE", "FFF", "GGG", "HHH", "JJJ", "KKK", "LLL", "QQQ", "WWW",
//                "EEE", "RRR", "TTT", "YYY", "UUU", "III", "OOO", "PPP", "ZZZ", "XXX", "NNN", "MMM", "###"};
        return data;
    }

    SectionAdapter adapter;
    private void init(){
        final List<List<String>> list = generate();

        GridLayoutManager llm = new GridLayoutManager(getContext(), 3);
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        adapter = new SectionAdapter<String>(getContext(), list, 0) {
            @Override
            public void convertSectionHeader(BaseViewHolder holder, int group) {
                holder.setText(R.id.tv_text, "组--"+group);
            }

            @Override
            public void convertSectionData(BaseViewHolder holder, int group, int position) {
                Log.e(TAG, "convert_data: "+group+" , index: "+position);
                holder.setText(R.id.tv_text, getItem(group, position));
            }

            @Override
            public void convertSectionData(BaseViewHolder holder, int position) {
//                holder.setText(R.id.tv_text, getItem(position));
            }

            @Override
            public void convertSectionFooter(BaseViewHolder holder, int group) {
                holder.setText(R.id.tv_text, "更多");
            }

            @Override
            public int getSectionDataItemViewLayout(int group, int position) {
                Log.e(TAG, "layout_data-> group: "+group+" , index: "+position);
                return R.layout.item_section_data;
            }

            @Override
            public int getSectionHeaderItemViewLayout(int group) {
                Log.e(TAG, "layout_header-> group: "+group);
                return R.layout.item_section_header;
            }

            @Override
            public int getSectionFooterItemViewLayout(int group) {
                Log.e(TAG, "layout_footer-> group: "+group);
                return R.layout.item_section_footer;
            }
        };
        recyclerView.setAdapter(adapter);

        Delegate delegate = new Delegate(recyclerView, layoutInflater.inflate(R.layout.section, null, false));
    }

    @Override
    public SectionAdapter getAdapter() {
        return adapter;
    }
}
