package ws.dyt.recyclerviewadapter.decoration;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import ws.dyt.adapter.adapter.SuperAdapter;
import ws.dyt.adapter.viewholder.BaseViewHolder;
import ws.dyt.recyclerviewadapter.R;

/**
 * Created by yangxiaowei on 2018/1/22.
 */

public class DecorationAdapter extends SuperAdapter<DataWrapper> {
    public DecorationAdapter(Context context, List data) {
        super(context, data);
    }

    public DecorationAdapter(Context context, List data, int itemLayoutResId) {
        super(context, data, itemLayoutResId);
    }

    @Override
    public int getItemViewLayout(int position) {
        DataWrapper e = getItem(position);
        if (e.type == DataWrapper.DataType.GROUP_TITLE) {
            return R.layout.item_decoration_group;
        } else if (e.type == DataWrapper.DataType.AD) {
            return R.layout.item_decoration_ad;
        }
        return R.layout.item_decoration_data;
    }

    @Override
    public void convert(BaseViewHolder holder, int position) {
        DataWrapper e = getItem(position);
        if (e.type == DataWrapper.DataType.GROUP_TITLE) {
            bindGroupHeader(holder, (String) e.data);
        } else if (e.type == DataWrapper.DataType.AD) {
            //ad
            bindItemAd(holder, (Ad) e.data);
        } else if (e.type == DataWrapper.DataType.GROUP_DATA) {
            //
            bindItemData(holder, (Series) e.data);
        }
    }

    @Override
    public boolean isFullSpanWithItemView(int position) {
        DataWrapper t = getItem(position);
        return t.type != DataWrapper.DataType.GROUP_DATA;
//                return false;
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

                Toast.makeText(mContext, "" + ((Series) o).des, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
