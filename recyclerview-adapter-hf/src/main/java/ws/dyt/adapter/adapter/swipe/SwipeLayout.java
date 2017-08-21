package ws.dyt.adapter.adapter.swipe;

import android.content.Context;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
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

import ws.dyt.adapter.adapter.Log.L;
import ws.dyt.adapter.adapter.core.base.IGC;

/**
 * Created by yangxiaowei on 16/8/1.
 *
 * 用来承载菜单视图和客户端设置的item视图
 */
public class SwipeLayout extends FrameLayout implements ICloseMenus, IMenuStatus, IGC{
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

    private ViewDragHelper mViewDragHelper;
    private SwipeDragHelperDelegate mDelegate;
    private void init() {
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(lp);
    }

    private ViewGroup mViewGroup;

    private View mItemView;
    private int mLeftMenuWidth;
    private int mRightMenuWidth;
    private List<Pair<View, MenuItem>> mLeftMenus;
    private List<Pair<View, MenuItem>> mRightMenus;

    /**
     *
     * @param viewGroup inflate viw
     * @param itemView
     * @param menuItems
     */
    public void setUpView(ViewGroup viewGroup, View itemView, List<MenuItem> menuItems) {
        this.mViewGroup = viewGroup;
        this.mItemView = itemView;

        if (null == menuItems || menuItems.isEmpty()) {
            return;
        }


        //筛选左右菜单
        Pair<List<MenuItem>, List<MenuItem>> menuPair = this.filterLeftAndRightMenu(menuItems, null, null);
        List<MenuItem> left = menuPair.first;
        List<MenuItem> right = menuPair.second;
        menuItems.clear();

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

        //同时支持左右菜单
        if (null != left && !left.isEmpty() && null != right && !right.isEmpty()) {
            this.EdgeTracking = MenuItem.EdgeTrack.LEFT_RIGHT;
        }

        if (null != itemView) {
            this.addView(itemView);
        }

        this.mDelegate = new SwipeDragHelperDelegate(this);
        this.mViewDragHelper = ViewDragHelper.create(this, 1.0f, mDelegate);
        this.mDelegate.init(mViewDragHelper);

        if (this.EdgeTracking == MenuItem.EdgeTrack.LEFT_RIGHT) {
            mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT|ViewDragHelper.EDGE_RIGHT);

        }else if (this.EdgeTracking == MenuItem.EdgeTrack.LEFT) {
            mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);

        }else if (this.EdgeTracking == MenuItem.EdgeTrack.RIGHT) {
            mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);

        }
    }

    /**
     * 筛选左右菜单
     * @param menuItems
     * @param left
     * @param right
     * @return Pair<List<MenuItem>, List<MenuItem>> first: left menu  second: right menu
     */
    private Pair<List<MenuItem>, List<MenuItem>> filterLeftAndRightMenu(List<MenuItem> menuItems, List<MenuItem> left, List<MenuItem> right) {
        for (MenuItem e:menuItems) {
            if (null == e) {
                continue;
            }

            final int et = e.getEdgeTrack();
            if (et == MenuItem.EdgeTrack.LEFT) {
                if (null == left) {
                    left = new ArrayList<>();
                }
                left.add(e);
            }else if (et == MenuItem.EdgeTrack.RIGHT) {
                if (null == right) {
                    right = new ArrayList<>();
                }
                right.add(e);
            }
        }
        return new Pair<>(left, right);
    }

    private void setUpLeftMenuView(MenuItem item, LinearLayout parent) {
        if (null == mLeftMenus) {
            this.mLeftMenus = new ArrayList<>();
        }
        View leftMenuView = LayoutInflater.from(getContext()).inflate(item.getMenuLayoutId(), mViewGroup, false);

        if (null == parent) {
            this.addView(leftMenuView, getMenuLayout(MenuItem.EdgeTrack.LEFT));
        }else {
            parent.addView(leftMenuView);
        }

        this.mLeftMenus.add(new Pair(leftMenuView, item));
    }

    private void setUpRightMenuView(MenuItem item, LinearLayout parent) {
        if (null == mRightMenus) {
            this.mRightMenus = new ArrayList<>();
        }

        View rightMenuView = LayoutInflater.from(getContext()).inflate(item.getMenuLayoutId(), mViewGroup, false);

        if (null == parent) {
            this.addView(rightMenuView, getMenuLayout(MenuItem.EdgeTrack.RIGHT));
        }else {
            parent.addView(rightMenuView);
        }

        this.mRightMenus.add(new Pair(rightMenuView, item));
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

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        layout.setLayoutParams(lp);
        return layout;
    }

    @MenuItem.EdgeTrackWhere
    private int EdgeTracking = MenuItem.EdgeTrack.RIGHT;


    private boolean mIsCloseOtherItemsWhenThisWillOpen = false;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        L.e("onInterceptTouchEvent->"+action+" , "+ mIsCloseOtherItemsWhenThisWillOpen);
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mViewDragHelper.cancel();

                RectF f = calcViewScreenLocation(mItemView);
                boolean isIn = f.contains(ev.getRawX(), ev.getRawY());

                if (isIn && mDelegate.getMenuStatus() == SwipeDragHelperDelegate.MenuStatus.OPEN) {
                    mDelegate.closeMenuItem();
                    return true;
                }
                return false;
        }

        if (mIsCloseOtherItemsWhenThisWillOpen) {
            if (MotionEvent.ACTION_DOWN == action) {
                if (SwipeDragHelperDelegate.hasOpenedMenuItems()) {
                    if (!closeOtherMenuItems()) {
                        //保证有item menu打开时其他item不能响应事件
                        return true;
                    }
                }
            }
        }


        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    public static RectF calcViewScreenLocation(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getWidth(), location[1] + view.getHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public void closeMenuItem() {
        this.mDelegate.closeMenuItem();
    }

    @Override
    public boolean closeOtherMenuItems() {
        return this.mDelegate.closeOtherMenuItems();
    }

//    @Override
//    public boolean hasOpenedMenuItems() {
//        return mDelegate.hasOpenedMenuItems();
//    }

    public void setIsCloseOtherItemsWhenThisWillOpen(boolean isCloseOtherItemsWhenThisWillOpen) {
        this.mIsCloseOtherItemsWhenThisWillOpen = isCloseOtherItemsWhenThisWillOpen;
    }

    public View getItemView() {
        return mItemView;
    }

    //当前item支持的菜单类型
    public int getEdgeTracking() {
        return EdgeTracking;
    }


    private List<Pair<View, MenuItem>> empAllMenus;
    public List<Pair<View, MenuItem>> getMenus() {
        if (null != empAllMenus && !empAllMenus.isEmpty()) {
            return empAllMenus;
        }
        if (null == empAllMenus && ( (null != mLeftMenus && !mLeftMenus.isEmpty()) || (null != mRightMenus && !mRightMenus.isEmpty()) )) {
            empAllMenus = new ArrayList<>();
        }

        if (null != mLeftMenus && !mLeftMenus.isEmpty()) {
            empAllMenus.addAll(mLeftMenus);
        }

        if (null != mRightMenus && !mRightMenus.isEmpty()) {
            empAllMenus.addAll(mRightMenus);
        }
        return empAllMenus;
    }

    public int getLeftMenuWidth() {
        if (null == this.mLeftMenus || this.mLeftMenus.isEmpty()) {
            return 0;
        }

        if (this.mLeftMenuWidth == 0) {
            for (Pair<View, MenuItem> pair: mLeftMenus) {
                mLeftMenuWidth += pair.first.getWidth();
            }
        }
        return mLeftMenuWidth;
    }

    public int getRightMenuWidth() {
        if (null == this.mRightMenus || this.mRightMenus.isEmpty()) {
            return 0;
        }

        if (this.mRightMenuWidth == 0) {
            for (Pair<View, MenuItem> pair: mRightMenus) {
                mRightMenuWidth += pair.first.getWidth();
            }
        }
        return mRightMenuWidth;
    }

    public int getMenuStatus() {
        return mDelegate.getMenuStatus();
    }

    @Override
    public boolean isOpenedMenu() {
        return getMenuStatus() == SwipeDragHelperDelegate.MenuStatus.OPEN;
    }

    /**
     * 该item被移除时，删除关联信息
     */
    @Override
    public void release() {
//        if (null != mLeftMenus) {
//            mLeftMenus.clear();
//        }
//
//        if (null != mRightMenus) {
//            mRightMenus.clear();
//        }

        if (null != mDelegate) {
            mDelegate.releaseItem(this);
        }
    }
}
