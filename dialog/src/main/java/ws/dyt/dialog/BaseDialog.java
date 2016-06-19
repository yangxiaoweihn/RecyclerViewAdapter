package ws.dyt.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;



public abstract class BaseDialog {
	public Dialog dialog = null;
	private Context context = null;
	private LayoutInflater layoutInflater = null;
	public BaseDialog(Context context){
		this.context = context;
		this.init(-1, 0.5f);
	}

	private int gravity = Gravity.CENTER;
	private int bdWidth = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
	private int bdHeight = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
	/**
	 * @constructor
	 * @function
	 *
	 * @param context
	 * @param gravity
	 * @param bdWidth
	 * @param bdHeight
	 */
	public BaseDialog(Context context, int gravity, int bdWidth, int bdHeight){
		this(context, gravity, bdWidth, bdHeight, -1);
	}

	public BaseDialog(Context context, int gravity, int bdWidth, int bdHeight, int animationStyleId){
		this(context, gravity, bdWidth, bdHeight, 0.5f, animationStyleId);
	}

	public BaseDialog(Context context, int gravity, int bdWidth, int bdHeight, float dimAmount, int animationStyleId){
		this.context = context;
		this.gravity = gravity;
		this.bdWidth = bdWidth;
		this.bdHeight = bdHeight;
		this.init(animationStyleId, dimAmount);
	}

	private void init(int animationStyleId, float dimAmount){
		dialog = new AlertDialog.Builder(context, R.style.DialogTheme).create();
		layoutInflater = dialog.getLayoutInflater();
		dialog.show();
		this.initAttrs(animationStyleId, dimAmount);
	}

	private void initAttrs(int animationStyleId, float dimAmount){
		android.view.WindowManager.LayoutParams dialogWMLP = dialog.getWindow().getAttributes();
		dialogWMLP.alpha = 1.0f;
		dialogWMLP.dimAmount = dimAmount;
		dialogWMLP.gravity = gravity;
		dialogWMLP.width = bdWidth;
		dialogWMLP.height = bdHeight;
		dialog.getWindow().setAttributes(dialogWMLP);
		if(animationStyleId >= 0){
			dialog.getWindow().setWindowAnimations(animationStyleId);
		}
		dialog.setCanceledOnTouchOutside(false);

		this.contentView = layoutInflater.inflate(setContentView(), null);
		dialog.setContentView(this.contentView);
		List<Integer> list = setGoneViewList();
		this.goneView(list);
	}

	public void goneView(List<Integer> views) {
		List<Integer> list = views;
		if(null != list && !list.isEmpty()){
			for(int id:list){
				View v = findViweById(id);
				if (null == v) {
					continue;
				}
				v.setVisibility(View.GONE);
			}
		}
	}

	public View contentView = null;
	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年8月5日	上午10:25:36
	 * @function 设置对话框布局
	 *
	 * @return
	 */
	public abstract int setContentView();

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年8月5日	上午10:27:53
	 * @function 设置一些不显示的控件
	 *
	 * @return
	 */
	public abstract List<Integer> setGoneViewList();

//	protected boolean isShieldDialogOnBackPressed() {
//	}


	public <T extends View> T findViweById(int id){
		View view = contentView.findViewById(id);
		return null == view ? null : (T) view;
	}
}
