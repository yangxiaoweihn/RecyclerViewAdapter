package ws.dyt.recyclerviewadapter.bilili_1;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.recyclerviewadapter.BaseFragment;
import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.utils.UnitUtils;
import ws.dyt.view.adapter.ItemWrapper;
import ws.dyt.view.adapter.SuperAdapter;
import ws.dyt.view.adapter.core.base.HeaderFooterAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;

/**
 * Created by yangxiaowei on 16/6/22.
 *
 * 一组Grid组数据-》一则广告-》一组Linear组数据
 *
 * 前面有几个数据是grid展示，后面数据展示是线性的
 *
 * 所以总体用GridLayoutManager，将分组数据、分组广告数据进行包装转换处理
 *
 * 点击只绑定了部分数据，其他未绑定的数据自行处理
 */
public class Bilili_2_ListFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        this.init();
        return view;
    }


    SuperAdapter<Wrapper1> adapter;

    private void init() {
        GridLayoutManager llm = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(llm);

        adapter = new SuperAdapter<Wrapper1>(getContext(), generate()) {
            @Override
            public int getItemViewLayout(int position) {
                Wrapper1 e = getItem(position);
                if (e.type == 0) {
                    return R.layout.bilili_item_group;
                } else if (e.type == 1) {
                    return R.layout.bilili_item_ad;
                }
                return R.layout.bilili_item_data;
            }

            @Override
            public void convert(BaseViewHolder holder, int position) {
                Wrapper1 e = getItem(position);
                if (e.type == 0) {
                    bindGroupHeader(holder, (String) e.data);
                } else if (e.type == 1) {
                    //ad
                    bindItemAd(holder, (Ad) e.data);
                } else if (e.type == 2) {
                    //
                    bindItemData(holder, (Series) e.data);
                }
            }

            @Override
            protected boolean isFullSpanWithItemView(int position) {
                Wrapper1 t = getItem(position);
                //只有动漫类型并且不是第二组（动漫总共两组）的以两列展示
                return t.type == 2 && t.group != 2;
            }
        };

        recyclerView.addItemDecoration(new D());
        recyclerView.setAdapter(adapter);
    }


    private void bindGroupHeader(BaseViewHolder holder, String e) {
        holder.setText(R.id.tv_group, e);
    }

    private void bindItemAd(BaseViewHolder holder, Ad e) {
        holder.setText(R.id.tv_des, e.title).setImageResource(R.id.iv_img, R.drawable.bilili_ad_3);
    }

    private void bindItemData(BaseViewHolder holder, Series e) {
        holder
                .setText(R.id.tv_des, e.des)
                .setText(R.id.tv_count_evaluate, e.count+100+"")
                .setText(R.id.tv_count_view, "2.8万")
                .setImageResource(R.id.iv_img, e.imgUrl);
        holder.itemView.setTag(e);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object o = v.getTag();
                if (null == o || !(o instanceof Series)) {
                    return;
                }

                tost("" + ((Series) o).des);
            }
        });
    }


    private <V extends View> V getView(int id, View root) {
        return (V) root.findViewById(id);
    }

    @Override
    protected HeaderFooterAdapter getAdapter() {
        return adapter;
    }


    class Wrapper1<T> extends ItemWrapper<T> {
        public int group;
        public int index;
        public Wrapper1(int type, T data) {
            super(type, data);
        }

        /**
         * @param type 0：组  1：广告  2：动漫
         * @param group
         * @param index
         * @param data
         */
        public Wrapper1(int type, int group, int index, T data) {
            super(type, data);
            this.group = group;
            this.index = index;
        }
    }

    private List<Wrapper1> generate() {
        List<Wrapper1> datas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (0 == i) {
                //动漫组
                String group = "Bilili分组 - "+i;
                datas.add(new Wrapper1(0, i, 0, group));

                //动漫数据，一组6个
                for (int j = 0; j < 6; j++) {
                    Log.e("HHH", "j: "+j);
                    Series app = new Series("【卡哇伊】小萝莉动漫··美女好看么- " + i + "," + j, R.drawable.bilili_1, 0);
                    datas.add(new Wrapper1(2, i, j, app));
                }
            }else if (i == 1) {
                //广告组
                String group = "周末放映室 - "+i;
                datas.add(new Wrapper1(0, i, 0, group));

                //广告数据
                Ad ad = new Ad("Bilili广告 - " + i, "");
                datas.add(new Wrapper1(1, i, 0, ad));
            }else {
                //动漫组
                String group = "Bilili分组 - "+i;
                datas.add(new Wrapper1(0, i, 0, group));

                //动漫数据，一组50个
                for (int j = 0; j < 51; j++) {
                    Series app = new Series("【卡哇伊】小萝莉动漫··美女好看么- " + i + "," + j, R.drawable.bilili_3, 0);
                    datas.add(new Wrapper1(2, i, j, app));
                }
            }
        }

        return datas;
    }

    class D extends RecyclerView.ItemDecoration {
        int padding;
        public D() {
            padding = UnitUtils.dip2Px(getContext(), 10);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int itemPosition = parent.getChildAdapterPosition(view);
            int aAll = adapter.getSysHeaderViewCount() + adapter.getHeaderViewCount();
            if (0 != aAll && itemPosition < aAll) {

                return;
            }

            int fAll = adapter.getSysFooterViewCount() + adapter.getFooterViewCount();
            if (0 != fAll && itemPosition - (aAll + adapter.getDataSectionItemCount()) >= 0) {
                return;
            }

            int index = itemPosition - aAll;

            Wrapper1 e = adapter.getItem(index);

            outRect.left = padding;
            outRect.right = padding;

            if (e.type == 1) {
                //ad
                outRect.bottom = padding;
            }else if (e.type == 2) {
                //data
                outRect.bottom = padding;

                if (e.group == 0) {
                    outRect.left = e.index % 2 == 0 ? padding : 0;
                }else {
                    outRect.left = padding;
                }
            }else if (e.type == 0) {
                outRect.bottom = padding;
                if (index == 0) {
                    outRect.top = padding;
                }
            }

        }
    }
}
