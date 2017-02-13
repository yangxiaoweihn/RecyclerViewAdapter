package ws.dyt.view.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import java.util.List;

import ws.dyt.view.adapter.pinned.PinnedAdapter;

/**
 * Created by yangxiaowei on 16/8/9.
 *
 * 客户端接口
 * 支持:
 * 1. 支持 {@link SuperAdapter} 所有功能
 * 2. 支持粘性头部相关操作
 */
abstract
public class SuperPinnedAdapter<T extends ItemWrapper> extends PinnedAdapter<T> {
    public SuperPinnedAdapter(Context context, List<T> data) {
        super(context, data);
    }

    public SuperPinnedAdapter(Context context, List<T> data, @LayoutRes int itemLayoutResId) {
        super(context, data, itemLayoutResId);
    }

}