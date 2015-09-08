package com.hsm.glowtextview;

import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

public class ObjectAnimatorActivity extends Activity {
	
	private int count;
	private TextView mTextView;
	
	//为count 属性添加一个setXXX方法
	public void setCount(int count) {
		this.count = count;
		mTextView.setText(""+count);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_animator);
		mTextView = (TextView) findViewById(R.id.text1);
		ObjectAnimator animator = ObjectAnimator.ofInt(this, "count", 0, 100);  
		animator.setDuration(10000); //10000/100-->100毫秒设置+1 
		animator.setInterpolator(new LinearInterpolator()); // 线性累加
		animator.setRepeatCount(ObjectAnimator.INFINITE);
		animator.start();  
	}
	
}
