package ws.dyt.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.dialog.adapter.BaseAdapter3;

/**
 * Created by yangxiaowei on 16/6/5.
 */
public class N<T extends BaseAdapter3.Entry<D>, D extends Parcelable> extends NormalDialogUtils<T, D>  {

    @Override
    protected int setDialogView() {
        //根据是否有列表数据进行加载不同布局
        return (null == builder.listData || builder.listData.isEmpty()) ? R.layout.dl_dialog_normal_v2 : R.layout.dl_dialog_normal_v3;
    }

    @Override
    protected void buildCommonDialog(Context context){
        final List<Integer> ids = new ArrayList<>();
        if (TextUtils.isEmpty(this.builder.nagetiveBtnText)){
            ids.add(builder.nagetiveBtnId);
            ids.add(R.id.dl_view_divider_v);
        }
        if (TextUtils.isEmpty(this.builder.positiveBtnText)){
            ids.add(builder.positiveBtnId);
            ids.add(R.id.dl_view_divider_v);
        }

        if (TextUtils.isEmpty(this.builder.title)){
            ids.add(R.id.dl_container_title);
        }
        if (TextUtils.isEmpty(this.builder.content)){
            ids.add(R.id.dl_container_content);
        }

        final BaseDialog bd = initBD(context);

//        bd.dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);

        ViewGroup titleView = findView(R.id.dl_container_title, bd.contentView);
        TextView tvTitle = findView(R.id.dl_tv_title, titleView);

        ViewGroup contentView = findView(R.id.dl_container_content, bd.contentView);
        TextView tvContent = findView(R.id.dl_tv_content, contentView);


        if (!TextUtils.isEmpty(this.builder.title) && null != tvTitle){
            tvTitle.setText(this.builder.title);
        }

        if (!TextUtils.isEmpty(this.builder.content) && null != tvContent){
//            tvContent.setGravity(contentGravity);
            tvContent.setText(this.builder.content);
        }

        ListView listView = findView(R.id.listView, bd.contentView);
        if (null != listView) {
            this.initListView(context, bd.dialog, listView);
        }

        View.OnClickListener ll = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                bd.dialog.dismiss();
                if (null != builder.onClickCallback){
                    @OnClickCallback.TypeWhere int which = OnClickCallback.Type.NAGETIVE;
                    if (builder.nagetiveBtnId == id) {
                        which = OnClickCallback.Type.NAGETIVE;
                    }else if (builder.positiveBtnId == id) {
                        which = OnClickCallback.Type.POSITIVE;
                    }
                    builder.onClickCallback.onClick(bd.dialog, view, which);
                }
            }
        };

        //如果没有设置过操作栏布局，则默认
        if (this.builder.operateViewLayoutId == R.layout.dl_item_operate) {
            this.builder.operateViewLayoutId = R.layout.dl_dialog_normal_operate;
        }

        ViewGroup operateVG = findView(R.id.dl_container_operate, bd.contentView);
        View operate = View.inflate(context, this.builder.operateViewLayoutId, null);
        operateVG.addView(operate);

        bd.goneView(ids);

        TextView positiveBtn = findView(this.builder.positiveBtnId, operate);
        if (null != positiveBtn) {
            positiveBtn.setText(this.builder.positiveBtnText);
            positiveBtn.setOnClickListener(ll);
        }

        TextView negativeBtn = findView(this.builder.nagetiveBtnId, operate);
        if (null != negativeBtn) {
            negativeBtn.setText(this.builder.nagetiveBtnText);
            negativeBtn.setOnClickListener(ll);
        }

        //外布局
        if (this.builder.dialogViewLayoutId < 0) {
            int br = this.builder.dialogIsCorners ? R.drawable.bg_corner_white : R.drawable.bg_rectangle_selector;
            bd.contentView.setBackgroundResource(br);
        }

        if ((TextUtils.isEmpty(this.builder.positiveBtnText) || TextUtils.isEmpty(this.builder.nagetiveBtnText))) {
            //默认操作栏处理
            if (this.builder.operateViewLayoutId == R.layout.dl_dialog_normal_operate) {
                //处理操作栏控件圆角直角问题（目前操作栏默认有两个控件）
                List<View> views = new ArrayList<>();
                views.add(findView(this.builder.positiveBtnId, operate));
                views.add(findView(this.builder.nagetiveBtnId, operate));
                if (!views.isEmpty()) {
                    for (View e:views) {
                        if (null == e || View.VISIBLE != e.getVisibility()) {
                            continue;
                        }
                        int br = this.builder.dialogIsCorners ? R.drawable.bg_corner_bottom_all_selector : R.drawable.bg_rectangle_selector;
                        e.setBackgroundResource(br);
                    }
                }
            }
        }

        this.initAnyViewClickCallback(bd);

        //convert callback
        if (null != this.builder.onConvertTitleCallback) {
            this.builder.onConvertTitleCallback.convertTitleView(titleView, tvTitle);
        }
        if (null != this.builder.onConvertContentCallback) {
            this.builder.onConvertCallback.convertContentView(contentView, tvContent);
        }
        if (null != this.builder.onConvertOperateCallback) {
            this.builder.onConvertOperateCallback.convertOperateView(operate, negativeBtn, positiveBtn);
        }
        if (null != this.builder.onConvertCallback) {
            this.builder.onConvertCallback.convertOperateView(operate, negativeBtn, positiveBtn);
            this.builder.onConvertCallback.convertTitleView(titleView, tvTitle);
            this.builder.onConvertCallback.convertContentView(contentView, tvContent);
        }

    }

    private void initListView(Context context, final Dialog dialog,  final ListView listView){
        listView.setAdapter(new BaseAdapter3<T, D>(context, builder.listData) {
            @Override
            public View getBaseConvertView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                if (null == convertView) {
                    convertView = layoutInflater.inflate(R.layout.dl_item_data, parent, false);
                    viewHolder = new ViewHolder(convertView);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                T e = getItem(position);
                viewHolder.textView.setText(e.showName);
                return convertView;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int hc = listView.getHeaderViewsCount();
                int index = i - hc;
                dialog.dismiss();
                if (null != builder.onItemClickListener) {
                    builder.onItemClickListener.onItemClick(builder.listData.get(index), index);
                }
            }
        });
    }
}
