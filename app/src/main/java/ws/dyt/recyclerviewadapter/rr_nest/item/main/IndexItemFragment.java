package ws.dyt.recyclerviewadapter.rr_nest.item.main;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.rr_nest.item.BaseItemFrament;
import ws.dyt.recyclerviewadapter.utils.Data;
import ws.dyt.recyclerviewadapter.utils.UnitUtils;
import ws.dyt.view.adapter.SuperAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;

/**
 * Created by yangxiaowei on 17/1/12.
 */

public class IndexItemFragment extends BaseItemFrament {

    @Override
    protected void setupView() {
        super.setupView();
        mFab.setVisibility(View.VISIBLE);

        adapter.addAll(generateData());

//        rootView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                boolean f = mRecyclerview.isLayoutFrozen();
//                Log.e("DEBUG", "OneItemFragment -> onTouch: "+f);
//                if (f) {
//                    mRecyclerview.setLayoutFrozen(false);
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("DEBUG", "OneItemFragment -> onStart");
    }

    @Override
    public void onResume() {
        super.onResume();

//        if (null == mRecyclerview) {
//            return;
//        }
//        boolean f = mRecyclerview.isLayoutFrozen();
//        Log.e("DEBUG", "OneItemFragment -> onResume: visible: "+thisIsVisible+" , frozen: "+f);
//        if (f) {
//            mRecyclerview.setLayoutFrozen(false);
//        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("DEBUG", "OneItemFragment -> onHiddenChanged: "+hidden);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
    }

    @Override
    public void onFragmentVisibilityChanged(boolean isVisible) {
        super.onFragmentVisibilityChanged(isVisible);
        if (!isVisible) {
//            mRecyclerview.setLayoutFrozen(true);
        }
        if (isVisible) {
            mRecyclerview.setLayoutFrozen(true);
            mRecyclerview.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    mRecyclerview.stopScroll();
                    mRecyclerview.setLayoutFrozen(false);
                }
            }, 500);
        }
//        mRecyclerview.setLayoutFrozen(!isVisible);
    }

    @Override
    protected int setItemLayoutId() {
        return R.layout.item_nest_one;
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(int itemLayoutOfViewType, View itemViewOfViewType) {
        BaseViewHolder bh = new BaseViewHolder(itemViewOfViewType);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        bh.setAdapter(R.id.recyclerview, getSubAdapter(), lm, new DividerItemDecoration(getContext()));
        return bh;
    }

    @Override
    protected void onConvert(BaseViewHolder holder, int position) {
        DataHolder dh = (DataHolder) adapter.getItem(position);
        RecyclerView rv = holder.getView(R.id.recyclerview);
        SuperAdapter<DataHolder> sa = (SuperAdapter) rv.getAdapter();
        sa.clear();
        sa.addAll(generateData());
    }

    private SuperAdapter<DataHolder> getSubAdapter() {
        return new SuperAdapter<DataHolder>(getContext(), this.generateData(), R.layout.item_nest_item) {
            @Override
            public void convert(BaseViewHolder holder, int position) {
                DataHolder dh = getItem(position);
                holder
                        .setText(R.id.tv_text, dh.desc)
                        .setImageResource(R.id.iv_skin, dh.src);
            }
        };
    }

    private static class DividerItemDecoration extends RecyclerView.ItemDecoration{
        private int dividerOfNormal;

        public DividerItemDecoration(Context context) {
            dividerOfNormal = UnitUtils.dip2Px(context, 10);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            final int position = parent.getChildAdapterPosition(view);
            if (position < 0 ) {
                return;
            }

            outRect.right = dividerOfNormal;
        }
    }
    private List<DataHolder> generateData() {
        List<DataHolder> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new DataHolder("欢迎Follow我的GitHub,关注我的简书.滚动控件的实现方" +i, R.drawable.bilili_ad_3));
        }
        return list;
    }

    private static class DataHolder{
        public String desc;
        public @DrawableRes int src;

        public DataHolder(String desc, int src) {
            this.desc = desc;
            this.src = src;
        }
    }
}
