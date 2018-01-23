package ws.dyt.recyclerviewadapter.normal;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ws.dyt.recyclerviewadapter.swipe.News;
import ws.dyt.recyclerviewadapter.utils.Data;

/**
 * Created by yangxiaowei on 16/6/22.
 *
 * 网格布局，增加header footer演示
 */
public class HeaderFooterGridFragment extends HeaderFooterLineFragment {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {

        return new GridLayoutManager(getContext(), 2);
    }

    @Override
    protected List<News> generateData() {
        return Data.generateNews(getResources()).subList(0, 7);
    }
}
