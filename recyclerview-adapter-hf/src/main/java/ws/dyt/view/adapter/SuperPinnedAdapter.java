package ws.dyt.view.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import java.util.List;

import ws.dyt.view.adapter.pinned.PinnedAdapter;

/**
 * Created by yangxiaowei on 16/8/9.
 *
 */
abstract
public class SuperPinnedAdapter<T extends ItemWrapper> extends PinnedAdapter<T> {
    public SuperPinnedAdapter(Context context, List<T> datas) {
        super(context, datas);
    }
}