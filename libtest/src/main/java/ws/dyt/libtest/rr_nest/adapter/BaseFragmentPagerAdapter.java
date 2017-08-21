package ws.dyt.recyclerviewadapter.rr_nest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;

import java.util.List;

/**
 * Created by yangxiaowei on 17/1/5.
 */

public class BaseFragmentPagerAdapter<T extends Fragment> extends FragmentPagerAdapter {
    private List<Pair<String, T>> fragmentsInfo;

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Pair<String, T>> fragmentsInfo) {
        super(fm);

        this.fragmentsInfo = fragmentsInfo;
    }

    @Override
    public Fragment getItem(int position) {
        return isEmpty() ? null : fragmentsInfo.get(position).second;
    }

    @Override
    public int getCount() {
        return isEmpty() ? 0 : fragmentsInfo.size();
    }

    private boolean isEmpty() {
        return null == fragmentsInfo || fragmentsInfo.isEmpty();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return isEmpty() ? super.getPageTitle(position) : fragmentsInfo.get(position).first;
    }
}
