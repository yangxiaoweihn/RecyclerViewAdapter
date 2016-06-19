package ws.dyt.dialog;

import android.app.Dialog;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import ws.dyt.dialog.adapter.BaseAdapter3;

/**
 * Created by yangxiaowei on 16/5/31.
 */
public class BaseDialogUtils<T extends BaseAdapter3.Entry<D>, D extends Parcelable> {

    protected <V extends View> V findView(@IdRes int id, View parent){
        if (null == parent) {
            return null;
        }
        View v = parent.findViewById(id);
        return null == v ? null : (V) v;
    }

    protected Builder builder = new Builder();
    protected class Builder{
        public CharSequence title = null;
        public CharSequence content = null;
        public @IdRes int nagetiveBtnId = R.id.dl_tv_nagetive;
        public CharSequence nagetiveBtnText = null;
        public @IdRes int positiveBtnId = R.id.dl_tv_positive;
        public CharSequence positiveBtnText = null;
        //通过
        public @IdRes int anyViewId = 0;
        public List<T> listData = null;

        //对话框位置
        public int gravity = Gravity.CENTER;
        public boolean canceledOnTouchOutside = true;
        //是否屏蔽返回键
        public boolean hideKeyBack = false;
        public int dialogWidth = WindowManager.LayoutParams.MATCH_PARENT;
        public int dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        public @StyleRes int dialogAnimationResId = 0;
        //对话框是否有圆角
        public boolean dialogIsCorners = true;

        public OnItemClickListener onItemClickListener = null;
        //对话框整体布局，为-1时表示不是调用者定制
        public int dialogViewLayoutId = -1;
        //标题栏
        public int titleViewLayoutId = R.layout.dl_item_title;
        //操作栏
        public int operateViewLayoutId = R.layout.dl_item_operate;
        //data item
        public int dataItemLayoutId = R.layout.dl_item_data;

        public OnConvertCallback onConvertCallback = null;
        public OnConvertTitleCallback onConvertTitleCallback = null;
        public OnConvertContentCallback onConvertContentCallback = null;
        public OnConvertOperateCallback onConvertOperateCallback = null;
        public OnConvertItemCallback onConvertItemCallback = null;

        public OnClickCallback onClickCallback = null;
    }

    /**
     * 点击监听器
     */
    public interface OnClickCallback{
        interface Type{
            int UNKNOW       = -404;
            int NAGETIVE     = 0;
            int POSITIVE     = 1;
        }
        @Retention(RetentionPolicy.SOURCE)
        @IntDef({Type.UNKNOW, Type.NAGETIVE, Type.POSITIVE})
        @interface TypeWhere{}

        /**
         * @param dialog
         * @param view
         * @param which {@link ws.dyt.dialog.BaseDialogUtils.OnClickCallback.Type}
          *      为 UNKNOW 时为自定义控件
         */
        void onClick(Dialog dialog, View view, @TypeWhere int which);
    }

    /**
     * list item 监听器
     * @param <T>
     */
    public interface OnItemClickListener<T>{
        /**
         * 列表数据选中回调
         * @param item          选中数据
         * @param position      数据位置索引
         */
        void onItemClick(T item, int position);
    }

    /**
     * 列表数据转换，可以根据回调处理列表样式
     */
    public interface OnConvertItemCallback{
        void convertItemView(View itemView);
    }

    /**
     * 标题控件转换回调，可以根据回调处理标题样式
     */
    public interface OnConvertTitleCallback{
        /**
         * 标题栏控件转换
         * @param titleView     标题控件容器
         * @param titleTextView 默认情况下标题控件
         */
        void convertTitleView(View titleView, TextView titleTextView);
    }

    /**
     * 内容控件转换回调，可以根据回调处理内容样式
     */
    public interface OnConvertContentCallback{
        /**
         * 内容区域控件转换
         * @param contentView       内容控件容器      自定义时可以通过该容器查找控件
         * @param contentTextView   默认情况下内容控件
         */
        void convertContentView(View contentView, TextView contentTextView);
    }

    /**
     * 操作栏控件转换回调，可以根据回调处理操作栏样式
     */
    public interface OnConvertOperateCallback{
        /**
         * 操作栏区域控件转换
         * @param operateView       操作栏控件容器     自定义时可以通过该容器查找控件
         * @param negativeTextView  默认情况下cancel tv
         * @param positiveTextView  默认情况下ok tv
         */
        void convertOperateView(View operateView, TextView negativeTextView, TextView positiveTextView);
    }

    /**
     * 控件转换回调，可以根据回调处理样式
     */
    public interface OnConvertCallback extends OnConvertTitleCallback, OnConvertContentCallback, OnConvertOperateCallback{}

}
