package com.tonyhuangjun.homework;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivityII extends SherlockActivity {

    // Preferences file accessor id's.
    final static String CLASS_TITLE = "class_title_";
    final static String CLASS_BODY = "class_body_";
    final static String CLASS_STATUS = "class_status_"; // true unfinished,
                                                        // false finished.
    final static String COLOR_SCHEME = "color_scheme";
    final static String NUMBER_OF_CLASSES = "number_of_classes";
    final static String NOTIFICATION_INTERVAL = "notification_interval";
    final static String NOTIFICATION_SOUND = "notification_sound";
    final static String FIRST_RUN = "first_run";

    // System resources accessor.
    private Resources r;

    // Controls user preferences.
    private SharedPreferences settings;
    private Editor editor;

    // To keep track of change in settings.
    private int notificationTimer;

    HashMap<String, Integer> colorScheme = new HashMap<String, Integer>();

    AlarmManager am;

    LinearLayout layout;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load in system resources handler.
        r = getResources();

        // Get preferences file and editor.
        settings = getSharedPreferences("Default", MODE_PRIVATE);
        editor = settings.edit();

        // First run check.
        if (settings.getBoolean(FIRST_RUN, true))
            populatePreferences();

        // Initialize AlarmManager.
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Set view.
        setContentView(R.layout.main);
        layout = (LinearLayout) findViewById(R.id.Layout);
    }

    // Called on application start and after EditActivity
    // or the PreferenceActivity gets closed. So we want
    // to check if the user has changed their total
    // number of classes, or the interval of the notifications.
    // If so, call the appropriate methods.
    protected void onResume() {
        super.onResume();
        refreshSettings();
        
        layout.addView(new Tile(this, "Class 1", "yo|sup|hah|").getView());
        
    }

    private void refreshSettings() {
        getColorScheme();
        int currentNotificationTimer = Integer.parseInt(settings
                        .getString(NOTIFICATION_INTERVAL, "1800000"));
        if (!(notificationTimer == currentNotificationTimer))
            refreshTimer();
    }

    // Get user preference on color scheme and apply to vars.
    private void getColorScheme() {
        colorScheme = Colors.colorScheme(r, Integer.parseInt(settings
                        .getString(COLOR_SCHEME, "1")));

    }

    // Creates a repeating alarm based on the user's notification interval
    // preference that broadcasts and Intent that will be picked up
    // by Alarm.class.
    private void refreshTimer() {
        notificationTimer = Integer.parseInt(settings.getString(
                        NOTIFICATION_INTERVAL, "1800000"));
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        this, 0, intent, 0);

        am.setRepeating(AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis()
                                        + notificationTimer,
                        notificationTimer, pendingIntent);
    }

    // If settings is empty, populates preferences file to contain
    // class titles, bodies, and statuses for debugging.
    private void populatePreferences() {
        editor.putString(NUMBER_OF_CLASSES, "4");
        editor.putString(NOTIFICATION_INTERVAL, "1800000");
        editor.putString(COLOR_SCHEME, "3");
        getColorScheme();

        for (int i = 1; i < 9; i++) {
            editor.putString(CLASS_TITLE + i, "Class " + i);
            editor.putString(CLASS_BODY + i, "");
            editor.putBoolean(CLASS_STATUS + i, false);
        }

        editor.putBoolean(FIRST_RUN, false);
        editor.commit();
    }

    // Populates action bar with buttons from main.xml.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // If user presses settings, opens up the preferences activity.
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
        case R.id.menu_settings:
            startActivity(new Intent(this, Preferences.class));
            break;
        }
        return true;
    }

}
