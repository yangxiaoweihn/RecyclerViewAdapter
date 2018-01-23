package ws.dyt.recyclerviewadapter.swipe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ws.dyt.recyclerviewadapter.utils.UnitUtils;

class SwipeDivider extends RecyclerView.ItemDecoration {
    int dividerH = 0;

    Rect rect;
    Paint paint;

    public SwipeDivider(Context context) {
        context = context.getApplicationContext();
        dividerH = UnitUtils.dip2Px(context, 5);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#e1e1e1"));
        rect = new Rect();

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        int cc = parent.getChildCount();
        for (int i = 0; i < cc; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + dividerH;

            rect.set(left + child.getPaddingLeft(), top, right - child.getPaddingRight(), bottom);
            c.drawRect(rect, paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerH;
    }
}