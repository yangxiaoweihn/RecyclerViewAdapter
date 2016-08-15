package ws.dyt.view.adapter.swipe;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiaowei on 16/8/1.
 *
 * 用来承载菜单视图和客户端设置的item视图
 */
public class SwipeLayout extends FrameLayout implements ICloseMenus{
    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private ViewDragHelper helper;
    private SwipeDragHelperDelegate delegate;
    private void init() {
    }

    private ViewGroup viewGroup;

    private View itemView;
    private int leftMenuWidth;
    private int rightMenuWidth;
    private List<Pair<View, MenuItem>> leftMenus;
    private List<Pair<View, MenuItem>> rightMenus;

    /**
     *
     * @param viewGroup inflate viw
     * @param itemView
     * @param menuItems
     */
    public void setUpView(ViewGroup viewGroup, View itemView, List<MenuItem> menuItems) {
        this.viewGroup = viewGroup;
        this.itemView = itemView;

        if (null == menuItems || menuItems.isEmpty()) {
            return;
        }


        //筛选左右菜单
        List<MenuItem>[] menu = this.filterLeftAndRightMenu(menuItems, null, null);
        List<MenuItem> left = menu[0];
        List<MenuItem> right = menu[1];

        //添加左菜单(如果菜单项为多个的话，菜单外层需要嵌套一层线性布局)
        if (null != left && !left.isEmpty()) {
            this.EdgeTracking = MenuItem.EdgeTrack.LEFT;

            if (left.size() == 1) {
                this.setUpLeftMenuView(left.get(0), null);
            }else {
                LinearLayout layout = this.getMultiHorizonMenuLayout();
                this.addView(layout, getMenuLayout(MenuItem.EdgeTrack.LEFT));

                for (MenuItem e:left) {
                    this.setUpLeftMenuView(e, layout);
                }
            }
        }

        //添加右菜单(如果菜单项为多个的话，菜单外层需要嵌套一层线性布局)
        if (null != right && !right.isEmpty()) {
            this.EdgeTracking = MenuItem.EdgeTrack.RIGHT;

            if (right.size() == 1) {
                this.setUpRightMenuView(right.get(0), null);
            }else {
                LinearLayout layout = this.getMultiHorizonMenuLayout();
                this.addView(layout, getMenuLayout(MenuItem.EdgeTrack.RIGHT));

                for (MenuItem e:right) {
                    this.setUpRightMenuView(e, layout);
                }
            }
        }

        if (null != itemView) {
            this.addView(itemView);
        }

        delegate = new SwipeDragHelperDelegate(this);
        this.helper = ViewDragHelper.create(this, 1.0f, delegate);
        delegate.init(helper);

        if (this.EdgeTracking == MenuItem.EdgeTrack.LEFT) {
            helper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);

        }else if (this.EdgeTracking == MenuItem.EdgeTrack.RIGHT) {
            helper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);

        }
    }

    /**
     * 筛选左右菜单
     * @param menuItems
     * @param left
     * @param right
     * @return [0]: left menu  [1]: right menu
     */
    private List<MenuItem>[] filterLeftAndRightMenu(List<MenuItem> menuItems, List<MenuItem> left, List<MenuItem> right) {
        for (MenuItem e:menuItems) {
            if (null == e) {
                continue;
            }

            if (e.getEdgeTrack() == MenuItem.EdgeTrack.LEFT) {
                if (null == left) {
                    left = new ArrayList<>();
                }
                left.add(e);
            }else if (e.getEdgeTrack() == MenuItem.EdgeTrack.RIGHT) {
                if (null == right) {
                    right = new ArrayList<>();
                }
                right.add(e);
            }
        }
        return new List[]{left, right};
    }

    private void setUpLeftMenuView(MenuItem item, LinearLayout parent) {
        if (null == leftMenus) {
            this.leftMenus = new ArrayList<>();
        }
        View leftMenuView = LayoutInflater.from(getContext()).inflate(item.getMenuLayoutId(), viewGroup, false);

        if (null == parent) {
            this.addView(leftMenuView, getMenuLayout(MenuItem.EdgeTrack.LEFT));
        }else {
            parent.addView(leftMenuView);
        }

        this.leftMenus.add(new Pair(leftMenuView, item));
    }

    private void setUpRightMenuView(MenuItem item, LinearLayout parent) {
        if (null == rightMenus) {
            this.rightMenus = new ArrayList<>();
        }

        View rightMenuView = LayoutInflater.from(getContext()).inflate(item.getMenuLayoutId(), viewGroup, false);

        if (null == parent) {
            this.addView(rightMenuView, getMenuLayout(MenuItem.EdgeTrack.RIGHT));
        }else {
            parent.addView(rightMenuView);
        }

        this.rightMenus.add(new Pair(rightMenuView, item));
    }

    /**
     * 生成布局参数
     * @param menu
     * @return
     */
    private FrameLayout.LayoutParams getMenuLayout(@MenuItem.EdgeTrackWhere int menu) {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (menu == MenuItem.EdgeTrack.LEFT) {
            lp.gravity = Gravity.START;
        }else if (menu == MenuItem.EdgeTrack.RIGHT) {
            lp.gravity = Gravity.END;
        }
        return lp;
    }

    /**
     * 生成横向多menu布局容器
     * @return
     */
    private LinearLayout getMultiHorizonMenuLayout() {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setBackgroundColor(Color.RED);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        layout.setLayoutParams(lp);
        return layout;
    }

    @MenuItem.EdgeTrackWhere
    private int EdgeTracking = MenuItem.EdgeTrack.RIGHT;


    private boolean isCloseOtherItemsWhenThisWillOpen = false;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                helper.cancel();

                RectF f = calcViewScreenLocation(itemView);
                boolean isIn = f.contains(ev.getRawX(), ev.getRawY());

                if (isIn && delegate.getMenuStatus() == SwipeDragHelperDelegate.MenuStatus.OPEN) {
                    delegate.closeMenuItem();
                    return true;
                }
                return false;
        }

        if (isCloseOtherItemsWhenThisWillOpen) {
            if (MotionEvent.ACTION_DOWN == action) {
                if (hasOpendMenuItems()) {
                    closeOtherMenuItems();
                }
            }
        }


        return helper.shouldInterceptTouchEvent(ev);
    }

    public static RectF calcViewScreenLocation(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getWidth(), location[1] + view.getHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        helper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (helper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public void closeMenuItem() {
        this.delegate.closeMenuItem();
    }

    @Override
    public void closeOtherMenuItems() {
        this.delegate.closeOtherMenuItems();
    }

    @Override
    public boolean hasOpendMenuItems() {
        return delegate.hasOpendMenuItems();
    }

    public void setIsCloseOtherItemsWhenThisWillOpen(boolean isCloseOtherItemsWhenThisWillOpen) {
        this.isCloseOtherItemsWhenThisWillOpen = isCloseOtherItemsWhenThisWillOpen;
    }

    public View getItemView() {
        return itemView;
    }

    public int getEdgeTracking() {
        return EdgeTracking;
    }



    private List<Pair<View, MenuItem>> empAllMenus;
    public List<Pair<View, MenuItem>> getMenus() {
        if (null != empAllMenus && !empAllMenus.isEmpty()) {
            return empAllMenus;
        }
        if (null == empAllMenus && ( (null != leftMenus && !leftMenus.isEmpty()) || (null != rightMenus && !rightMenus.isEmpty()) )) {
            empAllMenus = new ArrayList<>();
        }

        if (null != leftMenus && !leftMenus.isEmpty()) {
            empAllMenus.addAll(leftMenus);
        }

        if (null != rightMenus && !rightMenus.isEmpty()) {
            empAllMenus.addAll(rightMenus);
        }
        return empAllMenus;
    }

    public int getLeftMenuWidth() {
        if (null == this.leftMenus || this.leftMenus.isEmpty()) {
            return 0;
        }

        if (this.leftMenuWidth == 0) {
            for (Pair<View, MenuItem> pair:leftMenus) {
                leftMenuWidth += pair.first.getWidth();
            }
        }
        return leftMenuWidth;
    }

    public int getRightMenuWidth() {
        if (null == this.rightMenus || this.rightMenus.isEmpty()) {
            return 0;
        }

        if (this.rightMenuWidth == 0) {
            for (Pair<View, MenuItem> pair:rightMenus) {
                rightMenuWidth += pair.first.getWidth();
            }
        }
        return rightMenuWidth;
    }

    public int getMenuStatus() {
        return delegate.getMenuStatus();
    }

}
