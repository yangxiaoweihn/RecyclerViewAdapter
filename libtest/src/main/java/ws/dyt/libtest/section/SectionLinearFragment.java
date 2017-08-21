package ws.dyt.recyclerviewadapter.section;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ws.dyt.recyclerviewadapter.BaseFragment;
import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.decoration.DividerItemDecoration;
import ws.dyt.view.adapter.section.SectionMultiAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;


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
        data.add(new ArrayList(Arrays.asList(new String[]{"AAA", "A01", "A02"})));
        data.add(new ArrayList(Arrays.asList(new String[]{"BBB", "CCC", "DDD", "EEE", "E01", "E02", "E03"})));
        data.add(new ArrayList(Arrays.asList(new String[]{"FFF", "GGG"})));
        data.add(new ArrayList(Arrays.asList(new String[]{"HHH"})));
        return data;
    }

    SectionMultiAdapter<String> adapter;
    private void init(){
        final List<List<String>> list = generate();

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.drawable.divider_v)));

        adapter = new SectionMultiAdapter<String>(getContext(), list, true, true) {
            @Override
            public void convertSectionHeader(BaseViewHolder holder, int group) {

            }

            @Override
            public void convertSectionData(BaseViewHolder holder, int group, int positionOfGroup, int positionOfData) {
                holder.setText(R.id.tv_text, getItem(positionOfData));
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
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public SectionMultiAdapter getAdapter() {
        return adapter;
    }
}
