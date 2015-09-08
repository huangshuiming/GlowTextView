package com.hsm.glowtextview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

public class LinearGradientTextView extends TextView {

	private LinearGradient mLinearGradient;
	private Paint mPaint;
	private Matrix mMatrix;

	public LinearGradientTextView(Context context) {
		super(context);
	}

	public LinearGradientTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private void init() {
		mLinearGradient = new LinearGradient(
				0, // X0
				0, // Y0
				getWidth(), //X1 
				0,    //Y1
				new int[] { getCurrentTextColor(), //color 1
					        Color.WHITE,  //color 2
					        getCurrentTextColor()}, //color 3
				new float[] { 0, 0.5f, 1 },
				Shader.TileMode.REPEAT //模式
				);
	    mMatrix = new Matrix();
		mPaint = getPaint();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		init();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		mPaint.setShader(mLinearGradient);
		mLinearGradient.setLocalMatrix(mMatrix);
	}
}
