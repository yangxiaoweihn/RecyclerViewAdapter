package ws.dyt.recyclerviewadapter.normal;

import android.graphics.Color;
import android.view.View;

import java.util.List;

import ws.dyt.adapter.adapter.SuperAdapter;
import ws.dyt.adapter.adapter.core.base.HeaderFooterAdapter;
import ws.dyt.adapter.viewholder.BaseViewHolder;
import ws.dyt.recyclerviewadapter.base.BaseFragment;
import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.swipe.News;
import ws.dyt.recyclerviewadapter.utils.Data;

/**
 * Created by yangxiaowei on 16/6/22.
 *
 * 线性布局，增加header footer演示
 */
public class HeaderFooterLineFragment extends BaseFragment<News> {


    @Override
    protected void setUpView() {
        super.setUpView();

        //add one header
        adapter.addHeaderView(new BaseViewHolder(layoutInflater.inflate(R.layout.item_header_1, recyclerView, false))
                .setBackgroundColor(R.id.section_root, Color.argb(128, 100, 100, 100))
                .setText(R.id.tv_text, "Header One").itemView
        );

        //add an other one header
        final BaseViewHolder vh = new BaseViewHolder(layoutInflater.inflate(R.layout.item_header_1, recyclerView, false));
        vh
                .setBackgroundColor(R.id.section_root, Color.argb(128, 100, 200, 200))
                .setText(R.id.tv_text, "Header Two")
                .setOnClickListener(R.id.section_root, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //remove self
                        adapter.removeHeaderView(vh.itemView);
                    }
                });
        adapter.addHeaderView(vh.itemView);


        //add one footer
        adapter.addFooterView(new BaseViewHolder(layoutInflater.inflate(R.layout.item_footer_1, recyclerView, false))
                .setBackgroundColor(R.id.section_root, Color.argb(128, 100, 200, 100))
                .setText(R.id.tv_text, "I am the Footer")
                .setTextColor(R.id.tv_text, Color.WHITE).itemView
        );
    }

    @Override
    protected HeaderFooterAdapter<News> getAdapter() {
        return new SuperAdapter<News>(getContext(), this.generateData(), R.layout.item_swipe) {
            @Override
            public void convert(BaseViewHolder holder, int position) {
                News e = getItem(position);
                holder.setText(R.id.tv_title, e.title)
                        .setText(R.id.tv_from, e.from)
                        .setText(R.id.tv_time, e.time)
                        .setVisibility(R.id.btn_to, View.GONE)
                ;
            }
        };
    }

    protected List<News> generateData() {
        return Data.generateNews(getResources()).subList(0, 5);
    }

}
