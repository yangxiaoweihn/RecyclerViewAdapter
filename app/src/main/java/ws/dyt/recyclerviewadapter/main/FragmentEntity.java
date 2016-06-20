package ws.dyt.recyclerviewadapter.main;

import android.support.v4.app.Fragment;

/**
 * Created by yangxiaowei on 16/6/20.
 */
public class FragmentEntity {
    public String showName;
    public Class<?> clazz;

    public FragmentEntity(String showName, Class<?> clazz) {
        this.showName = showName;
        this.clazz = clazz;
    }
}
