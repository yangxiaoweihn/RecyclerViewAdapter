package ws.dyt.recyclerviewadapter;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ws.dyt.view.adapter.SuperAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;
import ws.dyt.recyclerviewadapter.utils.Data;


/**
 */
public class MultiSimpleItemV2ConvertSingleSimpleFragment extends BaseFragment {
    public MultiSimpleItemV2ConvertSingleSimpleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        this.init();
        return rootView;
    }

    static class DataWrapper<T> {
        int type;
        T data;

        public DataWrapper(int type, T data) {
            this.type = type;
            this.data = data;
        }
    }

    SuperAdapter<DataWrapper> adapter;
    private void init(){
        List<Data.SkinData> list = Data.generate();
        List<List<String>> banner = new ArrayList<>();
        banner.add(new ArrayList<>(Arrays.asList(new String[]{"A-00", "A-11", "A-22"})));
        banner.add(new ArrayList<>(Arrays.asList(new String[]{"B-00", "B-11", "B-22"})));

        List<DataWrapper> data = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Data.SkinData e = list.get(i);
            data.add(new DataWrapper(1, e));
        }

        data.add(3, new DataWrapper(0, banner.get(0)));
        data.add(3+4, new DataWrapper(0, banner.get(1)));

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        adapter = new SuperAdapter<DataWrapper>(getContext(), data, R.layout.item_text_c) {
            @Override
            public int getItemViewLayout(int position) {
                super.getItemViewLayout(position);
                int type = position % 3;
//                if (type == 0){
//                    type = R.layout.item_text_l;
//                }else if (type == 1){
//                    type = R.layout.item_text_c;
//                }else {
//                    type = R.layout.item_text_r;
//                }

                type = getItem(position).type == 0 ? R.layout.item_viewpager : R.layout.item_skin;

                return type;
            }

            @Override
            public void convert(BaseViewHolder holder, int position) {
                Log.e("FFF", "p: "+position);
                DataWrapper e = getItem(position);
                if (e.type == 0) {
                    aa(holder, (List<String>) e.data);
                    return;
                }
                Data.SkinData sd = (Data.SkinData) e.data;
                holder.setImageResource(R.id.iv_skin, sd.img).setText(R.id.tv_text, sd.title);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public SuperAdapter<DataWrapper> getAdapter() {
        return adapter;
    }

    private void aa(BaseViewHolder holder, List<String> banners){
        ViewPager viewPager = holder.getView(R.id.viewPager);
        viewPager.setAdapter(new BannerAdapter(banners));
    }

    private class BannerAdapter extends PagerAdapter {
        private List<View> views = new ArrayList<>();
        private List<String> banners = new ArrayList<>();


        public BannerAdapter(List<String> banners) {
            int[] colors = new int[]{
                    Color.parseColor("#FFC0CB"),
                    Color.parseColor("#9370DB"),
                    Color.parseColor("#7FFFD4")
            };
            this.banners = banners;
            for (int i = 0; i < banners.size(); i++) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_vp_con, null, false);
                view.setBackgroundColor(colors[i]);
                ((TextView) view.findViewById(R.id.tv_text)).setText(banners.get(i));
                views.add(view);
            }
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            View v = views.get(position);
            container.addView(v);
            return v;
        }
    }

}
