package ws.dyt.library.adapter;

/**
 * Created by yangxiaowei on 16/6/23.
 */
public class ItemWrapper<T> {
    public int type;
    public T data;

    public ItemWrapper(int type, T data) {
        this.type = type;
        this.data = data;
    }
}
