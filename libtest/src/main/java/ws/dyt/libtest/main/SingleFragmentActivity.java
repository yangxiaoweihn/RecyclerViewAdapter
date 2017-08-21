package ws.dyt.recyclerviewadapter.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ws.dyt.recyclerviewadapter.R;

/**
 * Created by yangxiaowei
 */
public class SingleFragmentActivity extends AppCompatActivity{
	public static final String CLASS_FRAGMENT = "class_fragment";
	public static final String ARGUMENTS_TO_FRAGMENT = "arguments_to_fragment";

	public static void to(Context context, Class<? extends Fragment> toFragmentClass, Bundle argsToFragment) {
		Intent intent = new Intent(context, SingleFragmentActivity.class);
		intent.putExtra(CLASS_FRAGMENT, toFragmentClass.getName());
		intent.putExtra(ARGUMENTS_TO_FRAGMENT, argsToFragment);
		context.startActivity(intent);
	}

	protected Fragment fragment = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		
		if(null == savedInstanceState){
			String clazz = getIntent().getStringExtra(CLASS_FRAGMENT);
			try {
				fragment = (Fragment) Class.forName(clazz).newInstance();
				fragment.setArguments(getIntent().getBundleExtra(ARGUMENTS_TO_FRAGMENT));
				getSupportFragmentManager().beginTransaction().replace(R.id.layout_data, fragment, "_fragment_").commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			fragment = getSupportFragmentManager().findFragmentByTag("_fragment_");
		}
	}

	public Fragment getFragment(){
		return fragment;
	}


	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		Log.e("DEBUG", "OneItemFragment -> onWindowFocusChanged: "+hasFocus);
	}
}
