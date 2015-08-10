package com.wancho.tipview;

import com.wancho.tipview.TipView.DataDrawble;
import com.wancho.tipview.TipView.DataWrapImageView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	Button btCenter;

	ImageView ivShare;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btCenter = (Button) findViewById(R.id.bt_center);
		ivShare = (ImageView) findViewById(R.id.iv_share);

		showGuide();
	}

	private void showGuide() {
		final TipView tipView = new TipView(this);
		tipView.drawWrapImageView(new DataWrapImageView(btCenter, R.drawable.tip_know, new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tipView.drop();
			}
		}));

		tipView.drawDrawble(new DataDrawble(ivShare, R.drawable.tip_share));
	}

}
