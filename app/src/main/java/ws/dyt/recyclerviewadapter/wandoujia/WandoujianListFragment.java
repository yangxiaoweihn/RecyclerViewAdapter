package ws.dyt.recyclerviewadapter.wandoujia;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.library.adapter.MultiAdapter;
import ws.dyt.library.adapter.base.HeaderFooterAdapter;
import ws.dyt.library.viewholder.BaseViewHolder;
import ws.dyt.recyclerviewadapter.BaseFragment;
import ws.dyt.recyclerviewadapter.utils.FileUtils;
import ws.dyt.recyclerviewadapter.R;

/**
 * Created by yangxiaowei on 16/6/22.
 */
public class WandoujianListFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        this.init();
        return view;
    }


    MultiAdapter<Wrapper> adapter;

    private void init() {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        adapter = new MultiAdapter<Wrapper>(getContext(), generate()) {
            @Override
            public int getItemViewLayout(int position) {
                Wrapper e = getItem(position);
                if (e.type == 0) {
                    Log.e("Layout", "ad_header: " + R.layout.wdj_ad_header);
                    return R.layout.wdj_ad_header;
                } else if (e.type == 1) {
                    Log.e("Layout", "ad_item: " + R.layout.wdj_item_ad);
                    return R.layout.wdj_item_ad;
                }
                Log.e("Layout", "data_item : " + R.layout.wdj_item_data);
                return R.layout.wdj_item_data;
            }

            @Override
            public void convert(BaseViewHolder holder, int position) {
                Wrapper e = getItem(position);
                if (e.type == 0) {
                    bindHeaderAd(holder, (AdHeader) e.data);
                } else if (e.type == 1) {
                    //ad
                    bindItemAd(holder, (ItemAd) e.data);
                } else if (e.type == 2) {
                    //app
                    bindItemData(holder, (ItemData) e.data);
                }
            }
        };

        recyclerView.setAdapter(adapter);
    }

    private void bindHeaderAd(BaseViewHolder holder, AdHeader ad) {
        holder
                .setText(R.id.tv_des, ad.AppDes)
                .setText(R.id.tv_app_name, ad.AppName)
                .setText(R.id.tv_size, ad.AppSize)
                .setText(R.id.tv_install_count, ad.AppInstallCount);
    }

    private void bindItemAd(BaseViewHolder holder, ItemAd ads) {
        View v1 = holder.getView(R.id.ad_1);
        View v2 = holder.getView(R.id.ad_2);
        View v3 = holder.getView(R.id.ad_3);
        v1.setBackgroundColor(Color.parseColor("#FFB6C1"));
        v2.setBackgroundColor(Color.parseColor("#87CEFA"));
        v3.setBackgroundColor(Color.parseColor("#EEE8AA"));
        bindItemAppInfo(v1, ads.datas.get(0));
        bindItemAppInfo(v2, ads.datas.get(1));
        bindItemAppInfo(v3, ads.datas.get(2));
    }

    private void bindItemAppInfo(View view, AppEntity ad) {
        ((TextView) getView(R.id.tv_app_name, view)).setText(ad.AppName);
        if (TextUtils.isEmpty(ad.AppSize)) {
            getView(R.id.tv_app_size_item, view).setVisibility(View.GONE);
        } else {
            getView(R.id.tv_app_size_item, view).setVisibility(View.VISIBLE);
            ((TextView) getView(R.id.tv_app_size_item, view)).setText(ad.AppSize);
        }

        if (TextUtils.isEmpty(ad.AppDes)) {
            getView(R.id.tv_app_des_item, view).setVisibility(View.GONE);
        } else {
            getView(R.id.tv_app_des_item, view).setVisibility(View.VISIBLE);
            ((TextView) getView(R.id.tv_app_des_item, view)).setText(ad.AppDes);
        }

        View btnInstall = getView(R.id.tv_install, view);
        btnInstall.setTag(ad);
        btnInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object o = v.getTag();
                if (null == o || !(o instanceof AppEntity)) {
                    return;
                }

                tost("" + ((AppEntity) o).AppName);
            }
        });

    }

    private void bindItemData(BaseViewHolder holder, ItemData data) {
        holder.setText(R.id.tv_title, data.GroupTitle);
        bindItemAppInfo(holder.getView(R.id.data_1), data.apps.get(0));
        bindItemAppInfo(holder.getView(R.id.data_2), data.apps.get(1));
        bindItemAppInfo(holder.getView(R.id.data_3), data.apps.get(2));
    }

    private <V extends View> V getView(int id, View root) {
        return (V) root.findViewById(id);
    }

    @Override
    protected HeaderFooterAdapter getAdapter() {
        return adapter;
    }

    private List<Wrapper> generate() {
        AdHeader adHeader = new Gson().fromJson(FileUtils.readRawFile(getResources(), R.raw.wdj_header_ad), AdHeader.class);
        List<ItemData> itemDatas = new ArrayList<>();

        List<Wrapper> datas = new ArrayList<>();
        datas.add(new Wrapper(0, adHeader));
        for (int i = 0; i < 30; i++) {
            //ad
            if (0 != i && i % 5 == 0) {
                ItemAd itemData = new ItemAd();
                ArrayList<AppEntity> apps = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    AppEntity app = new AppEntity("木蚂蚁-" + i + "," + j, "", "4.7M");
                    apps.add(app);
                }
                itemData.datas = apps;
                datas.add(new Wrapper(1, itemData));
            } else {
                ItemData itemData = new ItemData();
                itemData.GroupTitle = "测试分组App--" + i;
                itemData.GroupDes = "";
                ArrayList<AppEntity> apps = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    AppEntity app = new AppEntity("木蚂蚁-" + i + "," + j, "", "4.7M");
                    apps.add(app);
                }
                itemData.apps = apps;
                datas.add(new Wrapper(2, itemData));
            }
        }

        return datas;
    }
}
