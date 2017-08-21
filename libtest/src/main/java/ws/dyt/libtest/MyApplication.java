package ws.dyt.recyclerviewadapter;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by yangxiaowei on 17/2/20.
 */

public class MyApplication extends Application{

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        mRefWatcher = LeakCanary.install(this);
        BlockCanary.install(this, new BlockCanaryContext() {}).start();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }

    public static RefWatcher getRefWatcher(Context context) {

        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.mRefWatcher;

    }
}
