package com.hsm.glowtextview;


import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.sax.StartElementListener;
import android.util.AttributeSet;
import android.widget.TextView;

public class GlowTextView extends TextView {

	private LinearGradient mLinearGradient;
	private Paint mPaint;
	private Matrix mMatrix;
	private float glow; //  发光的大小
	private ObjectAnimator mAnimator;
	private boolean mIsRun=false;

	public GlowTextView(Context context) {
		super(context);
	}

	public GlowTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setGlow(float glow) {
		this.glow = glow;
		invalidate();
	}

	private void init() {
		mLinearGradient = new LinearGradient(
				0, // X0
				0, // Y0
				getWidth(), //X1 这个TextView
				0,    //Y1
				new int[] { getCurrentTextColor(), //color 1
					        Color.WHITE,  //color 2
					        getCurrentTextColor()}, //color 3
				new float[] { 0, 0.5f, 1 },
				Shader.TileMode.CLAMP //模式
				);
	    mMatrix = new Matrix();
		mPaint = getPaint();
		
		// TODO Auto-generated method stub
		mAnimator = ObjectAnimator.ofFloat(this, "glow", 0, getWidth());
		mAnimator.setRepeatCount(ValueAnimator.INFINITE);
		mAnimator.setDuration(1000);
		mAnimator.setStartDelay(0);
		mAnimator.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
                postInvalidateOnAnimation();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		init();
	}
	
	public void start(){
        mIsRun=!mIsRun;
		if(!mIsRun)
			mAnimator.cancel();
		else
		    mAnimator.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		if(mIsRun){
            if (mPaint.getShader() == null) {
                mPaint.setShader(mLinearGradient);
            }
	    mMatrix.setTranslate( glow, 0); //改变的区域
		mLinearGradient.setLocalMatrix(mMatrix);
		}else{
            mPaint.setShader(null);   //恢复默认
		}
        super.onDraw(canvas);
	}

}
