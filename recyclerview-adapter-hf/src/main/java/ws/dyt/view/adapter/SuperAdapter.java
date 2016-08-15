package ws.dyt.view.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import java.util.List;

import ws.dyt.view.adapter.core.MultiAdapter;
import ws.dyt.view.adapter.swipe.SwipeAdapter;

/**
 * Created by yangxiaowei on 16/8/9.
 *
 */
abstract
public class SuperAdapter<T> extends MultiAdapter<T> {

    public SuperAdapter(Context context, List<T> datas) {
        super(context, datas);
    }

    public SuperAdapter(Context context, List<T> datas, @LayoutRes int itemLayoutResId) {
        super(context, datas, itemLayoutResId);
    }
}