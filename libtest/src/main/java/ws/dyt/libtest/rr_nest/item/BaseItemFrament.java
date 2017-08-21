package ws.dyt.recyclerviewadapter.rr_nest.item;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.lazyload.LazyLoadFragment;
import ws.dyt.recyclerviewadapter.main.SingleFragmentActivity;
import ws.dyt.recyclerviewadapter.utils.UnitUtils;
import ws.dyt.view.adapter.SuperAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;

/**
 * Created by yangxiaowei on 17/1/12.
 */

abstract
public class BaseItemFrament extends LazyLoadFragment {
    @BindView(R.id.recyclerview)
    public RecyclerView mRecyclerview;
    @BindView(R.id.fab)
    public FloatingActionButton mFab;

    protected View rootView;
    protected SuperAdapter adapter;
    @Nullable
    @Override
    public View onLazyCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.recyclerview, container, false);
        ButterKnife.bind(this, rootView);
        this.init();
        this.setupView();
        return rootView;
    }

    protected void setupView(){
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleFragmentActivity.to(v.getContext(), ClassesDetailItemFragment.class, null);
            }
        });
    }

    private void init() {
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerview.setLayoutManager(lm);

        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext()));
        adapter = new SuperAdapter(getContext(), null, setItemLayoutId()) {
            @Override
            public BaseViewHolder onCreateViewHolderWithMultiItemTypes(int itemLayoutOfViewType, View itemViewOfViewType) {
                BaseViewHolder bh = BaseItemFrament.this.onCreateViewHolder(itemLayoutOfViewType, itemViewOfViewType);
                if (null != bh) {
                    return bh;
                }
                return super.onCreateViewHolderWithMultiItemTypes(itemLayoutOfViewType, itemViewOfViewType);
            }

            @Override
            public void convert(BaseViewHolder holder, int position) {
                BaseItemFrament.this.onConvert(holder, position);
            }
        };
        mRecyclerview.setAdapter(adapter);
    }

    abstract
    protected @LayoutRes int setItemLayoutId();

    protected BaseViewHolder onCreateViewHolder(int itemLayoutOfViewType, View itemViewOfViewType) {
        return null;
    }

    abstract
    protected void onConvert(BaseViewHolder holder, int position);

    private static class DividerItemDecoration extends RecyclerView.ItemDecoration{
        private int dividerOfNormal;

        public DividerItemDecoration(Context context) {
            dividerOfNormal = UnitUtils.dip2Px(context, 10);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            final int position = parent.getChildAdapterPosition(view);
            if (position < 0) {
                return;
            }

            outRect.bottom = dividerOfNormal;
        }
    }
}
