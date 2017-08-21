package ws.dyt.recyclerviewadapter.wandoujia;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yangxiaowei on 16/6/22.
 */
public class AppEntity {
    @Expose
    @SerializedName("app_icon")
    public String AppIcon = null;
    @Expose
    @SerializedName("app_name")
    public String AppName;
    @Expose
    @SerializedName("app_des")
    public String AppDes;
    @Expose
    @SerializedName("app_size")
    public String AppSize;
    @Expose
    @SerializedName("app_install_count")
    public String AppInstallCount;

    public AppEntity(String appName, String appDes, String appSize) {
        AppName = appName;
        AppDes = appDes;
        AppSize = appSize;
    }
}
