package ws.dyt.recyclerviewadapter.decoration;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.adapter.viewholder.BaseViewHolder;
import ws.dyt.recyclerviewadapter.base.BaseFragment;
import ws.dyt.recyclerviewadapter.R;
import ws.dyt.adapter.adapter.core.base.HeaderFooterAdapter;

/**
 * Created by yangxiaowei on 16/6/22.
 *
 * 所有的数据展示看起来都是grid
 *
 * 用GridLayoutManager，将分组数据、分组广告数据进行包装转换处理
 *
 * 点击只绑定了部分数据，其他未绑定的数据自行处理
 */
public class DecorationListFragment extends BaseFragment<DataWrapper> {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 2);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        recyclerView.addItemDecoration(new Decoration(getContext()));

        //add one header
        adapter.addHeaderView(new BaseViewHolder(layoutInflater.inflate(R.layout.item_header_1, recyclerView, false))
                .setBackgroundColor(R.id.section_root, Color.argb(128, 50, 100, 50))
                .setTextColor(R.id.tv_text, Color.WHITE)
                .setText(R.id.tv_text, "Header One").itemView
        );
    }

    @Override
    protected HeaderFooterAdapter<DataWrapper> getAdapter() {
        return new DecorationAdapter(getContext(), generate());
    }

    private List<DataWrapper> generate() {
        List<DataWrapper> datas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            //构造广告数据，独占一行
            if (i % 2 == 0) {
                //广告组
                String group = "This is AD : "+i;
                datas.add(new DataWrapper(DataWrapper.DataType.GROUP_TITLE, i, 0, group));

                //广告数据
                Ad ad = new Ad("This is maybe AD : " + i, "");
                datas.add(new DataWrapper(DataWrapper.DataType.AD, i, 0, ad));
            } else {
                //动漫组
                String group = "This is data-group : "+i;
                datas.add(new DataWrapper(DataWrapper.DataType.GROUP_TITLE, i, 0, group));

                //动漫数据，一组4个
                for (int j = 0; j < 4; j++) {
                    Series app = new Series("【卡哇伊】小萝莉动漫··美女好看么- " + i + "," + j, R.drawable.bilili_1, 0);
                    datas.add(new DataWrapper(DataWrapper.DataType.GROUP_DATA, i, j, app));
                }
            }
        }

        //构造一个缺省数据
        datas.remove(4);

        return datas;
    }


}
