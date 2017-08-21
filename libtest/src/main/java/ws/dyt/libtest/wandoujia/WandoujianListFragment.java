package ws.dyt.recyclerviewadapter.wandoujia;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.recyclerviewadapter.BaseFragment;
import ws.dyt.recyclerviewadapter.R;
import ws.dyt.view.adapter.ItemWrapper;
import ws.dyt.view.adapter.SuperAdapter;
import ws.dyt.view.adapter.core.base.HeaderFooterAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;

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


    SuperAdapter<ItemWrapper> adapter;

    private void init() {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        adapter = new SuperAdapter<ItemWrapper>(getContext(), generate()) {
            @Override
            public int getItemViewLayout(int position) {
                ItemWrapper e = getItem(position);
                if (e.type == 0) {
                    return R.layout.wdj_ad_header;
                } else if (e.type == 1) {
                    return R.layout.wdj_item_ad;
                }
                return R.layout.wdj_item_data;
            }

            @Override
            public void convert(BaseViewHolder holder, int position) {
                ItemWrapper e = getItem(position);
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

    private List<ItemWrapper> generate() {
        AdHeader adHeader = new AdHeader("XXXApp", "注册送100元红包\n还有2%加息券", "12.5M");
        adHeader.AppInstallCount = "23万人安装";

        List<ItemWrapper> datas = new ArrayList<>();
        datas.add(new ItemWrapper(0, adHeader));
        for (int i = 0; i < 30; i++) {
            //ad
            if (0 != i && i % 5 == 0) {
                ItemAd itemData = new ItemAd();
                ArrayList<AppEntity> apps = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    AppEntity app = new AppEntity("推广APP-" + i + "," + j, "", "4.7M");
                    apps.add(app);
                }
                itemData.datas = apps;
                datas.add(new ItemWrapper(1, itemData));
            } else {
                ItemData itemData = new ItemData();
                itemData.GroupTitle = "测试分组App--" + i;
                itemData.GroupDes = "";
                ArrayList<AppEntity> apps = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    AppEntity app = new AppEntity("三方APP-" + i + "," + j, "", "4.7M");
                    apps.add(app);
                }
                itemData.apps = apps;
                datas.add(new ItemWrapper(2, itemData));
            }
        }

        return datas;
    }
}
