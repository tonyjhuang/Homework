package com.tonyhuangjun.homework;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends SherlockActivity {

    // Preferences file accessor id's.
    final static String CLASS_TITLE = "class_title_";
    final static String CLASS_BODY = "class_body_";
    final static String CLASS_STATUS = "class_status_"; // true unfinished,
                                                        // false finished.
    final static String COLOR_SCHEME = "color_scheme";
    final static String NUMBER_OF_CLASSES = "number_of_classes";
    final static String NOTIFICATION_INTERVAL = "notification_interval";
    final static String NOTIFICATION_SOUND = "notification_sound";
    private final static String FIRST_RUN = "first_run";

    private Resources r;

    // Colors to style the homework tiles.
    int tu;
    int bu;
    int tf;
    int bf;

    // Controls user preferences.
    private SharedPreferences settings;
    private Editor editor;

    // Total number of classes the user has set.
    private int numberOfClasses;
    private int notificationTimer;

    // References to the homework tiles (their titles and bodies).
    TextView classTitle1, classTitle2, classTitle3, classTitle4, classTitle5,
            classTitle6, classTitle7, classTitle8;
    TextView classBody1, classBody2, classBody3, classBody4, classBody5,
            classBody6, classBody7, classBody8;

    // For notifications.
    AlarmManager am;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get preferences file and editor.
        settings = getSharedPreferences("Default", MODE_PRIVATE);
        editor = settings.edit();

        // Initialize am field.
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // If this run is the user's first time opening that app,
        // populate the preference file with dummy strings.
        if (settings.getBoolean(FIRST_RUN, true))
            populatePreferences();

        r = getResources();

    }

    // Called on application start and after EditActivity
    // or the PreferenceActivity gets closed. So we want
    // to check if the user has changed their total
    // number of classes, or the interval of the notifications.
    // If so, call the appropriate methods.
    protected void onResume() {
        super.onResume();

        getColorScheme();

        int currentNumberOfClasses = Integer.parseInt(settings.getString(
                NUMBER_OF_CLASSES, "1"));
        int currentNotificationTimer = Integer.parseInt(settings.getString(
                NOTIFICATION_INTERVAL, "1800000"));

        if (!(currentNumberOfClasses == numberOfClasses)) {
            refreshNumberOfClasses();
            refreshLayout();
        }
        getHandlersAndPopulate();

        if (!(notificationTimer == currentNotificationTimer))
            refreshTimer();

    }

    // Get user preference on color scheme and apply to vars.
    private void getColorScheme() {
        switch (Integer.parseInt(settings.getString(COLOR_SCHEME, "1"))) {
        case 1:
            tu = r.getColor(R.color.color_a1);
            bu = r.getColor(R.color.color_a2);
            tf = r.getColor(R.color.color_a3);
            bf = r.getColor(R.color.color_a4);
            break;

        case 2:
            tu = r.getColor(R.color.color_b1);
            bu = r.getColor(R.color.color_b2);
            tf = r.getColor(R.color.color_b3);
            bf = r.getColor(R.color.color_b4);
            break;

        case 3:
            tu = r.getColor(R.color.color_c1);
            bu = r.getColor(R.color.color_c2);
            tf = r.getColor(R.color.color_c3);
            bf = r.getColor(R.color.color_c4);
            break;

        case 4:
            tu = r.getColor(R.color.color_d1);
            bu = r.getColor(R.color.color_d2);
            tf = r.getColor(R.color.color_d3);
            bf = r.getColor(R.color.color_d4);
            break;

        default:
            tu = r.getColor(R.color.color_a1);
            bu = r.getColor(R.color.color_a2);
            tf = r.getColor(R.color.color_a3);
            bf = r.getColor(R.color.color_a4);
            break;
        }
    }

    // Update the variable holding the current number of classes.
    private void refreshNumberOfClasses() {
        numberOfClasses = Integer.parseInt(settings.getString(
                NUMBER_OF_CLASSES, "1"));
    }

    // Creates a repeating alarm based on the user's notification interval
    // preference that broadcasts and Intent that will be picked up
    // by Alarm.class.
    private void refreshTimer() {
        notificationTimer = Integer.parseInt(settings.getString(
                NOTIFICATION_INTERVAL, "1800000"));
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, 0);

        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + notificationTimer, notificationTimer, pendingIntent);
    }

    // Applies the layout corresponding to the current number of classes.
    private void refreshLayout() {
        switch (numberOfClasses) {
        case 8:
            setContentView(R.layout.main_eight);
            break;
        case 7:
            setContentView(R.layout.main_seven);
            break;
        case 6:
            setContentView(R.layout.main_six);
            break;
        case 5:
            setContentView(R.layout.main_five);
            break;
        case 4:
            setContentView(R.layout.main_four);
            break;
        case 3:
            setContentView(R.layout.main_three);
            break;
        case 2:
            setContentView(R.layout.main_two);
            break;
        default:
            setContentView(R.layout.main_one);
            break;
        }
    }

    // Gets the handlers for the titles and bodies of the homework tiles
    // based on the number of classes the user has set in preferences,
    // sets the bodies to be vertically scrollable, then sets the actual
    // text in the TextViews. To make the titles marquee properly, the
    // TextViews of all visible titles fields must be set to "selected".
    // Finally, calls style to color the tiles.
    private void getHandlersAndPopulate() {
        switch (numberOfClasses) {
        case 8:
            classTitle8 = (TextView) findViewById(R.id.ClassTitle8);
            classBody8 = (TextView) findViewById(R.id.ClassBody8);
            classBody8.setMovementMethod(new ScrollingMovementMethod());
            classTitle8.setText(settings.getString(CLASS_TITLE + 8, "Null"));
            classBody8.setText(settings.getString(CLASS_BODY + 8, "Null"));
            classTitle8.setSelected(true);

            // Style homework tiles.
            if (settings.getBoolean(CLASS_STATUS + 8, true)) {
                classTitle8.setTypeface(Typeface.DEFAULT_BOLD);
                classTitle8.setBackgroundColor(tu);
                classBody8.setBackgroundColor(bu);
            } else {
                classTitle8.setBackgroundColor(tf);
                classBody8.setBackgroundColor(bf);
            }

        case 7:
            classTitle7 = (TextView) findViewById(R.id.ClassTitle7);
            classBody7 = (TextView) findViewById(R.id.ClassBody7);
            classBody7.setMovementMethod(new ScrollingMovementMethod());
            classTitle7.setText(settings.getString(CLASS_TITLE + 7, "Null"));
            classBody7.setText(settings.getString(CLASS_BODY + 7, "Null"));
            classTitle7.setSelected(true);

            if (settings.getBoolean(CLASS_STATUS + 7, true)) {
                classTitle7.setTypeface(Typeface.DEFAULT_BOLD);
                classTitle7.setBackgroundColor(tu);
                classBody7.setBackgroundColor(bu);
            } else {
                classTitle7.setBackgroundColor(tf);
                classBody7.setBackgroundColor(bf);
            }

        case 6:
            classTitle6 = (TextView) findViewById(R.id.ClassTitle6);
            classBody6 = (TextView) findViewById(R.id.ClassBody6);
            classBody6.setMovementMethod(new ScrollingMovementMethod());
            classTitle6.setText(settings.getString(CLASS_TITLE + 6, "Null"));
            classBody6.setText(settings.getString(CLASS_BODY + 6, "Null"));
            classTitle6.setSelected(true);

            if (settings.getBoolean(CLASS_STATUS + 6, true)) {
                classTitle6.setTypeface(Typeface.DEFAULT_BOLD);
                classTitle6.setBackgroundColor(tu);
                classBody6.setBackgroundColor(bu);
            } else {
                classTitle6.setBackgroundColor(tf);
                classBody6.setBackgroundColor(bf);
            }

        case 5:
            classTitle5 = (TextView) findViewById(R.id.ClassTitle5);
            classBody5 = (TextView) findViewById(R.id.ClassBody5);
            classBody5.setMovementMethod(new ScrollingMovementMethod());
            classTitle5.setText(settings.getString(CLASS_TITLE + 5, "Null"));
            classBody5.setText(settings.getString(CLASS_BODY + 5, "Null"));
            classTitle5.setSelected(true);

            if (settings.getBoolean(CLASS_STATUS + 5, true)) {
                classTitle5.setTypeface(Typeface.DEFAULT_BOLD);
                classTitle5.setBackgroundColor(tu);
                classBody5.setBackgroundColor(bu);
            } else {
                classTitle5.setBackgroundColor(tf);
                classBody5.setBackgroundColor(bf);
            }

        case 4:
            classTitle4 = (TextView) findViewById(R.id.ClassTitle4);
            classBody4 = (TextView) findViewById(R.id.ClassBody4);
            classBody4.setMovementMethod(new ScrollingMovementMethod());
            classTitle4.setText(settings.getString(CLASS_TITLE + 4, "Null"));
            classBody4.setText(settings.getString(CLASS_BODY + 4, "Null"));
            classTitle4.setSelected(true);

            if (settings.getBoolean(CLASS_STATUS + 4, true)) {
                classTitle4.setTypeface(Typeface.DEFAULT_BOLD);
                classTitle4.setBackgroundColor(tu);
                classBody4.setBackgroundColor(bu);
            } else {
                classTitle4.setBackgroundColor(tf);
                classBody4.setBackgroundColor(bf);
            }

        case 3:
            classTitle3 = (TextView) findViewById(R.id.ClassTitle3);
            classBody3 = (TextView) findViewById(R.id.ClassBody3);
            classBody3.setMovementMethod(new ScrollingMovementMethod());
            classTitle3.setText(settings.getString(CLASS_TITLE + 3, "Null"));
            classBody3.setText(settings.getString(CLASS_BODY + 3, "Null"));
            classTitle3.setSelected(true);

            if (settings.getBoolean(CLASS_STATUS + 3, true)) {
                classTitle3.setTypeface(Typeface.DEFAULT_BOLD);
                classTitle3.setBackgroundColor(tu);
                classBody3.setBackgroundColor(bu);
            } else {
                classTitle3.setBackgroundColor(tf);
                classBody3.setBackgroundColor(bf);
            }

        case 2:
            classTitle2 = (TextView) findViewById(R.id.ClassTitle2);
            classBody2 = (TextView) findViewById(R.id.ClassBody2);
            classBody2.setMovementMethod(new ScrollingMovementMethod());
            classTitle2.setText(settings.getString(CLASS_TITLE + 2, "Null"));
            classBody2.setText(settings.getString(CLASS_BODY + 2, "Null"));
            classTitle2.setSelected(true);

            if (settings.getBoolean(CLASS_STATUS + 2, true)) {
                classTitle2.setTypeface(Typeface.DEFAULT_BOLD);
                classTitle2.setBackgroundColor(tu);
                classBody2.setBackgroundColor(bu);
            } else {
                classTitle2.setBackgroundColor(tf);
                classBody2.setBackgroundColor(bf);
            }

        default:
            classTitle1 = (TextView) findViewById(R.id.ClassTitle1);
            classBody1 = (TextView) findViewById(R.id.ClassBody1);
            classBody1.setMovementMethod(new ScrollingMovementMethod());
            classTitle1.setText(settings.getString(CLASS_TITLE + 1, "Null"));
            classBody1.setText(settings.getString(CLASS_BODY + 1, "Null"));
            classTitle1.setSelected(true);

            if (settings.getBoolean(CLASS_STATUS + 1, true)) {
                classTitle1.setTypeface(Typeface.DEFAULT_BOLD);
                classTitle1.setBackgroundColor(tu);
                classBody1.setBackgroundColor(bu);
            } else {
                classTitle1.setBackgroundColor(tf);
                classBody1.setBackgroundColor(bf);
            }
        }

        style();
    }

    // Checks if the homework tiles are set to unfinished (true) or
    // finished (false) and applies background colors appropriately.
    private void style() {
        switch (numberOfClasses) {
        case 8:
            if (settings.getBoolean(CLASS_STATUS + 8, true)) {
                classTitle8.setTypeface(Typeface.DEFAULT_BOLD);
                classTitle8.setBackgroundColor(tu);
                classBody8.setBackgroundColor(bu);
            } else {
                classTitle8.setTypeface(Typeface.DEFAULT);
                classTitle8.setBackgroundColor(tf);
                classBody8.setBackgroundColor(bf);
            }

        case 7:
            if (settings.getBoolean(CLASS_STATUS + 7, true)) {
                classTitle7.setTypeface(Typeface.DEFAULT_BOLD);
                classTitle7.setBackgroundColor(tu);
                classBody7.setBackgroundColor(bu);
            } else {
                classTitle7.setTypeface(Typeface.DEFAULT);
                classTitle7.setBackgroundColor(tf);
                classBody7.setBackgroundColor(bf);
            }

        case 6:
            if (settings.getBoolean(CLASS_STATUS + 6, true)) {
                classTitle6.setTypeface(Typeface.DEFAULT_BOLD);
                classTitle6.setBackgroundColor(tu);
                classBody6.setBackgroundColor(bu);
            } else {
                classTitle6.setTypeface(Typeface.DEFAULT);
                classTitle6.setBackgroundColor(tf);
                classBody6.setBackgroundColor(bf);
            }

        case 5:
            if (settings.getBoolean(CLASS_STATUS + 5, true)) {
                classTitle5.setTypeface(Typeface.DEFAULT_BOLD);
                classTitle5.setBackgroundColor(tu);
                classBody5.setBackgroundColor(bu);
            } else {
                classTitle5.setTypeface(Typeface.DEFAULT);
                classTitle5.setBackgroundColor(tf);
                classBody5.setBackgroundColor(bf);
            }

        case 4:
            if (settings.getBoolean(CLASS_STATUS + 4, true)) {
                classTitle4.setTypeface(Typeface.DEFAULT_BOLD);
                classTitle4.setBackgroundColor(tu);
                classBody4.setBackgroundColor(bu);
            } else {
                classTitle4.setTypeface(Typeface.DEFAULT);
                classTitle4.setBackgroundColor(tf);
                classBody4.setBackgroundColor(bf);
            }

        case 3:
            if (settings.getBoolean(CLASS_STATUS + 3, true)) {
                classTitle3.setTypeface(Typeface.DEFAULT_BOLD);
                classTitle3.setBackgroundColor(tu);
                classBody3.setBackgroundColor(bu);
            } else {
                classTitle3.setTypeface(Typeface.DEFAULT);
                classTitle3.setBackgroundColor(tf);
                classBody3.setBackgroundColor(bf);
            }

        case 2:
            if (settings.getBoolean(CLASS_STATUS + 2, true)) {
                classTitle2.setTypeface(Typeface.DEFAULT_BOLD);
                classTitle2.setBackgroundColor(tu);
                classBody2.setBackgroundColor(bu);
            } else {
                classTitle2.setTypeface(Typeface.DEFAULT);
                classTitle2.setBackgroundColor(tf);
                classBody2.setBackgroundColor(bf);
            }

        default:
            if (settings.getBoolean(CLASS_STATUS + 1, true)) {
                classTitle1.setTypeface(Typeface.DEFAULT_BOLD);
                classTitle1.setBackgroundColor(tu);
                classBody1.setBackgroundColor(bu);
            } else {
                classTitle1.setTypeface(Typeface.DEFAULT);
                classTitle1.setBackgroundColor(tf);
                classBody1.setBackgroundColor(bf);
            }
        }
    }

    // If settings is empty, populates preferences file to contain
    // class titles, bodies, and statuses for debugging.
    private void populatePreferences() {
        editor.putString(NUMBER_OF_CLASSES, "1");
        editor.putString(NOTIFICATION_INTERVAL, "1800000");
        editor.putString(COLOR_SCHEME, "1");
        refreshNumberOfClasses();
        notificationTimer = 1800000;

        for (int i = 1; i < 9; i++) {
            editor.putString(CLASS_TITLE + i, "Class " + i);
        }
        for (int j = 1; j < 9; j++) {
            editor.putString(CLASS_BODY + j, "Assignments.");
        }
        for (int k = 1; k < 9; k++) {
            editor.putBoolean(CLASS_STATUS + k, false);
        }

        editor.putBoolean(FIRST_RUN, false);
        editor.commit();
    }

    // Flips class status and applies new typeface. Then calls
    // style to apply color.
    public void flip(int i) {
        if (settings.getBoolean(CLASS_STATUS + i, true)) {
            editor.putBoolean(CLASS_STATUS + i, false);
            switch (i) {
            case 8:
                classTitle8.setTypeface(Typeface.DEFAULT);
                break;
            case 7:
                classTitle7.setTypeface(Typeface.DEFAULT);
                break;
            case 6:
                classTitle6.setTypeface(Typeface.DEFAULT);
                break;
            case 5:
                classTitle5.setTypeface(Typeface.DEFAULT);
                break;
            case 4:
                classTitle4.setTypeface(Typeface.DEFAULT);
                break;
            case 3:
                classTitle3.setTypeface(Typeface.DEFAULT);
                break;
            case 2:
                classTitle2.setTypeface(Typeface.DEFAULT);
                break;
            case 1:
                classTitle1.setTypeface(Typeface.DEFAULT);
                break;
            }
        } else {
            editor.putBoolean(CLASS_STATUS + i, true);
            switch (i) {
            case 8:
                classTitle8.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            case 7:
                classTitle7.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            case 6:
                classTitle6.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            case 5:
                classTitle5.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            case 4:
                classTitle4.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            case 3:
                classTitle3.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            case 2:
                classTitle2.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            case 1:
                classTitle1.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            }
        }
        editor.commit();
        style();

    }

    // Starts EditActivity populated with the class information
    // that is identified by i.
    public void view(int i) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("ID", i);
        startActivity(intent);
    }

    // ###OnClick managers for Titles and Bodies of classes###
    public void classTitle8Click(View v) {
        flip(8);
    }

    public void classTitle7Click(View v) {
        flip(7);
    }

    public void classTitle6Click(View v) {
        flip(6);
    }

    public void classTitle5Click(View v) {
        flip(5);
    }

    public void classTitle4Click(View v) {
        flip(4);
    }

    public void classTitle3Click(View v) {
        flip(3);
    }

    public void classTitle2Click(View v) {
        flip(2);
    }

    public void classTitle1Click(View v) {
        flip(1);
    }

    public void classBody8Click(View v) {
        view(8);
    }

    public void classBody7Click(View v) {
        view(7);
    }

    public void classBody6Click(View v) {
        view(6);
    }

    public void classBody5Click(View v) {
        view(5);
    }

    public void classBody4Click(View v) {
        view(4);
    }

    public void classBody3Click(View v) {
        view(3);
    }

    public void classBody2Click(View v) {
        view(2);
    }

    public void classBody1Click(View v) {
        view(1);
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
