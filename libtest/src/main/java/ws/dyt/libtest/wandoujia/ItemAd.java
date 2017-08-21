package ws.dyt.recyclerviewadapter.wandoujia;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by yangxiaowei on 16/6/22.
 */
public class ItemAd {
    @Expose
    @SerializedName("list")
    ArrayList<AppEntity> datas;
}
