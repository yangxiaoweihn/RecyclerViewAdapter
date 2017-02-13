package ws.dyt.view.adapter.swipe;

import android.support.annotation.IntDef;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import ws.dyt.view.adapter.Log.L;

/**
 * Created by yangxiaowei on 16/8/1.
 */
public class SwipeDragHelperDelegate extends ViewDragHelper.Callback implements ICloseMenus{

    private ViewDragHelper helper;
    private SwipeLayout swipeLayout;
    public SwipeDragHelperDelegate(final SwipeLayout swipeLayout) {
        this.swipeLayout = swipeLayout;
    }


    public void init(ViewDragHelper helper) {
        this.helper = helper;
    }

    @Override
    public boolean tryCaptureView(View child, int pointerId) {
        final View itemView = swipeLayout.getItemView();
        if (null != itemView && itemView == child) {
            return true;
        }
        return false;
    }

    /**
     * 用来判断是否左菜单滑出
     * @return
     */
    private boolean checkMenuIsLeft() {
        //用来标识正在出现的菜单
        boolean isLeftMenuVisible;
        final int et = swipeLayout.getEdgeTracking();
        if (et == MenuItem.EdgeTrack.LEFT_RIGHT) {
            //同时存在左右菜单
            isLeftMenuVisible = which == 1;
        }else {
            isLeftMenuVisible = et == MenuItem.EdgeTrack.LEFT;
        }
        return isLeftMenuVisible;
    }

    /**
     * 获取菜单宽度
     * @return
     */
    private int getMenuWidth() {
        final int menuWidth;
        //获取菜单宽度
        if (checkMenuIsLeft()) {
            menuWidth = swipeLayout.getLeftMenuWidth();

        }else {
            menuWidth = swipeLayout.getRightMenuWidth();

        }

        return menuWidth;
    }

    @Override
    public int clampViewPositionHorizontal(View child, int left, int dx) {
        //左菜单被打开-向右滑动
        if (left >= 0) {
            which = 1;
        }else {
            //右菜单被打开-向左滑动
            which = 2;
        }

        //用来标识正在出现的菜单
        boolean isLeftMenuVisible = checkMenuIsLeft();
        L.e("clampViewPositionHorizontal -> trackType: "+ which +" , "+left+" , "+dx);

        if (isLeftMenuVisible) {
            int menuWidth = swipeLayout.getLeftMenuWidth();
            if (left < 0 && dx < 0) {
                return 0;
            }
            if (left > menuWidth && dx > 0) {
                return menuWidth;
            }
        }else {
            int menuWidth = swipeLayout.getRightMenuWidth();
            if (left > 0 && dx > 0) {
                return 0;
            }
            if (left < -menuWidth && dx < 0) {
                return -menuWidth;
            }
        }

        return left;
    }

    @Override
    public void onViewReleased(View releasedChild, float xvel, float yvel) {
        final View itemView = swipeLayout.getItemView();
        if (releasedChild != itemView) {
            return;
        }

        //用来标识正在出现的菜单
        boolean isLeftMenuVisible = checkMenuIsLeft();

        final int l = Math.abs(itemView.getLeft());

        //获取菜单宽度
        final int menuWidth = getMenuWidth();

        final float min = Math.abs(menuWidth * openMenuBoundaryPercent);

        int left = 0;

        Log.e("DEBUG", "left: "+l+" , min: "+min+" , from: "+this.menuBoundaryStatusOfBeenTo);
        //计算偏移量
        if (l < min || (MenuStatus.OPEN == this.menuBoundaryStatusOfBeenTo && l < menuWidth)) {
            left = 0;
        } else {
            left += isLeftMenuVisible ? (+1 * menuWidth) : (-1 * menuWidth);
        }
        this.helper.settleCapturedViewAt(left, 0);
        this.swipeLayout.invalidate();
    }

    @Override
    public int getViewHorizontalDragRange(View child) {
        return swipeLayout.getItemView() == child ? child.getWidth() : 0;
    }

    @Override
    public int getViewVerticalDragRange(View child) {
//        return swipeLayout.getItemView() == child ? child.getHeight() : 0;
        return 0;
    }


    private int which = 1;

    @Override
    public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
        super.onViewPositionChanged(changedView, left, top, dx, dy);
        L.e("onViewPositionChanged-> "+left+" , "+dx);

        this.updateMenuStatus(left);
    }
    private void updateMenuStatus(int left) {
        int menuWidth = getMenuWidth();

        //记录拖动时到达过的边界状态
        if (left == 0) {
            this.menuBoundaryStatusOfBeenTo = MenuStatus.CLOSED;
        }else if (Math.abs(left) >= menuWidth) {
            this.menuBoundaryStatusOfBeenTo = MenuStatus.OPEN;
        }

        //记录操作过程中菜单的真实状态
        if (left == 0 ) {
            this.menuStatus = MenuStatus.CLOSED;
        }else {
            left = Math.abs(left);
            if(left == menuWidth) {
                this.menuStatus = MenuStatus.OPEN;
            }else {
                this.menuStatus = MenuStatus.DRAGGING;
            }
        }

        //记录打开关闭菜单项的item
        if (left == 0) {
            this.opened.remove(this.swipeLayout);
        }else if (0 != menuWidth && left == menuWidth) {
            if (!opened.contains(swipeLayout)) {
                opened.add(swipeLayout);
            }
        }
    }

    @Override
    public void closeMenuItem() {
        this.helper.smoothSlideViewTo(this.swipeLayout.getItemView(), 0, 0);
        ViewCompat.postInvalidateOnAnimation(this.swipeLayout);
    }


    public static List<SwipeLayout> opened = new ArrayList<>();

    @Override
    public boolean closeOtherMenuItems() {
        L.e("opened.size: "+opened.size());
        //当前item menu打开状态
        boolean currentItemMenuOpened = false;
        for (SwipeLayout e:opened) {
            if (null == e) {
                continue;
            }
            if (e == this.swipeLayout) {
                currentItemMenuOpened = true;
                continue;
            }

            e.closeMenuItem();
        }
        return currentItemMenuOpened;
    }

    public static void closeAllMenuItems() {
        for (SwipeLayout e:opened) {
            if (null == e) {
                continue;
            }
            e.closeMenuItem();
        }
    }

    //打开菜单所滑动的边界百分比,超过将打开菜单,否在则不打开
    private float openMenuBoundaryPercent = 0.2f;


    //记录拖动之前达到过的状态(只要到达过菜单开的状态，此时再次移动将会关闭菜单)
    @MenuBoundaryStatusOfBeenToWhere
    private int menuBoundaryStatusOfBeenTo = MenuStatus.CLOSED;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MenuStatus.OPEN, MenuStatus.CLOSED})
    private @interface MenuBoundaryStatusOfBeenToWhere {}

    //记录真实状态
    @MenuStatusWhere
    private int menuStatus = MenuStatus.CLOSED;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MenuStatus.OPEN, MenuStatus.DRAGGING, MenuStatus.CLOSED})
    private @interface MenuStatusWhere{}

    /**
     * 菜单状态
     */
    public interface MenuStatus{
        int CLOSED = -1;
        int DRAGGING = 0;
        int OPEN = 1;
    }

    public int getMenuStatus() {
        return menuStatus;
    }

    public static boolean hasOpenedMenuItems() {
        return null != opened && !opened.isEmpty();
    }

    public static void release() {
        if (null != opened) {
            opened.clear();
        }
    }
}
