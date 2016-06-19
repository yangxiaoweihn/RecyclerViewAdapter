package ws.dyt.recyclerviewadapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toSingleSimpleItem(View view){
        toFragment(SingleSimpleItemFragment.class);
    }

    public void toMultiSimpleItem(View view){
    }

    public void toMultiSimpleItemV2(View view){
        toFragment(MultiSimpleItemV2Fragment.class);
    }

    public void toMultiSimpleItemV2AdapteSigleSimple(View view){
        toFragment(MultiSimpleItemV2ConvertSingleSimpleFragment.class);
    }

    public void toMultiSimpleItemV2AdapteSigleSimpleGird(View view) {
        toFragment(MultiSimpleItemV2ConvertSingleSimpleGridFragment.class);
    }

    public void toMultiSimpleItemV2AdapteSigleSimpleStaggered(View view) {
        toFragment(MultiSimpleItemV2ConvertSingleSimpleStaggeredGridFragment.class);
    }

    public void toSectionSingle(View view) {
        toFragment(SectionLinearFragment.class);
    }

    public void toSectionGrid(View view) {
        toFragment(SectionGridFragment.class);
    }

    public void toSectionNoFooter(View view) {
        toFragment(SectionLinearNoFooterFragment.class);
    }

    private void toFragment(Class clazz){
        Intent intent = new Intent(this, SingleFragmentActivity.class);
        intent.putExtra(SingleFragmentActivity.CLASS_FRAGMENT, clazz.getName());
        startActivity(intent);
    }


}
