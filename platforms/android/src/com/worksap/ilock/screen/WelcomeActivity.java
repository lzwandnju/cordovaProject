package com.worksap.ilock.screen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.qq.e.ads.AdListener;
import com.qq.e.ads.AdRequest;
import com.qq.e.ads.AdSize;
import com.qq.e.ads.AdView;
import com.worksap.ilock.screen.ad.Constants;
import com.worksap.ilock.screen.front.MainActivity;


public class WelcomeActivity extends BaseActivity {
	private AdView bannerAD;
	private LinearLayout bannerContainer;
    private Button button_android;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		bannerContainer = (LinearLayout)findViewById(R.id.bannerad2);
		button_android=(Button)findViewById(R.id.button_android);
		button_android.setOnClickListener(new AndroidOnClickListener());	
	}

	
	//设置事件监听器
	private class AndroidOnClickListener implements OnClickListener {
	    public void onClick(View v){
			Intent intent = new Intent();
//			intent.setClass(WelcomeActivity.this,AndroidToJSActivity.class);
			intent.setClass(WelcomeActivity.this,MainActivity.class);
			startActivity(intent);
			WelcomeActivity.this.finish();
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
				// 仅在开启广点通广告关闭按钮时生效
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
