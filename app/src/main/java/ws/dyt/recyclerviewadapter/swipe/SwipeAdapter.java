package ws.dyt.recyclerviewadapter.swipe;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.adapter.adapter.SuperAdapter;
import ws.dyt.adapter.adapter.swipe.MenuItem;
import ws.dyt.adapter.viewholder.BaseViewHolder;
import ws.dyt.recyclerviewadapter.R;

/**
 * Created by yangxiaowei on 2018/1/22.
 */

public class SwipeAdapter extends SuperAdapter<News>{
    public SwipeAdapter(Context context, List<News> data) {
        super(context, data);
    }

    @Override
    public int getItemViewLayout(int position) {
        return R.layout.item_swipe;
    }

    @Override
    public List<MenuItem> onCreateMultiMenuItem(@LayoutRes int viewType) {
        List<MenuItem> mm = new ArrayList<>();
        mm.add(new MenuItem(R.layout.menu_item_test_delete, MenuItem.EdgeTrack.RIGHT, 01));
        mm.add(new MenuItem(R.layout.menu_item_test_mark, MenuItem.EdgeTrack.RIGHT, 02));
        return mm;
    }


    @Override
    public void convert(BaseViewHolder holder, final int position) {
        final News news = getItem(position);
        holder.setText(R.id.tv_title, news.title)
                .setText(R.id.tv_from, news.from)
                .setText(R.id.tv_time, news.time);
        View v = holder.getView(R.id.tv_menu_mark);
        if (null != v) {
            ((TextView) v).setText("加关注");
        }
    }

    @Override
    public boolean isCloseOtherItemsWhenThisWillOpen() {
        return true;
    }
}
