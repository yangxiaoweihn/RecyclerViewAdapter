package ws.dyt.recyclerviewadapter.lazyload;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import ws.dyt.view.adapter.Log.L;

/**
 * Created by yangxiaowei on 16/7/30.
 */
public abstract class LazyLoadFragment extends Fragment {

    private Bundle mSavedInstanceState;

    /** 标识客户端是否真正初始化了视图, 通过调用{@link #lazyCreateView} **/
    private boolean mIsRealViewSetup;

    private View mRootView;

    /** 是否已经调用了初始化view方法 **/
    private boolean mIsCalledOnCreateViewMethod = false;

    @Nullable
    @Override
    final
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.isViewLazyLoadEnable() == false || originVisibleOfUserHint) {//不是懒加载
            Log.e("LazyLoadFragment", "onCreateView() -> will call lazyCreateView() ");
            this.mRootView = lazyCreateView(LayoutInflater.from(getContext()), container, mSavedInstanceState);
            this.mIsRealViewSetup = true;
        } else {
            Log.e("LazyLoadFragment", "onCreateView() -> init by FrameLayout ");
            this.mRootView = new FrameLayout(getContext());
        }
        Log.e("LazyLoadFragment", "onCreateView -> " + isViewLazyLoadEnable() + " , >> " + getClass().getSimpleName());
        this.mSavedInstanceState = savedInstanceState;
        this.mIsCalledOnCreateViewMethod = true;
        return mRootView;
    }

    private boolean originVisibleOfUserHint = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.originVisibleOfUserHint = getUserVisibleHint();

        Log.e("LazyLoadFragment", "setUserVisibleHint -> " + isVisibleToUser + " , originVisibleOfUserHint: " + originVisibleOfUserHint + " ]]> " + getClass().getSimpleName() + " , rootView: " + mRootView);
        if (this.mRootView == null) {
            return;
        }

        if (this.isViewLazyLoadEnable() && isVisibleToUser && mIsCalledOnCreateViewMethod == true && mIsRealViewSetup == false) {
            Log.e("LazyLoadFragment", "setUserVisibleHint() -> will call lazyCreateView() ");
            ViewGroup rootView = (ViewGroup) mRootView;
            rootView.removeAllViews();
            View contentView = lazyCreateView(LayoutInflater.from(getContext()), rootView, mSavedInstanceState);
            rootView.addView(contentView);
            this.mIsRealViewSetup = true;
        }

        if (this.mIsRealViewSetup) {
            this.onFragmentVisibilityChangedInner(isVisibleToUser, false);
        }
    }


    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        Log.e("LazyLoadFragment", "onStart -> mIsRealViewSetup: " + mIsRealViewSetup + " , originVisibleOfUserHint+ " + originVisibleOfUserHint + " ]]> " + getClass().getSimpleName());

        if (mIsRealViewSetup && originVisibleOfUserHint) {
            this.onFragmentVisibilityChangedInner(true, true);
        }

        //页面初始化完毕并且view不支持延迟加载时，处理为可见[主要针对单页面]
        if (mIsRealViewSetup && !isViewLazyLoadEnable()) {
            this.onFragmentVisibilityChangedInner(true, true);
        }
    }

    @CallSuper
    @Override
    public void onStop() {
        super.onStop();

        if (mIsRealViewSetup && originVisibleOfUserHint) {
            this.onFragmentVisibilityChangedInner(false, true);
        }

        if (mIsRealViewSetup && !isViewLazyLoadEnable()) {
            this.onFragmentVisibilityChangedInner(false, true);
        }
    }


    private View lazyCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("LazyLoadFragment", "lazyCreateView -> [-" + getClass().getSimpleName() + "-]");
        return this.onLazyCreateView(inflater, container, savedInstanceState);
    }


    /** 记录当前fragment可见状态 **/
    public boolean mCurrentFragmentVisibility = false;

    /**
     * fragment可见性变化时回调
     *
     * @param isVisibleToUser 当前fragment是否前台可见
     * @param isLifeCycle     当前fragment可见性是否由fragment生命周期变化引起  false: 由调用{@link #setUserVisibleHint}引起
     */
    @CallSuper
    private void onFragmentVisibilityChangedInner(boolean isVisibleToUser, boolean isLifeCycle) {
        this.mCurrentFragmentVisibility = isVisibleToUser;
        Fragment fragment = getParentFragment();
        boolean fragmentVisible = false;
        if (fragment instanceof LazyLoadFragment) {
            //处理view非懒加载时有二级fragment viewpager情况第一次初始化时的问题
            //这种情况下一级fragment不可见，但二级viewpager中fragment初始化后会自动设置二级fragment的可见性
            fragmentVisible = ((LazyLoadFragment) fragment).mCurrentFragmentVisibility;
            if (!fragmentVisible && isLifeCycle) {
                return;
            }
        }

        this.onFragmentVisibilityChangedInner(isVisibleToUser);
        Log.e("LazyLoadFragment", "onFragmentVisibilityChangedInner -> currentFragment: " +getClass().getSimpleName()+"->  isVisibleToUser: "+isVisibleToUser+" , isLifeCycle: " + isLifeCycle + " , originVisible: "+isVisible()
                + (null != fragment ? " , parent: " + fragment.getClass().getSimpleName() + " = " + fragmentVisible
                + " , originVisible: "+fragment.isVisible() : "")
        );

        final ViewPager viewPager = this.setNestedViewPagerWithNestedFragment();
        if (null != viewPager) {
            this.handleNestedFragmentVisibilityWhenFragmentVisibilityChanged(viewPager, isVisibleToUser, isLifeCycle);
        }
    }

    /**
     * 处理在内外层viewpager里的fragment初始化后引起fragment可见性不一致问题(尤其开启了view懒加载后，子viewpager并未处理外层fragment可见性)
     * 这里的处理是:
     * 1. 外层fragment不可见时，它内部的所有fragment都应该不可见
     * 2. 内部fragment可见时，他所关联的父fragment也应该可见
     *
     * @param viewPager
     * @param isVisible
     * @param isLifeCycle
     */
    private void handleNestedFragmentVisibilityWhenFragmentVisibilityChanged(final ViewPager viewPager, boolean isVisible, boolean isLifeCycle) {
        Log.e("DEBUG", "onFragmentVisibilityChangedInner ---- ###  " + isVisible + " , " + isLifeCycle);
        if (null == viewPager || isLifeCycle) {
            return;
        }
        final FragmentPagerAdapter adapter = ((FragmentPagerAdapter) viewPager.getAdapter());
        if (isVisible == false) {
            //不可见的情况下，子viewpager里的所有fragment都不应该可见
            final int size = adapter.getCount();
            for (int i = 0; i < size; i++) {
                Fragment fragment = adapter.getItem(i);
                if (null == fragment) {
                    continue;
                }

                fragment.setUserVisibleHint(isVisible);
            }
        } else {
            Log.e("DEBUG", "onFragmentVisibilityChangedInner ---- " + viewPager.getCurrentItem());
            Fragment fragment = adapter.getItem(viewPager.getCurrentItem());
            if (null != fragment) {
                fragment.setUserVisibleHint(isVisible);
            }
        }
    }

    /**
     * 视图初始化回调
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public abstract View onLazyCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 适用与外层是viewpager(vp_outer) ，viewpager中某个fragment中也用了viewpager(vp_inner)时，用来设置vp_inner
     *
     * @return
     */
    protected ViewPager setNestedViewPagerWithNestedFragment() {
        return null;
    }


    private boolean mVisible;
    /**
     * fragment可见性变化时回调
     *
     * Fragment 可见时该方法会回调一次, 不可见时保证最少调用一次
     * @param visible
     */
    private void onFragmentVisibilityChangedInner(boolean visible) {
        this.mVisible = visible;
        Log.e("XXXXX", "XXXX "+getClass().getSimpleName()+" , "+visible);
        this.onFragmentVisibilityChanged(visible);
    }

    /**
     * fragment可见性变化时回调
     *
     * Fragment 可见时该方法会回调一次, 不可见时保证最少调用一次
     * @param visible
     */
    protected void onFragmentVisibilityChanged(boolean visible) {}

    public boolean isFragmentVisible() {
        return mVisible;
    }

    /**
     * 是否开启view的懒加载模式
     *
     * @return
     */
    protected boolean isViewLazyLoadEnable() {
        return true;
    }
}
