package ws.dyt.recyclerviewadapter.rr_nest;


import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.rr_nest.adapter.BaseFragmentPagerAdapter;
import ws.dyt.recyclerviewadapter.rr_nest.item.BaseItemFrament;
import ws.dyt.recyclerviewadapter.rr_nest.item.main.MeItemFragment;
import ws.dyt.recyclerviewadapter.rr_nest.item.main.IndexItemFragment;
import ws.dyt.recyclerviewadapter.rr_nest.item.main.DiagnosisItemFragment;
import ws.dyt.recyclerviewadapter.rr_nest.item.main.ClassesItemFragment;
import ws.dyt.recyclerviewadapter.rr_nest.item.sub.SubMainFragment;
import ws.dyt.recyclerviewadapter.rr_nest.tabs.TabItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class NestMainFragment extends Fragment {


    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    public NestMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nest_main, container, false);
        ButterKnife.bind(this, view);

        this.initTabs();
        return view;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MainTabIndex.TAB_INDEX, MainTabIndex.TAB_HIDOC, MainTabIndex.TAB_DUDU, MainTabIndex.TAB_ME})
    @interface MainTabIndexWhere{}
    public interface MainTabIndex{
        int TAB_INDEX    = 0;
        int TAB_HIDOC    = 1;
        int TAB_DUDU     = 2;
        int TAB_ME       = 3;
    }

    TabItem[] tabDataHolders = {
            new TabItem(MainTabIndex.TAB_INDEX, "首页"),
            new TabItem(MainTabIndex.TAB_HIDOC, "好多课"),
            new TabItem(MainTabIndex.TAB_DUDU, "问诊"),
            new TabItem(MainTabIndex.TAB_ME, "我的")
    };

    private void initTabs() {
        final int size = tabDataHolders.length;
        mViewPager.setOffscreenPageLimit(size);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mTabLayout.setOnTabSelectedListener(selectedListener);

        List<Pair<String, Fragment>> fragmentsInfo = new ArrayList<>(size);
        Fragment[] fragments = new Fragment[] {
                new IndexItemFragment(),
                new ClassesItemFragment(),
//                new DiagnosisItemFragment(),
                new SubMainFragment(),
                new MeItemFragment()
        };
        for (int i = 0; i < size; i++) {
            TabItem holder = tabDataHolders[i];
            fragmentsInfo.add(new Pair<>(holder.Title, fragments[i]));
        }
        BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), fragmentsInfo);

        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager);

        final int index = 1;
        mTabLayout.getTabAt(index).select();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        for (int i = 0; i < size; i++) {
            TabItem holder = tabDataHolders[i];
            View item = inflater.inflate(R.layout.item_tab_space_item, mTabLayout, false);
            TextView tv = ViewInject.findView(R.id.tv_title, item);
            tv.setText(holder.Title);

            mTabLayout.getTabAt(i).setCustomView(item);//.setIcon(tabContentDrawable);

            if (index == i) {
                item.setSelected(mTabLayout.getTabAt(index).isSelected());
            }


        }


    }

    private TabLayout.OnTabSelectedListener selectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition(), false);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };


    @Override
    public void onResume() {
        super.onResume();

        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (null == fragments || fragments.isEmpty()) {
            Log.e("DEBUG", "NestMainFragment -> OneItemFragment -> onResume : fragments size: 0 (null)");
            return;
        }

        Log.e("DEBUG", "NestMainFragment -> OneItemFragment -> onResume : size: "+fragments.size());
        for (Fragment fragment:fragments) {
            if (null == fragment) {
                Log.e("DEBUG", "\n   ]]> NestMainFragment -> OneItemFragment || fragment is null");
                continue;
            }
            String info =
                    "isAdded: "+fragment.isAdded()+" , isDetached: "+fragment.isDetached()
                    +" , isHidden: "+fragment.isHidden()+" , isInLayout: "+fragment.isInLayout()
                    +" , isRemoving: "+fragment.isRemoving()+" , isResumed: "+fragment.isResumed()
                    +" , isVisible: "+fragment.isVisible();

            info = "\n   ]]> NestMainFragment -> OneItemFragment || "+fragment.getClass().getSimpleName()+" -> "+info;
            Log.e("DEBUG", info);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (null == mViewPager) {
            return;
        }

//        mViewPager.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                int to = mTabLayout.getSelectedTabPosition() + 1;
//                to = to % tabDataHolders.length;
//                mTabLayout.getTabAt(to).select();
//            }
//        }, 1000);
    }
}
