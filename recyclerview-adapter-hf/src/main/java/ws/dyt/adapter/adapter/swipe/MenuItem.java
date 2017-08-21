package ws.dyt.adapter.adapter.swipe;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by yangxiaowei on 16/8/1.
 *
 * 菜单信息
 */
public class MenuItem {
    //菜单布局
    private int menuLayoutId;

    //菜单方向
    @MenuItem.EdgeTrackWhere
    private int edgeTrack;

    //菜单id
    private int menuId;


    public MenuItem(int menuLayoutId, int edgeTrack, int menuId) {
        this.menuLayoutId = menuLayoutId;
        this.edgeTrack = edgeTrack;
        this.menuId = menuId;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EdgeTrack.LEFT, EdgeTrack.RIGHT, EdgeTrack.LEFT_RIGHT})
    public @interface EdgeTrackWhere {}

    /**
     * 菜单打开方向
     */
    public interface EdgeTrack{
        int LEFT = 0;
        int RIGHT = 1;
        int LEFT_RIGHT = 2;
    }

    public int getMenuLayoutId() {
        return menuLayoutId;
    }

    public int getEdgeTrack() {
        return edgeTrack;
    }

    public int getMenuId() {
        return menuId;
    }
}
