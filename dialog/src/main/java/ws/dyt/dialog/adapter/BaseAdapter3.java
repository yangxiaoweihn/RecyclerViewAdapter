package ws.dyt.dialog.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @date	2015年2月27日	上午10:10:12
 * @function	只适合单行文本显示&&兼模糊匹配功能
 *
 * @param <T>	item包装数据
 * @param <D>	包装数据中的真实数据，提供给item使用
 */
public abstract class BaseAdapter3<T extends BaseAdapter3.Entry<D>, D extends Parcelable> extends BaseAdapter<T>{

	/**
	 * 适配器数据item
	 * @param <D>
	 */
	static public class Entry<D extends Parcelable> implements Parcelable {
		//显示的名字
		public String showName;
		//模糊匹配名字
		public String likeMatchName;
		//真实数据
		public D dataT;

		public Entry(String showName, D dataT) {
			this.showName = showName;
			this.dataT = dataT;
		}

		public Entry(String showName, String likeMatchName, D dataT) {
			this(showName, dataT);
			this.likeMatchName = likeMatchName;
		}


		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(this.showName);
			dest.writeString(this.likeMatchName);
			Bundle bundle = new Bundle();
			bundle.putParcelable("DATA", this.dataT);
			dest.writeBundle(bundle);
		}

		protected Entry(Parcel in) {
			this.showName = in.readString();
			this.likeMatchName = in.readString();
			Bundle bundle = in.readBundle();
			bundle.setClassLoader(getClass().getClassLoader());
			this.dataT = bundle.getParcelable("DATA");
		}

		public static final Creator<Entry> CREATOR = new Creator<Entry>() {
			public Entry createFromParcel(Parcel source) {
				return new Entry(source);
			}

			public Entry[] newArray(int size) {
				return new Entry[size];
			}
		};
	}

	public BaseAdapter3(Context context, List<T> data){
		super(context, data);
	}

	public abstract View getBaseConvertView(int position, View convertView, ViewGroup parent);
}
