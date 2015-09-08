package com.hsm.glowtextview;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class GlowActivity extends Activity {

    private GlowTextView mTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_glow);
        mTextView=(GlowTextView)findViewById(R.id.text);

        findViewById(R.id.button).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mTextView.start();
                    }
                });
	}

}
