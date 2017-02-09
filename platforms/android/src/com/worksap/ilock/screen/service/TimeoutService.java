package com.worksap.ilock.screen.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.apkfuns.logutils.LogUtils;
import com.worksap.ilock.screen.ActivityListUtil;
import com.worksap.ilock.screen.utils.AppConstants;

public class TimeoutService extends Service implements AppConstants {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	boolean isrun = true;
              
	@Override
	public void onCreate() {
		LogUtils.e("BindService-->onCreate()");
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogUtils.e("BindService-->onStartCommand()");
		
		
		new AlertDialog.Builder(this)
        .setTitle("超时提示" )  						   
        .setMessage("超时消息框" )  						   
        .setPositiveButton("确定" ,  null )  						   
        .show(); 
		
		forceApplicationExit();
		return super.onStartCommand(intent, flags, startId);
		
	}
	
	private void forceApplicationExit()
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				ActivityListUtil.getInstance().cleanActivityList();
				stopSelf();
				}
			}).start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isrun = false;
	}

}
