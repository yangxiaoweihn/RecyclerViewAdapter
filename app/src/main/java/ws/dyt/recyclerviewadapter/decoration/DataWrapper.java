package ws.dyt.recyclerviewadapter.decoration;

import ws.dyt.adapter.adapter.ItemWrapper;

public class DataWrapper<T> extends ItemWrapper<T> {
    public int group;
    public int index;

    public DataWrapper(int type, T data) {
        super(type, data);
    }

    /**
     * @param type  0：组  1：广告  2：动漫
     * @param group
     * @param index
     * @param data
     */
    public DataWrapper(int type, int group, int index, T data) {
        super(type, data);
        this.group = group;
        this.index = index;
    }

    public @interface DataType {
        int GROUP_TITLE = 0;
        int AD = 1;
        int GROUP_DATA = 2;
    }

    @Override
    public String toString() {
        return "Wrapper1{" +
                "type= " + type +
                " , group= " + group +
                " , index= " + index +
                '}';
    }
}