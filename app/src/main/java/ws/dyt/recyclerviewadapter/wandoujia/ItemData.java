package ws.dyt.recyclerviewadapter.wandoujia;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiaowei on 16/6/22.
 */
public class ItemData {
    @Expose
    @SerializedName("group_title")
    public String GroupTitle;
    public String GroupDes;

    @Expose
    @SerializedName("list")
    public ArrayList<AppEntity> apps;
}
