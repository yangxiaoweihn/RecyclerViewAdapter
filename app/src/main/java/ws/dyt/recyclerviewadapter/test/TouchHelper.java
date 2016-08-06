package ws.dyt.recyclerviewadapter.test;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import ws.dyt.recyclerviewadapter.R;
import ws.dyt.view.viewholder.BaseViewHolder;

/**
 * Created by yangxiaowei on 16/7/29.
 */
public class TouchHelper extends ItemTouchHelper.Callback{

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //滑动的时候支持的方向
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //拖拽的时候支持的方向
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

        //必须调用该方法告诉ItemTouchHelper支持的flags
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

            View vv = viewHolder.itemView.findViewById(R.id.iv_img);
            Log.e("GGG", "x: " + dX + " , y: " + dY + " , v-x: " +( Math.abs(dX) / viewHolder.itemView.getWidth()));
            float aa = 100 - 100*Math.abs(dX) / viewHolder.itemView.getWidth();
            if (aa < 0) {
                super.onChildDraw(c, recyclerView, new BaseViewHolder(vv), aa+100, dY, actionState, isCurrentlyActive);
                return;
            }
            super.onChildDraw(c, recyclerView, new BaseViewHolder(vv), dX, dY, actionState, isCurrentlyActive);
        }else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }
}
