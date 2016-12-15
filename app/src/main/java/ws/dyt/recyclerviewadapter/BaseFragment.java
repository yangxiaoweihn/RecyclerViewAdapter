package ws.dyt.recyclerviewadapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ws.dyt.recyclerviewadapter.utils.ViewJect;
import ws.dyt.view.adapter.core.base.BaseAdapter;
import ws.dyt.view.adapter.core.base.HeaderFooterAdapter;
import ws.dyt.view.adapter.deprecated.SectionAdapter;
import ws.dyt.view.adapter.section.SectionMultiAdapter;

/**
 * Created by yangxiaowei on 16/6/9.
 */
abstract public class BaseFragment extends Fragment{
    protected LayoutInflater layoutInflater;
    @BindView(R.id.recyclerview)
    public RecyclerView recyclerView;
    @BindView(R.id.section_input)
    public ViewGroup mSectionInput;
    @BindView(R.id.et)
    EditText mEt;
    @BindView(R.id.et_operator)
    EditText mEtOperator;

    @BindView(R.id.section_clear)
    View mSectionClear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, rootView);

        mSectionInput.setVisibility(View.GONE);

        ViewJect.find(R.id.btn_add, rootView).setOnClickListener(ll);
        ViewJect.find(R.id.addHeader, rootView).setOnClickListener(ll);
        ViewJect.find(R.id.addFooter, rootView).setOnClickListener(ll);
        ViewJect.find(R.id.addSysHeader, rootView).setOnClickListener(ll);
        ViewJect.find(R.id.addSysFooter, rootView).setOnClickListener(ll);

        ViewJect.find(R.id.btn_clearSysHeader, rootView).setOnClickListener(ll);
        ViewJect.find(R.id.btn_clearHeader, rootView).setOnClickListener(ll);
        ViewJect.find(R.id.btn_clearFooter, rootView).setOnClickListener(ll);
        ViewJect.find(R.id.btn_clearSysFooter, rootView).setOnClickListener(ll);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        addListener();
    }

    private View.OnClickListener ll = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_clearSysHeader:{
                    adapter.clearSysHeaders();
                    break;
                }
                case R.id.btn_clearHeader:{
                    adapter.clearHeaders();
                    break;
                }
                case R.id.btn_clearFooter:{
                    adapter.clearFooters();
                    break;
                }
                case R.id.btn_clearSysFooter:{
                    adapter.clearSysFooters();
                    break;
                }
                case R.id.btn_add:{
                    onAddDataClick();
                    break;
                }
                case R.id.addHeader:{
                    onAddHeaderClick();
                    break;
                }

                case R.id.addFooter:{
                    onAddFooterClick();
                    break;
                }
                case R.id.addSysHeader:{
                    onAddSysHeaderClick();
                    break;
                }
                case R.id.addSysFooter:{
                    onAddSysFooterClick();
                    break;
                }
            }
        }
    };

    private void onAddDataClick() {
        String string = mEt.getText().toString().trim();
        if (TextUtils.isEmpty(string)) {
            Toast.makeText(getActivity(), "输入数据", Toast.LENGTH_SHORT).show();
            return;
        }
        //规则说明：
        // 1. -号分割索引位置，,号分割数据
        // 2. 如果无索引位置，则默认索引为末尾，具体回调用负数表示
        int i = string.indexOf("-");

        //没有索引
        if (i < 0) {
            //只有一个数据时，当作数据处理
            dealData(-1, string);
        }else {
            String index = string.substring(0, i);
            String data = string.substring(i+1);
            dealData(Integer.valueOf(index), data);
        }
    }

    private void dealData(int index, String dd){
        String[] info = dd.split(",");
        List<String> datas = new ArrayList<>();
        int len = info.length;
        for (int i = 0; i < len; i++) {
            datas.add(info[i]);
        }

        onInputData(Integer.valueOf(index), datas);
    }

    protected void onInputData(int position, List<String> data){
        BaseAdapter adapter = (BaseAdapter) getAdapter();
        if (data.isEmpty()) {
            return;
        }

        if (data.size() == 1) {
            if (position < 0) {
                adapter.add(data.get(0));
            }else {
                adapter.add(position, data.get(0));
            }
        }else {
            if (position < 0) {
                adapter.addAll(data);
            }else {
                adapter.addAll(position, data);
            }
        }
    }

    private int getPosition() {
        String s = mEtOperator.getText().toString();
        int p = -1;
        if (!TextUtils.isEmpty(s)) {
            p = Integer.valueOf(s);
        }
        return p;
    }

    private void onAddHeaderClick(){
        final View header1 = layoutInflater.inflate(R.layout.item_header_1, recyclerView, false);
        int p = getPosition();
        if (p < 0) {
            adapter.addHeaderView(header1);
        }else {
            adapter.addHeaderView(header1, p);
        }

        header1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                adapter.removeHeaderView(header1);
                return true;
            }
        });
    }

    private void onAddFooterClick(){
        final View footer1 = layoutInflater.inflate(R.layout.item_footer_1, recyclerView, false);
        int p = getPosition();
        if (p < 0) {
            adapter.addFooterView(footer1);
        }else {
            adapter.addFooterView(footer1, p);
        }

        footer1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                adapter.removeFooterView(footer1);
                return true;
            }
        });
    }

    private void onAddSysHeaderClick() {
        final View sysHeader = layoutInflater.inflate(R.layout.item_sys_header, recyclerView, false);
//        getAdapter().setSysHeaderView(sysHeader);
        int p = getPosition();
        if (p < 0) {
            adapter.addSysHeaderView(sysHeader);
        }else {
            adapter.addSysHeaderView(sysHeader, p);
        }

        sysHeader.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                adapter.removeSysHeaderView(sysHeader);
                return true;
            }
        });
    }

    private void onAddSysFooterClick(){
        final View sysFooter = layoutInflater.inflate(R.layout.item_sys_footer, recyclerView, false);
        adapter.setSysFooterView(sysFooter);
//        int p = getPosition();
//        if (p < 0) {
//        }else {
//        }

        sysFooter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                adapter.removeSysFooterView(sysFooter);
                return true;
            }
        });

    }

    abstract
    protected HeaderFooterAdapter getAdapter();

    public HeaderFooterAdapter adapter;

    private void addListener(){
        adapter = null == adapter ? getAdapter() : adapter;
        adapter.setOnItemClickListener(new HeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                toast(position);
            }
        });

        adapter.setOnItemLongClickListener(new HeaderFooterAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, int position) {
                toast(position);
            }
        });

        recyclerView.setAdapter(adapter);
        if (adapter instanceof SectionMultiAdapter) {
            SectionMultiAdapter sectionAdapter = (SectionMultiAdapter) adapter;
            sectionAdapter.setOnItemClickListener(new SectionMultiAdapter.OnItemClickListener() {
                @Override
                public void onClick(int group, int positionOfGroup, int positionOfData, @SectionAdapter.ItemTypeWhere int type) {
                    String a = "";
                    switch (type) {
                        case SectionMultiAdapter.ItemType.ITEM_HEADER: {
                            a = "H-> group: " + group + " , index: " + positionOfGroup;
                            break;
                        }
                        case SectionMultiAdapter.ItemType.ITEM_DATA: {
                            a = "D-> group: " + group + " , index: " + positionOfGroup + " , " + positionOfData;
                            break;
                        }
                        case SectionMultiAdapter.ItemType.ITEM_FOOTER: {
                            a = "F-> group: " + group + " , index: " + positionOfGroup;
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    tost(a);
                }
            });
        }
    }


    private void toast(int position){
        this.tost("" + position);
    }

    public void tost(String des) {
        Toast.makeText(getContext(), des, Toast.LENGTH_SHORT).show();
    }
}
