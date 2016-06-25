package ws.dyt.recyclerviewadapter;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ws.dyt.view.Delegate;
import ws.dyt.view.adapter.SectionMultiAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;
import ws.dyt.recyclerviewadapter.utils.Data;
import ws.dyt.recyclerviewadapter.utils.UnitUtils;


/**
 */
public class SectionSkinGridFragment extends BaseFragment {
    public static final String TAG = "Section";
    public SectionSkinGridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        mSectionInput.setVisibility(View.GONE);
        this.init();
        return rootView;
    }



    SectionMultiAdapter<Data.SkinData> adapter;
    private void init(){
        final List<List<Data.SkinData>> list = Data.generateSection();

        GridLayoutManager llm = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(llm);

//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.drawable.divider_v)));
        recyclerView.addItemDecoration(new SkinItemDecoration(getContext()));

        adapter = new SectionMultiAdapter<Data.SkinData>(getContext(), list, true, false) {
            @Override
            public void convertSectionHeader(BaseViewHolder holder, int group) {
                holder.setText(R.id.tv_text, 0 == group ? "最新皮肤，不容错过" : "热门皮肤推荐");
            }

            @Override
            public void convertSectionData(BaseViewHolder holder, int group, int positionOfGroup, int positionOfData) {
                Log.e(TAG, "convert_data: "+group+" , index: "+positionOfGroup);
                Data.SkinData e = getItem(group, positionOfGroup);
//                SkinData e = getItem(positionOfData);
                holder.setText(R.id.tv_text, e.title).setImageResource(R.id.iv_skin, e.img);
            }

            @Override
            public void convertSectionFooter(BaseViewHolder holder, int group) {
                holder.setText(R.id.tv_text, "更多");
            }

            @Override
            public int getSectionDataItemViewLayout(int group, int position) {
                Log.e(TAG, "layout_data-> group: "+group+" , index: "+position);
                return R.layout.item_skin;
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
    public SectionMultiAdapter<Data.SkinData> getAdapter() {
        return adapter;
    }


    static class SkinItemDecoration extends RecyclerView.ItemDecoration {
        private int left;
        public SkinItemDecoration(Context context) {
            left = UnitUtils.dip2Px(context, 10);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            SectionMultiAdapter<Data.SkinData> adapter = (SectionMultiAdapter<Data.SkinData>) parent.getAdapter();
            int position = parent.getChildAdapterPosition(view);
//            @SectionMultiAdapter.ItemTypeSectionSummaryWhere int type = adapter.getItemTypeByPosition(position);
//            Log.e("LLL", ""+position+" , "+type);
//            if (SectionMultiAdapter.ItemTypeSectionSummary.ITEM_HEADER == type) {
//                return;
//            }

            SectionMultiAdapter.DataItemWrapper wrapper = adapter.getItemInfo(position);
            if (wrapper.itemType == SectionMultiAdapter.ItemTypeSectionSummary.ITEM_DATA) {
                outRect.left = wrapper.positionOfGroup % 2 == 0 ? left : 0;
                outRect.right = left;
                outRect.top = left;
                return;
            }
        }
    }

}
