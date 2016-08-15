package ws.dyt.view.adapter.pinned;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by yangxiaowei on 16/8/8.
 */
public class PinnedLayout extends LinearLayout{
    public PinnedLayout(Context context) {
        this(context, null);
    }

    public PinnedLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinnedLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        this.setOrientation(VERTICAL);
    }

    public View itemView;
    public View pinnedView;

    public void setUpView(ViewGroup viewGroup, View itemView, View pinnedView) {
        if (null != pinnedView) {
            this.addView(pinnedView);
        }
        this.addView(itemView);
        this.itemView = itemView;
        this.pinnedView = pinnedView;
    }

}
