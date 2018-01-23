package ws.dyt.adapter.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import java.util.List;

/**
 * Created by yangxiaowei on 18/1/23.
 *
 * 客户端接口
 * 支持:
 * 1. 支持 {@link SuperAdapter} 所有功能
 * 2. 支持粘性头部相关操作
 */
abstract
public class SuperStickyAdapter<T extends ItemWrapper> extends SuperPinnedAdapter<T> {
    public SuperStickyAdapter(Context context, List<T> data) {
        super(context, data);
    }

    public SuperStickyAdapter(Context context, List<T> data, @LayoutRes int itemLayoutResId) {
        super(context, data, itemLayoutResId);
    }

}