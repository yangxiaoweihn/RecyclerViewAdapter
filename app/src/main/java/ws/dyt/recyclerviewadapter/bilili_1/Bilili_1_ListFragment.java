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
import ws.dyt.adapter.adapter.ItemWrapper;
import ws.dyt.adapter.adapter.SuperAdapter;
import ws.dyt.adapter.adapter.core.base.HeaderFooterAdapter;
import ws.dyt.adapter.viewholder.BaseViewHolder;

/**
 * Created by yangxiaowei on 16/6/22.
 *
 * 所有的数据展示看起来都是grid
 *
 * 用GridLayoutManager，将分组数据、分组广告数据进行包装转换处理
 *
 * 点击只绑定了部分数据，其他未绑定的数据自行处理
 */
public class Bilili_1_ListFragment extends BaseFragment<Bilili_1_ListFragment.Wrapper1> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        this.init();
        return view;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 2);
    }

    private void init() {

        recyclerView.addItemDecoration(new D());
    }


    private void bindGroupHeader(BaseViewHolder holder, String e) {
        holder.setText(R.id.tv_group, e);
    }

    private void bindItemAd(BaseViewHolder holder, Ad e) {
        holder.setText(R.id.tv_des, e.title);
    }

    private void bindItemData(BaseViewHolder holder, Series e) {
        holder
                .setText(R.id.tv_des, e.des)
                .setText(R.id.tv_count_evaluate, e.count+100+"")
                .setText(R.id.tv_count_view, "2.8万");
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
    protected HeaderFooterAdapter<Wrapper1> getAdapter() {
        return adapter = new SuperAdapter<Wrapper1>(getContext(), generate()) {
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
            public boolean isFullSpanWithItemView(int position) {
                Wrapper1 t = getItem(position);
                return t.type != 2;
//                return false;
            }
        };
    }


    static class Wrapper1<T> extends ItemWrapper<T> {
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

        @Override
        public String toString() {
            return "Wrapper1{" +
                    "type= " + type +
                    " , group= " + group +
                    " , index= " + index +
                    '}';
        }
    }

    private List<Wrapper1> generate() {
        List<Wrapper1> datas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            //构造广告数据，独占一行
            if (0 != i && i % 2 == 0) {
                //广告组
                String group = "周末放映室 - "+i;
                datas.add(new Wrapper1(0, i, 0, group));

                //广告数据
                Ad ad = new Ad("Bilili广告 - " + i, "");
                datas.add(new Wrapper1(1, i, 0, ad));
            } else {
                //动漫组
                String group = "Bilili分组 - "+i;
                datas.add(new Wrapper1(0, i, 0, group));

                //动漫数据，一组4个
                for (int j = 0; j < 4; j++) {
                    Series app = new Series("【卡哇伊】小萝莉动漫··美女好看么- " + i + "," + j, R.drawable.bilili_1, 0);
                    datas.add(new Wrapper1(2, i, j, app));
                }
            }
        }

        //构造一个缺省数据
        datas.remove(4);

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
            Log.e("DEBUG", "postion: "+itemPosition+" , all: "+aAll);
            if (itemPosition < 0) {
                return;
            }
            //header
            if (0 != aAll && itemPosition < aAll) {
                Log.e("DEBUG", "---");
                return;
            }

            //footer
            int fAll = adapter.getSysFooterViewCount() + adapter.getFooterViewCount();
            if (0 != fAll && itemPosition - (aAll + adapter.getDataSectionItemCount()) >= 0) {
                return;
            }


            int index = itemPosition - aAll;

            Log.e("DEBUG", "postion: "+itemPosition+" , all: "+aAll+" , fall: "+fAll+" , dataIndex: "+index);
            Wrapper1 e = adapter.getItem(index);


            outRect.left = padding;
            outRect.right = padding;
            if (e.type == 0) {
                outRect.bottom = padding;
                if (index == 0) {
                    outRect.top = padding;
                }
            }else if (e.type == 1) {
                //ad
                outRect.bottom = padding;
            }else if (e.type == 2) {
                //data
                outRect.bottom = padding;
//
                outRect.left = e.index % 2 == 0 ? padding : 0;
            }
        }
    }
}
