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

import ws.dyt.library.adapter.SectionAdapter;
import ws.dyt.library.viewholder.BaseViewHolder;


/**
 */
public class SectionLinearFragment extends BaseFragment {
    public static final String TAG = "Section";
    public SectionLinearFragment() {
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

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        adapter = new SectionAdapter<String>(getContext(), list, 0) {
            @Override
            public void convertSectionHeader(BaseViewHolder holder, int group) {

            }

            @Override
            public void convertSectionData(BaseViewHolder holder, int group, int position) {
//                for (int i = 0; i < list.size(); i++) {
//                    List<String> d = list.get(i);
//                    for (int j = 0; j < d.size(); j++) {
//                        if (group == i && j == position) {
//                            holder.setText(R.id.tv_text, d.get(j));
//                            return;
//                        }
//                    }
//                }
            }

            @Override
            public void convertSectionData(BaseViewHolder holder, int position) {
                holder.setText(R.id.tv_text, getItem(position));
            }

            @Override
            public void convertSectionFooter(BaseViewHolder holder, int group) {

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
            //            @Override
//            public void convert(BaseViewHolder holder, int position) {
//                holder.setText(R.id.tv_text, position+"  "+getItem(position));
//            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public SectionAdapter getAdapter() {
        return adapter;
    }
}
