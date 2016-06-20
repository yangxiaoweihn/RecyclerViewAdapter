package ws.dyt.recyclerviewadapter.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ws.dyt.recyclerviewadapter.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        getSupportFragmentManager().beginTransaction().add(R.id.layout_data, MainFragment.newInstance(), "main_fragment").commit();
    }


}
