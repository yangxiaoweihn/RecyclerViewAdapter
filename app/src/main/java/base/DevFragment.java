package base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.utils.ViewJect;

/**
 * Created by yangxiaowei on 16/12/11.
 */

public class DevFragment extends Fragment {

    protected View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.recyclerview, container, false);
        View fab = ViewJect.find(R.id.fab, rootView);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFloatActionButtonClicked();
            }
        });
        return rootView;
    }

    protected void onFloatActionButtonClicked() {}
}
