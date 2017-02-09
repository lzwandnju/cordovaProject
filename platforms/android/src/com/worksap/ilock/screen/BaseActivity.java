package com.worksap.ilock.screen;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.worksap.ilock.screen.activity.GestureVerifyActivity;
import com.worksap.ilock.screen.application.MyApplication;
import com.worksap.ilock.screen.service.ScreenObserver;
import com.worksap.ilock.screen.service.TimeoutService;
import com.worksap.ilock.screen.utils.ActivityCollector;
import com.worksap.ilock.screen.utils.AppUtils;
import com.worksap.ilock.screen.utils.SPUtils;

public class BaseActivity extends Activity {

	boolean flag = false;
    boolean activityIsActive =false;
    private ScreenObserver mScreenObserver;
    private static final int SHOW_ANOTHER_ACTIVITY = 0;
    
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Message msg = mHandler.obtainMessage(SHOW_ANOTHER_ACTIVITY);
        mHandler.sendMessageDelayed(msg, 30000);//30秒之后进入锁屏的状态
	        
//      mScreenObserver = new ScreenObserver(this);
//		mScreenObserver.requestScreenStateUpdate(new ScreenStateListener() {
//			@Override
//			public void onScreenOn() {
//				if (!ScreenObserver.isApplicationBroughtToBackground(BaseActivity.this)) {
//					cancelAlarmManager();
//				}
//			}
//
//			@Override
//			public void onScreenOff() {
//				if (!ScreenObserver.isApplicationBroughtToBackground(BaseActivity.this)) {
//					cancelAlarmManager();
//					setAlarmManager();
//				}
//			}
//		});
		
		ActivityCollector.addActivity(this);
	}


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            resetTime();
            break;
        default:
            break;
        }
        return true;
    }
       
//    @Override  
//    public boolean dispatchTouchEvent(MotionEvent ev) {  
//        // TODO Auto-generated method stub  
//         resetTime();  
//        return super.dispatchTouchEvent(ev);  
//    }
    
    private void resetTime() {
        // TODO Auto-generated method stub
        mHandler.removeMessages(SHOW_ANOTHER_ACTIVITY);//从消息队列中移除
        Message msg = mHandler.obtainMessage(SHOW_ANOTHER_ACTIVITY);
        mHandler.sendMessageDelayed(msg, 30000);//无操作30秒之后进入到锁屏的状态
    }
     
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if(msg.what==SHOW_ANOTHER_ACTIVITY)
            {            	
                //跳到activity  
//              Log.i(TAG, "跳到activity");  
//              Intent intent=new Intent(BaseActivity.this, GestureVerifyActivity.class);  
//              startActivity(intent);

				if (!SPUtils.get(MyApplication.getContext(),"gesturePsd", "").toString().equals("")) {
                  LogUtils.d("Start Lock the Screen!");
      	          Intent intent = new Intent();
      	    	  intent.setClass(BaseActivity.this, GestureVerifyActivity.class);
      	    	  startActivity(intent);
      	    	  BaseActivity.this.finish();
            	}else{
					LogUtils.d("还没有设置锁屏的密码。");
				}
            }
        }
    };
	
	
	/**
	 * 设置定时器管理器
	 */
	private void setAlarmManager() {
		long numTimeout = 30 * 1000;//  30秒 （ 5分钟）
		LogUtils.d("isTimeOutMode=yes,timeout=" + numTimeout);
		Intent alarmIntent = new Intent(this, TimeoutService.class);
		alarmIntent.putExtra("action", "timeout"); // 自定义参数
		
//      LogUtils.d("Start Lock the Screen!");
//		Intent intent = new Intent();
//		intent.setClass(this, GestureVerifyActivity.class);
//		startActivity(intent);
//		finish();

//		PendingIntent pi = PendingIntent.getService(this, 1024, alarmIntent,
//				PendingIntent.FLAG_UPDATE_CURRENT);

//		PendingIntent pi = PendingIntent.getService(this, 1024, intent,
//				PendingIntent.FLAG_UPDATE_CURRENT);
		
		PendingIntent pi = PendingIntent.getService(this, 1024, alarmIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		long triggerAtTime = (System.currentTimeMillis() + numTimeout);
		am.set(AlarmManager.RTC_WAKEUP, triggerAtTime, pi); // 设定的一次性闹钟，这里决定是否使用绝对时间
		LogUtils.d("----->设置定时器");
//		LogUtil.d("","");
	}

	/**
	 * 取消定时管理器
	 */
	private void cancelAlarmManager() {
		AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(this, TimeoutService.class);
		PendingIntent pi = PendingIntent.getService(this, 1024, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		// 与上面的intent匹配（filterEquals(intent)）的闹钟会被取消
		alarmMgr.cancel(pi);
		LogUtils.d("----->取消定时器");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Object object = false;
		// SPUtils.get(this, "showGesture", new Boolean(false));
		if ((Boolean) SPUtils.get(this, "isbackground", false)) {
			Intent intent = new Intent();
			intent.setClass(this, GestureVerifyActivity.class);
			startActivity(intent);
		}
	}

	@Override
	protected void onResume() {
//		LogUtils.e("MainActivity-onResume");
		super.onResume();
		cancelAlarmManager();
		activityIsActive = true;
		LogUtils.d("activityIsActive=" + activityIsActive);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		SPUtils.put(this, "isbackground", false);
		ActivityCollector.removeActivity(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		LogUtils.e("onStop");
		if (AppUtils.isRunningBackground(this)) {
			SPUtils.put(this, "isbackground", true);
		}
		if (ScreenObserver.isApplicationBroughtToBackground(this)) {
			cancelAlarmManager();
			setAlarmManager();
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == event.KEYCODE_BACK) {

			if (flag) {

				ActivityCollector.finishAll();
				// appData.exitAllActivity(BaseActivity.this);
				this.finish();
			} else {
				Toast.makeText(this, "连按两次退出程序", Toast.LENGTH_SHORT).show();
				flag = true;
				new Thread() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						flag = false;
						super.run();
					}
				}.start();
			}

		}
		return true;
	}

}
