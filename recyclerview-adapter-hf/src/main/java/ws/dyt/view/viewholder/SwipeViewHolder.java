package ws.dyt.view.viewholder;

/**
 * Created by yangxiaowei on 16/8/2.
 */
final
public class SwipeViewHolder {
    private BaseViewHolder baseViewHolder;

    public SwipeViewHolder(BaseViewHolder baseViewHolder) {
        this.baseViewHolder = baseViewHolder;
    }

    public BaseViewHolder getBaseViewHolder() {
        return baseViewHolder;
    }
}
