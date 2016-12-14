package ws.dyt.view.adapter.core.base;

import android.content.Context;

import java.util.List;

import ws.dyt.view.adapter.Log.L;


/**
 * Created by yangxiaowei on 16/6/8.
 *
 * 主要提供数据操作
 */
abstract
public class BaseAdapter<T> extends HeaderFooterAdapter<T> implements CRUD<T> {

    public BaseAdapter(Context context, List<T> datas) {
        super(context, datas);
    }


    @Override
    public void add(T item) {
        this.add(getDataSectionItemCount(), item);
    }

    @Override
    public void add(int position, T item) {
        if (null == item) {
            L.e("data should't be null");
            return;
        }
        if (position < 0 || position > getDataSectionItemCount()) {
            throw new IndexOutOfBoundsException("data position out of bounds");
        }
        realDatas.add(position, item);
        position = this.getAllHeaderViewCount() + position;
        notifyItemInserted(position);
    }

    @Override
    public void addAll(List<T> items) {
        this.addAll(getDataSectionItemCount(), items);
    }

    @Override
    public void addAll(int position, List<T> items) {
        if (null == items || items.isEmpty()) {
            L.w("no data.");
            return;
        }

        if (position < 0 || position > getDataSectionItemCount()) {
            throw new IndexOutOfBoundsException("data position out of bounds");
        }

        realDatas.addAll(position, items);
        position += this.getAllHeaderViewCount();
        notifyItemRangeInserted(position, items.size());
    }

    @Override
    public void remove(T item) {
        if (isEmptyOfData()) {
            return;
        }
        if (realDatas.contains(item)) {
            remove(realDatas.indexOf(item));
        }
    }

    @Override
    public void remove(int position) {
        if (isEmptyOfData()) {
            return;
        }
        if (position < 0 || position > getDataSectionItemCount()) {
            throw new IndexOutOfBoundsException("data position out of bounds");
        }

        realDatas.remove(position);
        position += this.getAllHeaderViewCount();
        notifyItemRemoved(position);
    }

    @Override
    public void removeAll(List<T> items) {
        if (isEmptyOfData()) {
            return;
        }
        realDatas.removeAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void replace(T oldItem, T newItem) {
        replace(realDatas.indexOf(oldItem), newItem);
    }

    @Override
    public void replace(int position, T item) {
        if (isEmptyOfData()) {
            return;
        }
        if (position < 0 || position > getDataSectionItemCount()) {
            throw new IndexOutOfBoundsException("data position out of bounds");
        }

        realDatas.set(position, item);
        position += this.getAllHeaderViewCount();
        notifyItemChanged(position);
    }

    @Override
    public boolean isEmptyOfData() {
        return isEmpty();
    }

    @Override
    public void replaceAll(List<T> items) {

    }

    @Override
    public void clear() {
        if (isEmpty()) {
            return;
        }
        realDatas.clear();
        notifyDataSetChanged();
    }
}
