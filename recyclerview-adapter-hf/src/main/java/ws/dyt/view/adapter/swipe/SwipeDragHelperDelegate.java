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


    @Override
    public int clampViewPositionHorizontal(View child, int left, int dx) {
        if (swipeLayout.getEdgeTracking() == MenuItem.EdgeTrack.LEFT) {
            int menuWidth = swipeLayout.getLeftMenuWidth();
            if (left > menuWidth && dx > 0) {
                return menuWidth;
            }
            if (left < 0 && dx < 0) {
                return 0;
            }
        }else if (swipeLayout.getEdgeTracking() == MenuItem.EdgeTrack.RIGHT) {
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

        final int et = swipeLayout.getEdgeTracking();
        final int l = Math.abs(itemView.getLeft());

        final int menuWidth;
        //获取菜单宽度
        if (et == MenuItem.EdgeTrack.LEFT) {
            menuWidth = swipeLayout.getLeftMenuWidth();

        }else if (et == MenuItem.EdgeTrack.RIGHT){
            menuWidth = swipeLayout.getRightMenuWidth();

        }else {
            menuWidth = 0;
        }

        final float min = Math.abs(menuWidth * openMenuBoundaryPercent);

        final int left;

        Log.e("DEBUG", "left: "+l+" , min: "+min+" , from: "+this.menuBoundaryStatusOfBeenTo);
        //计算偏移量
        if (l < min || (MenuStatus.OPEN == this.menuBoundaryStatusOfBeenTo && l < menuWidth)) {
            left = 0;
        } else {
            if (et == MenuItem.EdgeTrack.LEFT) {
                left = +1 * menuWidth;
            }else if (et == MenuItem.EdgeTrack.RIGHT) {
                left = -1 * menuWidth;
            }else {
                left = 0;
            }
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
        return swipeLayout.getItemView() == child ? child.getHeight() : 0;
    }

    @Override
    public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
        super.onViewPositionChanged(changedView, left, top, dx, dy);
        this.updateMenuStatus(left);
    }

    private void updateMenuStatus(int left) {
        final int et = swipeLayout.getEdgeTracking();
        int menuWidth = 0;
        if(MenuItem.EdgeTrack.LEFT == et) {
            menuWidth = swipeLayout.getLeftMenuWidth();
        }else if (MenuItem.EdgeTrack.RIGHT == et) {
            menuWidth = swipeLayout.getRightMenuWidth();
        }

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
            if((MenuItem.EdgeTrack.LEFT == et && left == menuWidth) || (MenuItem.EdgeTrack.RIGHT == et && left == menuWidth)) {
                this.menuStatus = MenuStatus.OPEN;
            }else {
                this.menuStatus = MenuStatus.DRAGING;
            }
        }

        //记录打开关闭菜单项的item
        if (left == 0) {
            this.openView.remove(this.swipeLayout);
        }else if (0 != menuWidth && left == menuWidth) {
            if (!openView.contains(swipeLayout)) {
                openView.add(swipeLayout);
            }
        }
    }

    @Override
    public void closeMenuItem() {
        this.helper.smoothSlideViewTo(this.swipeLayout.getItemView(), 0, 0);
        ViewCompat.postInvalidateOnAnimation(this.swipeLayout);
    }


    public static List<SwipeLayout> openView = new ArrayList<>();

    @Override
    public void closeOtherMenuItems() {
        Log.e("DEBUG", "openView.size: "+openView.size());
        for (SwipeLayout e:openView) {
            if (null == e) {
                continue;
            }
            if (e == this.swipeLayout) {
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
    @IntDef({MenuStatus.OPEN, MenuStatus.DRAGING, MenuStatus.CLOSED})
    private @interface MenuStatusWhere{}

    /**
     * 菜单状态
     */
    public interface MenuStatus{
        int CLOSED = -1;
        int DRAGING = 0;
        int OPEN = 1;
    }

    public int getMenuStatus() {
        return menuStatus;
    }

    @Override
    public boolean hasOpendMenuItems() {
        return null != openView && !openView.isEmpty();
    }
}
