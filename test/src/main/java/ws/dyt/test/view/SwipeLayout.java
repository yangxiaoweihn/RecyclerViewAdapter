package ws.dyt.test.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
/**
 * 侧滑删除
 * 
 * @author sunny
 *
 * @date  2016年1月31日
 *
 * @site www.sunnyang.com
 */
@SuppressLint("ClickableViewAccessibility")
public class SwipeLayout extends FrameLayout {

	private ViewDragHelper dragHelper;
	private OnSwipeChangeListener swipeChangeListener;
	private Status status=Status.CLOSE;//拖拽状态 默认关闭

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public static enum Status {
		OPEN, CLOSE, DRAGING
	}

	//拖拽事件监听器
	public static interface OnSwipeChangeListener {
		void onDraging(SwipeLayout mSwipeLayout);

		void onOpen(SwipeLayout mSwipeLayout);

		void onClose(SwipeLayout mSwipeLayout);
		
		void onStartOpen(SwipeLayout mSwipeLayout);
		
		void onStartClose(SwipeLayout mSwipeLayout);
		
	}

	//重写三个构造方法
	public SwipeLayout(Context context) {
		this(context, null);
	}

	public SwipeLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		dragHelper = ViewDragHelper.create(this, callback);
	}

	private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

		//所有子View都可拖拽
		public boolean tryCaptureView(View child, int pointerId) {
			return true;
		}

		//水平拖拽后处理
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			if (child == frontView) {
				if (left > 0) {
					return 0;
				} else if (left < -range) {
					return -range;
				}
			} else if (child == backView) {
				if (left > width) {
					return width;
				} else if (left < width - range) {
					return width - range;
				}
			}
			return left;
		}

		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
			if (changedView == frontView) {
				backView.offsetLeftAndRight(dx);
			} else if (changedView == backView) {
				frontView.offsetLeftAndRight(dx);
			}
			//事件派发
			dispatchSwipeEvent();
			//兼容低版本
			invalidate();
		};
		
		//松手后根据侧滑位移确定菜单打开与否
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			if (xvel == 0 && frontView.getLeft() < -range * 0.5f) {
				open();
			} else if (xvel < 0) {
				open();
			} else {
				close();
			}
		};
		
		//子View如果是clickable，必须重写的方法
		public int getViewHorizontalDragRange(View child) {
			return 1;
		}

		public int getViewVerticalDragRange(View child) {
			return 1;
		}
	};

	//根据当前状态判断回调事件
	protected void dispatchSwipeEvent() {
		Status preStatus=status;
		status=updateStatus();
		
		if(swipeChangeListener!=null){
			swipeChangeListener.onDraging(this);
		}
		
		if(preStatus!=status&&swipeChangeListener!=null){
			if(status==Status.CLOSE){
				swipeChangeListener.onClose(this);
			}else if(status==Status.OPEN){
				swipeChangeListener.onOpen(this);
			}else if(status==Status.DRAGING){
				if(preStatus==Status.CLOSE){
					swipeChangeListener.onStartOpen(this);
				}else if(preStatus==Status.OPEN){
					swipeChangeListener.onStartClose(this);
				}
			}
		}
	}

	//更改状态
	private Status updateStatus() {
		int left=frontView.getLeft();
		if(left==0){
			return Status.CLOSE;
		}else if(left==-range){
			return Status.OPEN;
		}
		return Status.DRAGING;
	}

	private View backView;//侧滑菜单
	private View frontView;//内容区域
	private int height;//自定义控件布局高
	private int width;//自定义控件布局宽
	private int range;//侧滑菜单可滑动范围

	// 持续平滑动画 高频调用
	public void computeScroll() {
		// 如果返回true，动画还需要继续
		if (dragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
	};

	public void open() {
		open(true);
	}

	public void open(boolean isSmooth) {
		int finalLeft = -range;
		if (isSmooth) {
			if (dragHelper.smoothSlideViewTo(frontView, finalLeft, 0)) {
				ViewCompat.postInvalidateOnAnimation(this);
			}
		} else {
			layoutContent(true);
		}
	}

	public void close() {
		close(true);
	}

	public void close(boolean isSmooth) {
		int finalLeft = 0;
		if (isSmooth) {
			if (dragHelper.smoothSlideViewTo(frontView, finalLeft, 0)) {
				ViewCompat.postInvalidateOnAnimation(this);
			}
		} else {
			layoutContent(false);
		}
	}

	//布局子View
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		layoutContent(false);

	}

	/**
	 * @param isOpen 侧滑菜单是否打开
	 */
	private void layoutContent(boolean isOpen) {
		Rect frontRect = computeFrontViewRect(isOpen);
		frontView.layout(frontRect.left, frontRect.top, frontRect.right, frontRect.bottom);

		Rect backRect = computeBackViewRect(frontRect);
		backView.layout(backRect.left, backRect.top, backRect.right, backRect.bottom);

		//调整顺序
//		bringChildToFront(frontView);
	}

	/**
	 * 通过内容区域所占矩形坐标计算侧滑菜单的矩形位置区域
	 * @param frontRect 内容区域所占矩形
	 * @return
	 */
	private Rect computeBackViewRect(Rect frontRect) {
		int left = frontRect.right;
		return new Rect(left, 0, left + range, height);
	}

	/**
	 * 通过菜单打开与否isOpen计算内容区域的矩形区
	 * @param isOpen
	 * @return
	 */
	private Rect computeFrontViewRect(boolean isOpen) {
		int left = 0;
		if (isOpen) {
			left = -range;
		}
		return new Rect(left, 0, left + width, height);
	}

	//获取两个View
	protected void onFinishInflate() {
		super.onFinishInflate();

		int childCount = getChildCount();
		if (childCount < 2) {
			throw new IllegalStateException("you need 2 children view");
		}
		if (!(getChildAt(0) instanceof ViewGroup) || !(getChildAt(1) instanceof ViewGroup)) {
			throw new IllegalArgumentException("your children must be instance of ViewGroup");
		}

		backView = getChildAt(0);//侧滑菜单
		frontView = getChildAt(1);//内容区域

	}

	//初始化布局的高height宽width以及可滑动的范围range
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		height = frontView.getMeasuredHeight();
		width = frontView.getMeasuredWidth();
		range = backView.getMeasuredWidth();
	}

	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return dragHelper.shouldInterceptTouchEvent(ev);
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		dragHelper.processTouchEvent(event);
		return true;
	}

	public void setSwipeChangeListener(OnSwipeChangeListener swipeChangeListener) {
		this.swipeChangeListener = swipeChangeListener;
	}

}
