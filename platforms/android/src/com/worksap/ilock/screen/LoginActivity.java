package com.worksap.ilock.screen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.qq.e.ads.AdListener;
import com.qq.e.ads.AdRequest;
import com.qq.e.ads.AdSize;
import com.qq.e.ads.AdView;
import com.worksap.ilock.screen.activity.GestureEditActivity;
import com.worksap.ilock.screen.ad.Constants;
import com.worksap.ilock.screen.application.MyApplication;
import com.worksap.ilock.screen.utils.SPUtils;
import com.worksap.ilock.screen.utils.ToastUtil;



public class LoginActivity extends BaseActivity implements OnClickListener {

	private Button loginButton;
	private EditText usernameET, userpsdET;

	private AdView bannerAD;
	private LinearLayout bannerContainer;
//    private LockScreenAPI lockScreen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();

	}

	private void initView() {
		bannerContainer = (LinearLayout) findViewById(R.id.bannerad);
		usernameET = (EditText) findViewById(R.id.editText_username);
		userpsdET = (EditText) findViewById(R.id.editText_userpsd);
		loginButton = (Button) findViewById(R.id.button_login);
		loginButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.button_login:
			if (usernameET.getText().toString().trim().equals("")
					|| userpsdET.getText().toString().trim().equals("")) {
				ToastUtil.showLong(this, "用户名或密码不能为空！");
			} else {
				SPUtils.put(MyApplication.getContext(), "username", usernameET
						.getText().toString().trim());
				SPUtils.put(MyApplication.getContext(), "userpsd", userpsdET
						.getText().toString().trim());

				//启动锁屏的程序，结束掉登录的activity。
				Intent intent = new Intent();
				intent.setClass(this, GestureEditActivity.class);
				startActivity(intent);
				finish();

//				lockScreen.setGesture();
			}

			break;

		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		showBannerAD();
	}

	private void showBannerAD() {
		this.bannerAD = new AdView(this, AdSize.BANNER, Constants.APPId,
				Constants.BannerPosId);
		this.bannerAD.setAdListener(new AdListener() {

			@Override
			public void onNoAd() {
				Log.i("admsg:", "Banner AD LoadFail");
			}

			@Override
			public void onBannerClosed() {
				// 关闭按钮时生效
				Log.i("admsg:", "Banner AD Closed");
			}

			@Override
			public void onAdReceiv() {
				Log.i("admsg:", "Banner AD Ready to show");
			}

			@Override
			public void onAdExposure() {
				Log.i("admsg:", "Banner AD Exposured");
			}

			@Override
			public void onAdClicked() {
				//
				Log.i("admsg:", "Banner AD Clicked");
			}

			@Override
			public void onNoAd(int arg0) {
				Log.i("admsg:", "Banner AD onNoAd:::" + arg0);

			}
		});
		this.bannerContainer.removeAllViews();
		this.bannerContainer.addView(bannerAD);
		bannerAD.fetchAd(new AdRequest());
	}
}
