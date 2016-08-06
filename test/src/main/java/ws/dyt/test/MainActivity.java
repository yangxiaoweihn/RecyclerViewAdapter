package ws.dyt.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.test.view.SwipeLayout;

public class MainActivity extends Activity {

	private ListView listView;
	private MyAdapter adapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		initView();
	}
	private void initView(){
		listView=(ListView) findViewById(R.id.listView);
		adapter=new MyAdapter();
		listView.setAdapter(adapter);
	}
	
	
	
	
	private class MyAdapter extends BaseAdapter{
		//存放所有已经打开的菜单
		private List<SwipeLayout> openList=new ArrayList<SwipeLayout>();
		@Override
		public int getCount() {
			return 7;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if(convertView==null){
				holder=new ViewHolder();
				convertView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_swipe, parent, false);
				holder.textView=(TextView) convertView.findViewById(R.id.textView);
				holder.imageView=(ImageView) convertView.findViewById(R.id.imageView);
				holder.tv_del=(TextView) convertView.findViewById(R.id.tv_del);
				holder.swipeLayout=(SwipeLayout) convertView.findViewById(R.id.swipeLayout);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			holder.textView.setText(strs[position]);
			holder.imageView.setImageResource(ids[position]);
			holder.tv_del.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					holder.swipeLayout.close();
					Toast.makeText(getApplicationContext(),strs[position],Toast.LENGTH_SHORT).show();
				}
			});
			
			holder.swipeLayout.setSwipeChangeListener(new SwipeLayout.OnSwipeChangeListener() {
				
				@Override
				public void onStartOpen(SwipeLayout mSwipeLayout) {
					
					for(SwipeLayout layout:openList){
						layout.close();
					}
					openList.clear();
				}
				
				@Override
				public void onStartClose(SwipeLayout mSwipeLayout) {
					
				}
				
				@Override
				public void onOpen(SwipeLayout mSwipeLayout) {
					openList.add(mSwipeLayout);
				}
				
				@Override
				public void onDraging(SwipeLayout mSwipeLayout) {
					
				}
				
				@Override
				public void onClose(SwipeLayout mSwipeLayout) {
					openList.remove(mSwipeLayout);
				}
			});
			return convertView;
		}
		
	}
	
	private class ViewHolder{
		SwipeLayout swipeLayout;
		TextView textView;
		TextView tv_del;
		ImageView imageView;
	}
	
	private String strs[]={"hello world","lovery","my group","my computer","my phone","my doc","my key"};
	private int ids[]={R.drawable.kxg,R.drawable.mmi,R.drawable.geu,R.drawable.feb,R.drawable.fdy,R.drawable.ewo,R.drawable.mdt};

}
