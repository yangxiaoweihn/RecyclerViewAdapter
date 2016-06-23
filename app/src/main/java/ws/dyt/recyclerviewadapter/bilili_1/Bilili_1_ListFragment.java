package ws.dyt.recyclerviewadapter.bilili_1;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.library.adapter.MultiAdapter;
import ws.dyt.library.adapter.base.HeaderFooterAdapter;
import ws.dyt.library.viewholder.BaseViewHolder;
import ws.dyt.recyclerviewadapter.BaseFragment;
import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.utils.UnitUtils;
import ws.dyt.recyclerviewadapter.wandoujia.Wrapper;

/**
 * Created by yangxiaowei on 16/6/22.
 */
public class Bilili_1_ListFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        this.init();
        return view;
    }


    MultiAdapter<Wrapper1> adapter;

    private void init() {
        GridLayoutManager llm = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(llm);

        adapter = new MultiAdapter<Wrapper1>(getContext(), generate()) {
            @Override
            public int getItemViewLayout(int position) {
                Wrapper e = getItem(position);
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
            protected boolean isDataItemView(int position) {
                boolean b = super.isDataItemView(position);
                if (b == false) {
                    return b;
                }

                Wrapper t = getItem(position - getSysHeaderViewCount() - getHeaderViewCount());
                return t.type == 2;
            }
        };

        recyclerView.addItemDecoration(new D());
        recyclerView.setAdapter(adapter);
    }


    private void bindGroupHeader(BaseViewHolder holder, String e) {
        holder.setText(R.id.tv_group, e);
    }

    private void bindItemAd(BaseViewHolder holder, Ad e) {

    }

    private void bindItemData(BaseViewHolder holder, Series e) {
        holder.setText(R.id.tv_des, e.des).setText(R.id.tv_count_evaluate, e.count+"");
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


    class Wrapper1<T> extends Wrapper<T> {
        public int group;
        public int index;
        public Wrapper1(int type, T data) {
            super(type, data);
        }

        public Wrapper1(int type, int group, int index, T data) {
            super(type, data);
            this.group = group;
            this.index = index;
        }
    }

    private List<Wrapper1> generate() {
        List<Wrapper1> datas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            //ad
            if (0 != i && i % 2 == 0) {
                String group = "Bilili广告-组 - "+i;
                datas.add(new Wrapper1(0, i, 0, group));

                Ad ad = new Ad("Bilili广告 - " + i, "");
                datas.add(new Wrapper1(1, i, 0, ad));
            } else {
                String group = "Bilili分组-组 - "+i;
                datas.add(new Wrapper1(0, i, 0, group));

                for (int j = 0; j < 4; j++) {
                    Series app = new Series("item-" + i + "," + j, "", 0);
                    datas.add(new Wrapper1(2, i, j, app));
                }
            }
        }

        datas.remove(4);

        return datas;
    }

    class D extends RecyclerView.ItemDecoration {
        int padding;
        public D() {
            padding = UnitUtils.dip2Px(getContext(), 10);
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            super.getItemOffsets(outRect, itemPosition, parent);
            int index = itemPosition - adapter.getSysHeaderViewCount() - adapter.getHeaderViewCount();
            Wrapper1 e = adapter.getItem(index);

            outRect.left = padding;
            outRect.right = padding;

            if (e.type == 1) {
                //ad
                outRect.bottom = padding;
            }else if (e.type == 2) {
                //data
                outRect.bottom = padding;

                outRect.left = e.index % 2 == 0 ? padding : 0;
//                outRect.right = padding;
            }

        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);


        }
    }
}
