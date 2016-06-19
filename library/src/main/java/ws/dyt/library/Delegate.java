package ws.dyt.library;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yangxiaowei on 16/6/14.
 */
public class Delegate {
    private RecyclerView recyclerView;
    private View view;
    public Delegate(RecyclerView recyclerView, final View view) {
        this.recyclerView = recyclerView;
        this.view = view;



        final GridLayoutManager glm = (GridLayoutManager) recyclerView.getLayoutManager();



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View view1 = recyclerView.getChildAt(0);
                int a = 255/2+(dy*10);
                if (a <= 0) {
                    a = 0;
                }else if (a >= 255) {
                    a = 255;
                }
//                view1.setBackgroundColor(Color.argb(a, 255, 0, 0));





                int position = glm.findFirstVisibleItemPosition();
                Log.e("DEBUG", "--  " + dx + " , " + dy + " , firstVisiblePosition: " + position);

                View view2 = glm.findViewByPosition(position);
//                view2.scrollBy(dx, -dy);

//                view2.getGlobalVisibleRect(new Rect());

            }
        });

//        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//            }
//        });

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }



}
