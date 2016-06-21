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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ws.dyt.library.Delegate;
import ws.dyt.library.adapter.SectionMultiAdapter;
import ws.dyt.library.adapter.deprecated.SectionAdapter;
import ws.dyt.library.viewholder.BaseViewHolder;
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

    private List<List<SkinData>> generate(){
        List<List<SkinData>> data = new ArrayList<>();
        data.add(new ArrayList(Arrays.asList(
                new SkinData[]{new SkinData("Hamburger", R.drawable.xy), new SkinData("SUPER MARIO", R.drawable.xy)})));
        data.add(
                new ArrayList(Arrays.asList(new SkinData[]{
                        new SkinData("默认皮肤", R.drawable.xy),
                        new SkinData("ios风格", R.drawable.xy),
                        new SkinData("保卫萝卜3", R.drawable.xy),
                        new SkinData("欧洲杯", R.drawable.xy),
                        new SkinData("别字", R.drawable.xy),
                        new SkinData("素白", R.drawable.xy),
                        new SkinData("魔兽", R.drawable.xy),
                        new SkinData("X战警", R.drawable.xy),
                        new SkinData("彩漆怀旧", R.drawable.xy),
                        new SkinData("雪碧-击败炎热", R.drawable.xy),
                        new SkinData("呆萌树懒", R.drawable.xy),
                        new SkinData("江南水乡", R.drawable.xy),
                        new SkinData("四驱车", R.drawable.xy),
                        new SkinData("黄色机甲", R.drawable.xy),
                        new SkinData("阿狸·照亮路的月亮", R.drawable.xy),
                        new SkinData("一拳超人", R.drawable.xy),
                        new SkinData("粉色少女", R.drawable.xy),
                        new SkinData("草莓", R.drawable.xy),
                        new SkinData("少女心事", R.drawable.xy),
                        new SkinData("紫罗兰少女", R.drawable.xy),
                        new SkinData("Crazy Magic", R.drawable.xy),
                        new SkinData("蓝色简约", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy)
                })));
        return data;
    }

    static class SkinData {
        public String title;
        public int img;

        public SkinData(String title, int img) {
            this.title = title;
            this.img = img;
        }
    }

    SectionMultiAdapter<SkinData> adapter;
    private void init(){
        final List<List<SkinData>> list = generate();

        GridLayoutManager llm = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(llm);

//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.drawable.divider_v)));
        recyclerView.addItemDecoration(new SkinItemDecoration(getContext()));

        adapter = new SectionMultiAdapter<SkinData>(getContext(), list, true, false) {
            @Override
            public void convertSectionHeader(BaseViewHolder holder, int group) {
                holder.setText(R.id.tv_text, 0 == group ? "最新皮肤，不容错过" : "热门皮肤推荐");
            }

            @Override
            public void convertSectionData(BaseViewHolder holder, int group, int positionOfGroup, int positionOfData) {
                Log.e(TAG, "convert_data: "+group+" , index: "+positionOfGroup);
                SkinData e = getItem(group, positionOfGroup);
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
    public SectionMultiAdapter<SkinData> getAdapter() {
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

            SectionMultiAdapter<SkinData> adapter = (SectionMultiAdapter<SkinData>) parent.getAdapter();
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
