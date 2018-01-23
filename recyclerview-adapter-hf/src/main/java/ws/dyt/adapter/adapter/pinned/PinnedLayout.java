package ws.dyt.adapter.adapter.pinned;

import android.content.Context;
import android.util.AttributeSet;
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
        super(context, attrs);
        this.init();
    }

    public PinnedLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(lp);
        this.setOrientation(VERTICAL);
    }

    public View originalItemView;
    public View stickyView;

    @Deprecated
    public void setUpView(ViewGroup viewGroup, View originalItemView, View stickyView) {
        if (null != stickyView) {
            this.addView(stickyView);
        }
        this.addView(originalItemView);
        this.originalItemView = originalItemView;
        this.stickyView = stickyView;
    }

    public PinnedLayout join(View originalItemView, View stickyView) {
        this.setUpView(null, originalItemView, stickyView);
        return this;
    }
}
