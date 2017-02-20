package ws.dyt.recyclerviewadapter.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;

import ws.dyt.recyclerviewadapter.MyApplication;
import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.utils.ViewJect;

/**
 * Created by yangxiaowei on 16/12/11.
 */

public class DevFragment extends Fragment {

    protected View rootView;
    protected View fab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.recyclerview, container, false);
        fab = ViewJect.find(R.id.fab, rootView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFloatActionButtonClicked();
            }
        });
        setUpView();
        return rootView;
    }

    protected void setUpView() {

    }

    protected void onFloatActionButtonClicked() {}

    @Override
    public void onDestroy() {
        super.onDestroy();

        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        if(refWatcher != null) {

            refWatcher.watch(this);

        }
    }
}
