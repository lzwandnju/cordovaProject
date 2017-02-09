package com.worksap.ilock.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;

import com.qq.e.splash.SplashAd;
import com.qq.e.splash.SplashAdListener;
import com.worksap.ilock.screen.activity.GestureVerifyActivity;
import com.worksap.ilock.screen.ad.Constants;
import com.worksap.ilock.screen.application.MyApplication;
import com.worksap.ilock.screen.utils.SPUtils;


public class SplashActivity extends Activity {

	private FrameLayout frameLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		frameLayout = (FrameLayout) findViewById(R.id.splashcontainer);
		SplashAd();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

	}

	private void SplashAd() {
		new SplashAd(this, frameLayout, Constants.APPId, Constants.SplashPosId,
				new SplashAdListener() {

					@Override
					public void onAdPresent() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAdFailed(int arg0) {
						// TODO Auto-generated method stub
						new Handler().postDelayed(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (!SPUtils
										.get(MyApplication.getContext(),
												"gesturePsd", "").toString()
										.equals("")) {
									Intent intent = new Intent();
									intent.setClass(MyApplication.getContext(),
											GestureVerifyActivity.class);
									startActivity(intent);
									SplashActivity.this.finish();
								} else {
									Intent intent = new Intent();
									intent.setClass(MyApplication.getContext(),
											LoginActivity.class);
									startActivity(intent);
									SplashActivity.this.finish();
								}
							}
						}, 1000);
					}

					@Override
					public void onAdDismissed() {
						// TODO Auto-generated method stub
						new Handler().postDelayed(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (!SPUtils
										.get(MyApplication.getContext(),
												"gesturePsd", "").toString()
										.equals("")) {
									Intent intent = new Intent();
									intent.setClass(MyApplication.getContext(),
											GestureVerifyActivity.class);
									startActivity(intent);
									SplashActivity.this.finish();
								} else {
									Intent intent = new Intent();
									intent.setClass(MyApplication.getContext(),
											LoginActivity.class);
									startActivity(intent);
									SplashActivity.this.finish();
								}
							}
						}, 1000);
					}
				});
	}
}
