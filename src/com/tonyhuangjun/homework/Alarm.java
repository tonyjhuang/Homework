package com.tonyhuangjun.homework;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class Alarm extends BroadcastReceiver {
	private static final String TAG = "Alarm";

	NotificationManager nm;
	SharedPreferences settings;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "Alarm call received.");
		settings = context
				.getSharedPreferences("Default", context.MODE_PRIVATE);

		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = new Notification(R.drawable.ic_action_done,
				"DO YOUR FUCKING HOMEWORK.", System.currentTimeMillis());
		n.flags = Notification.FLAG_AUTO_CANCEL;
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				new Intent(context, MainActivity.class), 0);
		n.setLatestEventInfo(context, "iiiiiit's homework time!",
				findUnfinished(), pendingIntent);
		n.number += 1;
		nm.notify(0, n);
	}

	private String findUnfinished() {
		int numOfClasses = Integer.valueOf(settings.getString(
				MainActivity.NUMBER_OF_CLASSES, "1"));
		String result = "Unfinished: ";
		for (int i = 0; i < numOfClasses; i++) {
			if (settings.getBoolean(MainActivity.CLASS_STATUS + (i + 1), true)) {
				Log.d(TAG, "i = " + i);
				Log.d(TAG,
						"Current class name = "
								+ settings.getString(MainActivity.CLASS_TITLE
										+ i, "Null"));
				result += (settings.getString(MainActivity.CLASS_TITLE + (i + 1),
						"Null") + ", ");
			}
		}

		// Everything is finished!
		if (result.equals("Unfinished: "))
			return "Good job, champ! You have NO homework left to do!";
		else
			return result.substring(0, result.length() - 2) + ".";
	}

}