package ws.dyt.recyclerviewadapter.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import ws.dyt.adapter.adapter.SuperAdapter;
import ws.dyt.recyclerviewadapter.utils.UnitUtils;

/**
 * 这里需要注意添加的header 和 footer
 */
class Decoration extends RecyclerView.ItemDecoration {
    int padding;

    public Decoration(Context context) {
        padding = UnitUtils.dip2Px(context, 10);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemPosition = parent.getChildAdapterPosition(view);
        SuperAdapter<DataWrapper> adapter = (SuperAdapter) parent.getAdapter();
        int aAll = adapter.getSysHeaderViewCount() + adapter.getHeaderViewCount();
        Log.e("DEBUG", "postion: " + itemPosition + " , all: " + aAll);
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

        Log.e("DEBUG", "postion: " + itemPosition + " , all: " + aAll + " , fall: " + fAll + " , dataIndex: " + index);
        DataWrapper e = adapter.getItem(index);


        outRect.left = padding;
        outRect.right = padding;
        if (e.type == DataWrapper.DataType.GROUP_TITLE) {
            outRect.bottom = padding;
            if (index == 0) {
                outRect.top = padding;
            }
        } else if (e.type == DataWrapper.DataType.AD) {
            //ad
            outRect.bottom = padding;
        } else if (e.type == DataWrapper.DataType.GROUP_DATA) {
            //data
            outRect.bottom = padding;
//
            outRect.left = e.index % 2 == 0 ? padding : 0;
        }
    }
}