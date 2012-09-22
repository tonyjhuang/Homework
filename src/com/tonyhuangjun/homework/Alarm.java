package com.tonyhuangjun.homework;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Vibrator;

public class Alarm extends BroadcastReceiver {

    NotificationManager nm;
    SharedPreferences settings;

    @SuppressWarnings("deprecation")
    @Override
    public void onReceive(Context context, Intent intent) {
        settings = context
                .getSharedPreferences("Default", Context.MODE_PRIVATE);

        String message = findUnfinished();
        if (!message.equals("Null")) {

            NotificationManager nm = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            Notification n = new Notification(R.drawable.fallout,
                    "DO YOUR HOMEWORK.", System.currentTimeMillis());
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,

            new Intent(context, MainActivity.class), 0);
            n.flags = Notification.FLAG_AUTO_CANCEL
                    | Notification.FLAG_ONLY_ALERT_ONCE;
            n.setLatestEventInfo(context, "iiiiiit's homework time!",
                    findUnfinished(), pendingIntent);

            n.number += 1;

            n.sound = Uri.parse(settings.getString(
                    MainActivity.NOTIFICATION_SOUND,
                    "content://settings/system/notification_sound"));

            Vibrator v = (Vibrator) context
                    .getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(1000);
            nm.notify(0, n);

        }
    }

    private String findUnfinished() {
        int numOfClasses = Integer.valueOf(settings.getString(
                MainActivity.NUMBER_OF_CLASSES, "1"));
        String result = "Unfinished: ";
        for (int i = 0; i < numOfClasses; i++) {
            if (settings.getBoolean(MainActivity.CLASS_STATUS + (i + 1), true)) {
                result += (settings.getString(MainActivity.CLASS_TITLE
                        + (i + 1), "Null") + ", ");
            }
        }

        // Everything is finished!
        if (result.equals("Unfinished: "))
            return "Null";
        else
            return result.substring(0, result.length() - 2) + ".";
    }

}
