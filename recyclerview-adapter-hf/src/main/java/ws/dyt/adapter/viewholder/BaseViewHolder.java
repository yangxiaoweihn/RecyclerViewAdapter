package ws.dyt.adapter.viewholder;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.FloatRange;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.method.MovementMethod;
import android.util.SparseArray;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class BaseViewHolder extends RecyclerView.ViewHolder implements ChainSetter<BaseViewHolder> {
    //事件(解决滑动时事件问题)
    public View eventItemView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.eventItemView = itemView;
    }

    public BaseViewHolder(View itemView, View eventItemView) {
        super(itemView);
        this.eventItemView = eventItemView;
    }

    private SparseArray<View> childViews = new SparseArray<>();

    public <T extends View> T getView(int id) {
        View childView = childViews.get(id);
        if (childView == null) {
            childView = itemView.findViewById(id);
            if (childView != null) {
                childViews.put(id, childView);
            }
        }
        return null == childView ? null : (T) childView;
    }

    @Override
    public BaseViewHolder setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        if (this.valid(view)) {
            view.setText(text);
        }
        return this;
    }

    @Override
    public BaseViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        if (this.valid(view)) {
            view.setTextColor(textColor);
        }
        return this;
    }

    @Override
    public BaseViewHolder setTextColor(int viewId, ColorStateList colorStateList) {
        TextView view = getView(viewId);
        if (this.valid(view)) {
            view.setTextColor(colorStateList);
        }
        return this;
    }

    @Override
    public BaseViewHolder setMovementMethod(int viewId, MovementMethod method) {
        TextView view = getView(viewId);
        if (this.valid(view)) {
            view.setMovementMethod(method);
        }
        return this;
    }

    @Override
    public BaseViewHolder setImageResource(int viewId, int imgResId) {
        ImageView view = getView(viewId);
        if (this.valid(view)) {
            view.setImageResource(imgResId);
        }
        return this;
    }

    @Override
    public BaseViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        if (this.valid(view)) {
            view.setImageDrawable(drawable);
        }
        return this;
    }

    @Override
    public BaseViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        if (this.valid(view)) {
            view.setImageBitmap(bitmap);
        }
        return this;
    }

    @Override
    public BaseViewHolder setImageUri(int viewId, Uri imageUri) {
        ImageView view = getView(viewId);
        if (this.valid(view)) {
            view.setImageURI(imageUri);
        }
        return this;
    }

    @Override
    public BaseViewHolder setScaleType(int viewId, ImageView.ScaleType type) {
        ImageView view = getView(viewId);
        if (this.valid(view)) {
            view.setScaleType(type);
        }
        return this;
    }

    @Override
    public BaseViewHolder setBackgroundColor(int viewId, int bgColor) {
        View view = getView(viewId);
        if (this.valid(view)) {
            view.setBackgroundColor(bgColor);
        }
        return this;
    }

    @Override
    public BaseViewHolder setBackgroundResource(int viewId, int bgRes) {
        View view = getView(viewId);
        if (this.valid(view)) {
            view.setBackgroundResource(bgRes);
        }
        return this;
    }

    @Override
    public BaseViewHolder setColorFilter(int viewId, ColorFilter colorFilter) {
        ImageView view = getView(viewId);
        if (this.valid(view)) {
            view.setColorFilter(colorFilter);
        }
        return this;
    }

    @Override
    public BaseViewHolder setColorFilter(int viewId, int colorFilter) {
        ImageView view = getView(viewId);
        if (this.valid(view)) {
            view.setColorFilter(colorFilter);
        }
        return this;
    }

    @Override
    public BaseViewHolder setAlpha(int viewId, @FloatRange(from = 0.0, to = 1.0) float value) {
        View view = getView(viewId);
        if (this.valid(view)) {
            ViewCompat.setAlpha(view, value);
        }
        return this;
    }

    @Override
    public BaseViewHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        if (this.valid(view)) {
            view.setVisibility(visibility);
        }
        return this;
    }

    @Override
    public BaseViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        if (this.valid(view)) {
            view.setMax(max);
        }
        return this;
    }

    @Override
    public BaseViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        if (this.valid(view)) {
            view.setProgress(progress);
        }
        return this;
    }

    @Override
    public BaseViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        if (this.valid(view)) {
            view.setRating(rating);
        }
        return this;
    }

    @Override
    public BaseViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        if (this.valid(view)) {
            view.setTag(tag);
        }
        return this;
    }

    @Override
    public BaseViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        if (this.valid(view)) {
            view.setTag(key, tag);
        }
        return this;
    }

    @Override
    public BaseViewHolder setEnabled(int viewId, boolean enabled) {
        View view = getView(viewId);
        if (this.valid(view)) {
            view.setEnabled(enabled);
        }
        return this;
    }

    @Override
    public BaseViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
        return this;
    }

    @Override
    public BaseViewHolder setAdapter(int viewId, RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager) {
        RecyclerView view = getView(viewId);
        if (this.valid(view)) {
            view.setAdapter(adapter);
            view.setLayoutManager(layoutManager);
        }
        return this;
    }

    @Override
    public BaseViewHolder setAdapter(int viewId, RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager, RecyclerView.ItemDecoration itemDecoration) {
        RecyclerView view = getView(viewId);
        if (this.valid(view)) {
            view.setAdapter(adapter);
            view.setLayoutManager(layoutManager);
            view.addItemDecoration(itemDecoration);
        }
        return this;
    }

    @Override
    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (this.valid(view)) {
            view.setOnClickListener(listener);
        }
        return this;
    }

    @Override
    public BaseViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        if (this.valid(view)) {
            view.setOnLongClickListener(listener);
        }
        return this;
    }

    private boolean valid(View view) {
        return null != view;
    }
}