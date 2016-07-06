package ws.dyt.view.adapter.base;

import java.util.List;

/**
 * Created by yangxiaowei on 16/6/9.
 */
public interface CRUD<T> {
    void add(T item);

    void add(int position, T item);

    void addAll(List<T> items);

    void addAll(int position, List<T> items);

    void remove(T item);

    void remove(int position);

    void removeAll(List<T> items);

    void replace(T oldItem, T newItem);

    void replace(int position, T item);

    void replaceAll(List<T> items);

    void clear();
}
