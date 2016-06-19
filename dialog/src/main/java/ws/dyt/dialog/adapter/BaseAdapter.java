package ws.dyt.dialog.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @date	2015年2月27日	上午10:10:12
 * @function
 *
 * @param <T>
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter{
	public LayoutInflater layoutInflater = null;
	public Resources resources = null;
	public List<T> datas = new ArrayList<T>();

	public BaseAdapter(Context context, List<T> data){
		this.layoutInflater = LayoutInflater.from(context);
//		this.datas = null == data ? new ArrayList<T>() : new ArrayList<T>(data);
		if(null != data && !data.isEmpty()){
			datas.addAll(data);
		}
		this.resources = context.getResources();
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年7月19日	上午11:33:26
	 * @function 添加对象列表，添加之前处理好判空操作
	 *
	 * @param data
	 */
	public void addDataOnly(List<T> data){
		if(data.contains(null)){
			data.remove(null);
		}
		this.datas.addAll(data);
	}

	/**
	 *
	 * @param data
	 */
	public void addData(T data) {
		this.datas.add(data);
	}

	public void addData(int position, T data) {
		this.datas.add(position, data);
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年7月19日	上午11:33:26
	 * @function 添加对象列表，添加之前处理好判空操作
	 *
	 * @param data
	 */
	public void addDataAfterClean(List<T> data){
		this.datas.clear();
		this.addDataOnly(data);
	}

	public void removeData(T t){
		if(null != this.datas && !this.datas.isEmpty() && this.datas.contains(t)){
			this.datas.remove(t);
		}
	}

	public void clearData(){
		if(null == this.datas || this.datas.isEmpty()){
			return ;
		}
		this.datas.clear();
	}

	public Object getRealData(){
		return this.datas;
	}

	@Override
	public int getCount() {
		return null == this.datas ? 0 : this.datas.size();
	}

	@Override
	public T getItem(int position) {
		return null == this.datas ? null : this.datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return this.getBaseConvertView(position, convertView, parent);
	}

	public abstract View getBaseConvertView(int position, View convertView, ViewGroup parent);
}
