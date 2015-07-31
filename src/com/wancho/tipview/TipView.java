package com.wancho.tipview;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
/**
 * 引导帮助提示了类
 * 为锚点View添加图片提示 {@link #drawDrawble(DataTipDrawble)}</p>
 * 为锚点View添加View提示 {@link #drawView(DataTipView)}
 * @author Wancho
 *
 */
@SuppressLint("DrawAllocation")
public class TipView extends RelativeLayout {

	private Context mContext;
	
	private List<DataTipDrawble> drawbles;
	
	private List<DataTipView> views;
	
	public TipView(Context context) {
		super(context);
		this.mContext = context;
		setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {

			}
		});
		drawbles = new ArrayList<TipView.DataTipDrawble>(10);
		views = new ArrayList<TipView.DataTipView>(1);
	}
	
	public TipView builder() {
		if (mContext instanceof Activity) {
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			Activity activity = (Activity) mContext;
			ViewGroup vgDecorView = (ViewGroup) activity.getWindow().getDecorView();
			if (vgDecorView.indexOfChild(this) == -1) {
				vgDecorView.addView(this, lp);
			}
			removeAllViews();
		}
		return this;
	}
	
	public void drawDrawble(DataTipDrawble dataTipDrawble) {
		drawbles.add(dataTipDrawble);
		invalidate();
	}
	
	public void drop() {
		if (mContext instanceof Activity) {
			Activity activity = (Activity) mContext;
			ViewGroup vgDecorView = (ViewGroup) activity.getWindow().getDecorView();
			if (vgDecorView.indexOfChild(this) != -1) {
				vgDecorView.removeView(this);
			}
		}
	}
	
	public void drawView(DataTipView dataTipView) {
		views.add(dataTipView);
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (DataTipDrawble data : drawbles) {
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), data.getImgRes());
			Point point = getLeftAndTopPoint(data.getViewTarget(), bitmap);
			canvas.drawBitmap(bitmap, point.x, point.y, null);
		}
	}

	private Point getLeftAndTopPoint(View viewTarget, Bitmap bitmap) {
		Point point = new Point();
		int[] location = new int[2];
		viewTarget.getLocationInWindow(location);
		point.x = location[0] + viewTarget.getWidth() / 2 - bitmap.getWidth() / 2;
		point.y = location[1] + viewTarget.getHeight() / 2 - bitmap.getHeight() / 2;
		return point;
	}

	private Point getLeftAndTopPoint(View viewTarget, View viewTip) {
		Point point = new Point();
		int[] location = new int[2];
		viewTarget.getLocationInWindow(location);
		viewTip.measure(0, 0);
		point.x = location[0] + viewTarget.getWidth() / 2 - viewTip.getMeasuredWidth() / 2;
		point.y = location[1] + viewTarget.getHeight() / 2 - viewTip.getMeasuredHeight() / 2;
		return point;
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		RelativeLayout.LayoutParams lpChild = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		for (DataTipView data : views) {
			View viewTarget = data.getViewTarget();
			View viewTip = data.getViewTip();
			if (indexOfChild(viewTip) == -1) {
				Point point = getLeftAndTopPoint(viewTarget, viewTip);
				lpChild.leftMargin = point.x;
				lpChild.topMargin = point.y;
				addView(viewTip, lpChild);
			}
		}
	}
	
    public static class DataTipDrawble {
    	
    	View viewTarget;
    	
    	int imgRes;

		public DataTipDrawble(View viewTarget, int imgRes) {
			super();
			this.viewTarget = viewTarget;
			this.imgRes = imgRes;
		}

		public View getViewTarget() {
			return viewTarget;
		}

		public void setViewTarget(View viewTarget) {
			this.viewTarget = viewTarget;
		}

		public int getImgRes() {
			return imgRes;
		}

		public void setImgRes(int imgRes) {
			this.imgRes = imgRes;
		}
    }
	
	public static class DataTipView {

		View viewTarget;
		
		View viewTip;
		
		public DataTipView(View viewTarget, View viewTip) {
			super();
			this.viewTarget = viewTarget;
			this.viewTip = viewTip;
		}

		public View getViewTarget() {
			return viewTarget;
		}

		public View getViewTip() {
			return viewTip;
		}
	}

}
