package ws.dyt.dialog;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.dialog.adapter.BaseAdapter3;
import ws.dyt.dialog.utils.DipUtil;
import ws.dyt.dialog.utils.UIUtils;

/**
 * Created by yangxiaowei on 16/5/31.
 */
abstract public class NormalDialogUtils<T extends BaseAdapter3.Entry<D>, D extends Parcelable> extends BaseDialogUtils<T, D> {
    final protected BaseDialog initBD(Context context){
        final BaseDialog bd = new BaseDialog(
                context,
                this.builder.gravity,
                this.builder.dialogWidth,
                this.builder.dialogHeight,
                this.builder.dialogAnimationResId){
            @Override
            public int setContentView() {
                return setDialogView();
            }

            @Override
            public List<Integer> setGoneViewList() {
                return null;
            }

        };
        return bd;
    };


    /**
     * 设置对话框布局文件资源
     * @return
     */
    protected abstract @LayoutRes int setDialogView();

    class ViewHolder{
        public TextView textView;

        public ViewHolder(View rootView) {
            textView = (TextView) rootView.findViewById(R.id.tv_tpl);
        }
    }


    final public void build(Context context){
//        if (builder.gravity != Gravity.BOTTOM) {
//            this.buildCommonDialog(context);
//        }else {
//            this.buildListDialog(context);
//        }
        this.buildCommonDialog(context);
    }

    protected void buildCommonDialog(Context context){}

    protected void buildListDialog(Context context){}



    /**
     *
     * @param bd
     */
    final protected void initAnyViewClickCallback(final BaseDialog bd){
        if (null != this.builder.onClickCallback && 0 != this.builder.anyViewId) {
            View view = findView(this.builder.anyViewId, bd.contentView);
            if (null == view) {
                return;
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.onClickCallback.onClick(bd.dialog, v, OnClickCallback.Type.UNKNOW);
                }
            });
        }
    }

    //---------------------------------------------------------------------------------

    /**
     * @param gravity @{@link Gravity}
     * @return
     */
    public NormalDialogUtils setDialogGravity(int gravity){
        this.builder.gravity = gravity;
        return this;
    }

    public NormalDialogUtils setDialogSize(int width, int height){
        this.builder.dialogWidth = width;
        this.builder.dialogHeight = height;
        return this;
    }

    public NormalDialogUtils setDialogSize(Context context, int marginDpLeftAndRight, int height){
        int w = UIUtils.getScreenSize(context)[0] - DipUtil.dip2px(context, marginDpLeftAndRight);
        return this.setDialogSize(w, height);
    }

    public NormalDialogUtils setDialogAnimation(@StyleRes int animation){
        this.builder.dialogAnimationResId = animation;
        return this;
    }

    public NormalDialogUtils setDialogCanceledOnTouchOutside(boolean canceledOnTouchOutside){
        this.builder.canceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }

    public NormalDialogUtils isHideKeyBack(boolean hide){
        this.builder.hideKeyBack = hide;
        return this;
    }

    public NormalDialogUtils isDialogCorners(boolean isCorners){
        this.builder.dialogIsCorners = isCorners;
        return this;
    }

    private NormalDialogUtils setTitleView(View titleView){
        return this;
    }
    public NormalDialogUtils setTitleView(@LayoutRes int titleView){
        this.builder.titleViewLayoutId = titleView;
        return this;
    }

    public NormalDialogUtils setTitle(CharSequence title){
        this.builder.title = title;
        return this;
    }

    public NormalDialogUtils setContent(CharSequence content){
        this.builder.content = content;
        return this;
    }

    public NormalDialogUtils setNagetiveBtn(CharSequence nagetiveBtnText, OnClickCallback callback){
        return this.setNagetiveBtn(R.id.dl_tv_nagetive, nagetiveBtnText, callback);
    }

    public NormalDialogUtils setNagetiveBtn(@IdRes int nagetiveBtnId, CharSequence nagetiveBtnText, OnClickCallback callback){
        if (nagetiveBtnId > 0) {
            this.builder.nagetiveBtnId = nagetiveBtnId;
        }
        this.builder.nagetiveBtnText = nagetiveBtnText;
        this.builder.onClickCallback = callback;
        return this;
    }

    public NormalDialogUtils setPositiveBtn(CharSequence positiveBtnText, OnClickCallback callback){
        return this.setPositiveBtn(R.id.dl_tv_positive, positiveBtnText, callback);
    }

    public NormalDialogUtils setPositiveBtn(@IdRes int positiveBtnId, CharSequence positiveBtnText, OnClickCallback callback){
        if (positiveBtnId > 0) {
            this.builder.positiveBtnId = positiveBtnId;
        }
        this.builder.positiveBtnText = positiveBtnText;
        this.builder.onClickCallback = callback;
        return this;
    }

    /**
     * 任意控件点击监听器
     * @param id
     * @param callback
     * @return
     */
    public NormalDialogUtils setViewOnClickCallback(@IdRes int id, OnClickCallback callback){
        this.builder.anyViewId = id;
        this.builder.onClickCallback = callback;
        return this;
    }

    public NormalDialogUtils setListData(List<T> datas, OnItemClickListener<T> onItemClickListener){
        this.builder.listData = datas;
        this.builder.onItemClickListener = onItemClickListener;
        return this;
    }

    private NormalDialogUtils setOperateView(View operateView) {
        return this;
    }

    public NormalDialogUtils setOperateView(@LayoutRes int operateView){
        this.builder.operateViewLayoutId = operateView;
        return this;
    }
    //---------------------------------------------------------------------------------

    public NormalDialogUtils setOnConvertItemCallback(OnConvertItemCallback convertCallback){
        this.builder.onConvertItemCallback = convertCallback;
        return this;
    }

    public NormalDialogUtils setOnConvertCallback(OnConvertCallback convertCallback){
        this.builder.onConvertCallback = convertCallback;
        return this;
    }

    public NormalDialogUtils setOnConvertTitleCallback(OnConvertTitleCallback convertCallback){
        this.builder.onConvertTitleCallback = convertCallback;
        return this;
    }

    public NormalDialogUtils setOnConvertContentCallback(OnConvertContentCallback convertCallback){
        this.builder.onConvertContentCallback = convertCallback;
        return this;
    }

    public NormalDialogUtils setOnConvertOperateCallback(OnConvertOperateCallback convertCallback){
        this.builder.onConvertOperateCallback = convertCallback;
        return this;
    }
}
