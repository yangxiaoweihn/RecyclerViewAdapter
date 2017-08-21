package ws.dyt.recyclerviewadapter.rr_nest.item.sub;

import android.support.annotation.DrawableRes;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.rr_nest.item.BaseItemFrament;
import ws.dyt.view.viewholder.BaseViewHolder;

/**
 * Created by yangxiaowei on 17/1/12.
 */

public class OneItemFragment extends BaseItemFrament {

    @Override
    protected void setupView() {
        super.setupView();
        mFab.setVisibility(View.VISIBLE);

        adapter.addAll(this.generateData());
    }

    @Override
    protected int setItemLayoutId() {
        return R.layout.item_nest_two;
    }

    @Override
    protected void onConvert(BaseViewHolder holder, int position) {
        DataHolder dh = (DataHolder) adapter.getItem(position);
        holder.setText(R.id.tv_text, dh.desc).setImageResource(R.id.iv_skin, dh.src);
    }


    private List<DataHolder> generateData() {
        List<DataHolder> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new DataHolder("特特  --  > " +i, R.drawable.xy));
        }
        return list;
    }

    private static class DataHolder{
        public String desc;
        public @DrawableRes int src;

        public DataHolder(String desc, int src) {
            this.desc = desc;
            this.src = src;
        }
    }
}
