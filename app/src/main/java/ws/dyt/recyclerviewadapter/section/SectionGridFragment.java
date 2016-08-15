package ws.dyt.recyclerviewadapter.section;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ws.dyt.recyclerviewadapter.BaseFragment;
import ws.dyt.recyclerviewadapter.R;
import ws.dyt.view.adapter.decoration.DividerItemDecoration;
import ws.dyt.view.adapter.section.SectionMultiAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;


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
        data.add(new ArrayList(Arrays.asList(new String[]{"AAA", "A01", "A02"})));
        data.add(new ArrayList(Arrays.asList(new String[]{"BBB", "CCC", "DDD", "EEE", "E01", "E02", "E03"})));
        data.add(new ArrayList(Arrays.asList(new String[]{"FFF", "GGG"})));
        data.add(new ArrayList(Arrays.asList(new String[]{"HHH"})));
        return data;
    }

    SectionMultiAdapter<String> adapter;
    private void init(){
        final List<List<String>> list = generate();

        GridLayoutManager llm = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(llm);

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.drawable.divider_v)));

        adapter = new SectionMultiAdapter<String>(getContext(), list, false, false) {
            @Override
            public void convertSectionHeader(BaseViewHolder holder, int group) {
                holder.setText(R.id.tv_text, "组--"+group);
            }

            @Override
            public void convertSectionData(BaseViewHolder holder, int group, int positionOfGroup, int positionOfData) {
                Log.e(TAG, "convert_data: " + group + " , index: " + positionOfGroup + " , positionOfData: "+positionOfData);
//                holder.setText(R.id.tv_text, getItem(group, positionOfGroup));
                holder.setText(R.id.tv_text, getItem(positionOfData));
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

    }

    @Override
    public SectionMultiAdapter<String> getAdapter() {
        return adapter;
    }
}
