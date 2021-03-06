package com.worksap.ilock.screen;

import android.app.Activity;

import java.util.ArrayList;

public class ActivityListUtil {
	private static ActivityListUtil instance;
	public ArrayList<Activity> activityList;

	public ActivityListUtil() {
		activityList = new ArrayList<Activity>();
	}
	public static ActivityListUtil getInstance()
	{
		if (instance == null) {
			instance = new ActivityListUtil();
		}
		return instance;
	}
	public void addActivityToList(Activity activity) {
		if(activity!=null)
		{
			activityList.add(activity);
		}
	}
	public void removeActivityFromList(Activity activity)
	{
		if(activityList!=null && activityList.size()>0)
		{
			activityList.remove(activity);
		}
	}
	public void cleanActivityList() {
		if (activityList!=null && activityList.size() > 0) {
			for (int i = 0; i < activityList.size(); i++) {
				Activity activity = activityList.get(i);
				if(activity!=null && !activity.isFinishing())
				{
					activity.finish();
				}
			}
		}
		
	}
}

