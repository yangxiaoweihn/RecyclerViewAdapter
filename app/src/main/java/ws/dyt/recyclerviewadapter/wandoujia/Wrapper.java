package ws.dyt.recyclerviewadapter.wandoujia;

/**
 * Created by yangxiaowei on 16/6/22.
 */
public class Wrapper<T> {
    public int type;
    public T data;

    public Wrapper(int type, T data) {
        this.type = type;
        this.data = data;
    }
}
