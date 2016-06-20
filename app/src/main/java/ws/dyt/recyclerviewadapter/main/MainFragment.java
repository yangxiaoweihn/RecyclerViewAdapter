package ws.dyt.recyclerviewadapter.main;


import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.library.adapter.MultiAdapter;
import ws.dyt.library.adapter.base.BaseHFAdapter;
import ws.dyt.library.viewholder.BaseViewHolder;
import ws.dyt.recyclerviewadapter.MultiSimpleItemV2ConvertSingleSimpleFragment;
import ws.dyt.recyclerviewadapter.MultiSimpleItemV2ConvertSingleSimpleGridFragment;
import ws.dyt.recyclerviewadapter.MultiSimpleItemV2ConvertSingleSimpleStaggeredGridFragment;
import ws.dyt.recyclerviewadapter.MultiSimpleItemV2Fragment;
import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.SectionGridFragment;
import ws.dyt.recyclerviewadapter.SectionLinearFragment;
import ws.dyt.recyclerviewadapter.SectionLinearNoFooterFragment;
import ws.dyt.recyclerviewadapter.SingleSimpleItemFragment;

/**
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recyclerview, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.init();
    }

    private void init() {
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        final MultiAdapter<FragmentEntity> adapter = getAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseHFAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                toFragment(adapter.getItem(position).clazz);
            }
        });
    }

    private MultiAdapter<FragmentEntity> getAdapter() {
        return new MultiAdapter<FragmentEntity>(getContext(), generate(), R.layout.item_main) {
            @Override
            public void convert(BaseViewHolder holder, int position) {
                holder.setText(R.id.tv_text, getItem(position).showName);
            }
        };
    }

    private List<FragmentEntity> generate() {
        List<FragmentEntity> data = new ArrayList<>();
        data.add(new FragmentEntity("多简单类型V2", MultiSimpleItemV2Fragment.class));
        data.add(new FragmentEntity("多简单类型适配单类型", MultiSimpleItemV2ConvertSingleSimpleFragment.class));
        data.add(new FragmentEntity("多简单类型适配单类型Grid", MultiSimpleItemV2ConvertSingleSimpleGridFragment.class));
        data.add(new FragmentEntity("多简单类型适配单类型Staggered", MultiSimpleItemV2ConvertSingleSimpleStaggeredGridFragment.class));
        data.add(new FragmentEntity("section纵向线性单item", SectionLinearFragment.class));
        data.add(new FragmentEntity("section单itemGrid", SectionGridFragment.class));
        data.add(new FragmentEntity("section无Footer", SectionLinearNoFooterFragment.class));
        return data;
    }

    private void toFragment(Class clazz) {
        Intent intent = new Intent(getActivity(), SingleFragmentActivity.class);
        intent.putExtra(SingleFragmentActivity.CLASS_FRAGMENT, clazz.getName());
        startActivity(intent);
    }
}
