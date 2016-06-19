package ws.dyt.library.adapter.base;

import android.content.Context;
import android.util.Log;

import java.util.List;


/**
 * Created by yangxiaowei on 16/6/8.
 */
public abstract class BaseAdapter<T> extends BaseHFAdapter<T> implements CRUD<T> {
    public static final String TAG = "Adapter";

    public BaseAdapter(Context context, List<T> datas) {
        super(context, datas);
    }



    @Override
    public void add(T item) {
        datas.add(item);
        int index = getHeaderViewCount() + getDataSectionItemCount() - 1;
        Log.e(TAG, "index: "+index);
        notifyItemInserted(index);
    }

    @Override
    public void add(int position, T item) {
        datas.add(position, item);
        position = getHeaderViewCount() + position;
        notifyItemInserted(position);
    }

    @Override
    public void addAll(List<T> items) {
        if (null == items || items.isEmpty()) {
            Log.w(TAG, "no data.");
            return;
        }
        int index = getDataSectionItemCount();
        datas.addAll(items);
        index = getHeaderViewCount() + index;
        notifyItemRangeInserted(index, items.size());
    }

    @Override
    public void addAll(int position, List<T> items) {
        if (null == items || items.isEmpty()) {
            Log.w(TAG, "no data.");
            return;
        }

        if (position < 0 || position >= getDataSectionItemCount()) {
            Log.w(TAG, "position IndexOutOfBoundsException.");
            return;
        }

        position += getHeaderViewCount();
        datas.addAll(position, items);
        notifyItemRangeInserted(position, items.size());
    }

    @Override
    public void remove(T item) {
        if (datas.contains(item)) {
            remove(datas.indexOf(item));
        }
    }

    @Override
    public void remove(int position) {
        datas.remove(position);
        position += getHeaderViewCount();
        notifyItemRemoved(position);
    }

    @Override
    public void removeAll(List<T> items) {
        datas.removeAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void replace(T oldItem, T newItem) {
        replace(datas.indexOf(oldItem), newItem);
    }

    @Override
    public void replace(int position, T item) {
        datas.set(position, item);
        position += getHeaderViewCount();
        notifyItemChanged(position);
    }

    @Override
    public void replaceAll(List<T> items) {

    }

    @Override
    public void clear() {
        if (isEmpty()) {
            return;
        }
        datas.clear();
        notifyDataSetChanged();
    }
}
