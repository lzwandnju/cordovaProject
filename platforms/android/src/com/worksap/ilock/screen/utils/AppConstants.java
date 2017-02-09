package com.worksap.ilock.screen.utils;

import android.content.Intent;
import android.os.IBinder;

public interface AppConstants {
	public IBinder onBind(Intent arg0);
	public void onCreate();
	public int onStartCommand(Intent intent, int flag, int startId);
	public void onDestroy();
}
