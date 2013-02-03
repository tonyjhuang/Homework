package com.tonyhuangjun.homework;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivityII extends SherlockActivity {

    // Preferences file accessor id's.
    final static String CLASS_TITLE = "class_title_";
    final static String CLASS_BODY = "class_body_";
    final static String CLASS_UNFINISHED = "class_status_"; // true unfinished,
    // false finished.
    final static String COLOR_SCHEME = "color_scheme";
    final static String NUMBER_OF_CLASSES = "number_of_classes";
    final static String NOTIFICATION_INTERVAL = "notification_interval";
    final static String NOTIFICATION_SOUND = "notification_sound";
    final static String FIRST_RUN = "first_run";

    final static int MAIN_ID = 0;
    final static int EDIT_ID = 1;

    // System resources accessor.
    private Resources r;

    // Controls user preferences.
    private SharedPreferences settings;
    private Editor editor;

    // To keep track of change in settings.
    private int notificationTimer;

    HashMap<String, Integer> colorScheme = new HashMap<String, Integer>();

    AlarmManager am;

    // Keeps track of layout and Tiles to be drawn.
    LinearLayout layout;
    ArrayList<Tile> leftOrTop, rightOrBottom;
    // Determine number of Tiles to put in a row or a column.
    // INVARIANT: If device is rotated, there is at most 2 rows.
    // INVARIANT: Otherwise, there is at most 2 columns.
    // rowcolumnSize determines how many rows or columns there should be
    // with respect to orientation, respecting the invariants listed above.
    int rowcolumnSize;
    int numOfClass;

    // Screen rotation.
    int rotation;

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
        drawTiles();
    }

    private void drawTiles() {

        // Refresh Tiles to be drawn
        leftOrTop = groupTiles(rotation, numOfClass, rowcolumnSize,
                        "left");
        rightOrBottom = groupTiles(rotation, numOfClass,
                        rowcolumnSize, "right");

        // Clear layout to redraw.
        layout.removeAllViews();

        // Prepare layout params. balancedweight is for child2a, child2b
        LinearLayout.LayoutParams halfweight = new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT, .5f);
        LinearLayout.LayoutParams balancedweight = new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT, (float) 1
                                        / rowcolumnSize);

        // Create ONE LinearLayout if number of classes is EXACTLY 2.
        // Create THREE LinearLayouts if number of classes is greater than 2.
        // Push Tile to screen if there is EXACTLY 1.
        layout = refreshLayoutOrientation(layout, false);
        LinearLayout child2a = null, child2b = null, child1 = null;

        if (numOfClass == 1) {
            View viewTile = leftOrTop.get(0).getView();
            viewTile.setLayoutParams(new LinearLayout.LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT, 1f));
            layout.addView(viewTile);
        } else {
            child1 = refreshLayoutOrientation(new LinearLayout(this),
                            true);
            child1.setWeightSum(1f);
            // Make layout fill up whole parent.
            child1.setLayoutParams(new LinearLayout.LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT, 1f));
            if (numOfClass == 2) {
                child1 = addTiles(
                                addTiles(child1, leftOrTop,
                                                halfweight),
                                rightOrBottom, halfweight);
            } else if (numOfClass > 2) {
                child2a = refreshLayoutOrientation(new LinearLayout(
                                this), false);
                child2b = refreshLayoutOrientation(new LinearLayout(
                                this), false);
                child2a = addTiles(child2a, leftOrTop, balancedweight);
                child2b = addTiles(child2b, rightOrBottom,
                                balancedweight);
                child1.addView(child2a, halfweight);
                child1.addView(child2b, halfweight);
            }
            layout.addView(child1);
        }
    }

    // Return arraylist with appropriate tiles based on rotation,
    private ArrayList<Tile> groupTiles(int rotation, int numOfClass,
                    int rowcolumnSize, String leftOrRight) {
        ArrayList<Tile> result = new ArrayList<Tile>();
        // If screen is rotated:
        if (rotation == Surface.ROTATION_90
                        || rotation == Surface.ROTATION_270) {
            // For the top row: first half of Tiles.
            if (leftOrRight.equals("left")) {
                // First half of tiles
                for (int i = 1; i - 1 < rowcolumnSize; i++) {
                    result.add(getTile(i));
                }
            } else {
                // For the bottom row: second half of Tiles.
                for (int j = rowcolumnSize + 1; j - 1 < numOfClass; j++) {
                    result.add(getTile(j));
                }
            }
            // If screen is portrait:
        } else {
            if (leftOrRight.equals("left")) {
                // Odd numbered tiles:
                for (int i = 1; i <= numOfClass; i += 2) {
                    result.add(getTile(i));
                }
            } else {
                // Even numbered tiles:
                for (int j = 2; j <= numOfClass; j += 2) {
                    result.add(getTile(j));
                }
            }
        }
        return result;
    }

    // Make Tile with info from preferences. Register for context menu.
    private Tile getTile(int i) {
        Tile tile = new Tile(this, settings.getString(
                        CLASS_TITLE + i, "Null"), settings.getString(
                        CLASS_BODY + i, "Null"), i, MAIN_ID, settings);
        registerForContextMenu(tile.getListView());
        return tile;
    }

    // Add Tiles in the ArrayList to a given layout.
    private LinearLayout addTiles(LinearLayout layout,
                    ArrayList<Tile> tiles, LinearLayout.LayoutParams p) {
        Iterator<Tile> i = tiles.iterator();
        while (i.hasNext()) {
            layout.addView(((Tile) i.next()).getView(), p);
        }
        return layout;
    }

    // Updates orientation of layout based on rotation of the screen.
    // Also takes a boolean. If its true, reverse the orientation of the layout.
    private LinearLayout refreshLayoutOrientation(
                    LinearLayout layout, boolean opposite) {
        if (rotation == Surface.ROTATION_90
                        || rotation == Surface.ROTATION_270) {
            if (opposite)
                layout.setOrientation(LinearLayout.VERTICAL);
            else
                layout.setOrientation(LinearLayout.HORIZONTAL);
        } else {
            if (opposite)
                layout.setOrientation(LinearLayout.HORIZONTAL);
            else
                layout.setOrientation(LinearLayout.VERTICAL);
        }
        return layout;
    }

    private void refreshSettings() {
        getColorScheme();
        int currentNotificationTimer = Integer.parseInt(settings
                        .getString(NOTIFICATION_INTERVAL, "1800000"));
        if (!(notificationTimer == currentNotificationTimer))
            refreshTimer();
        rotation = getWindowManager().getDefaultDisplay()
                        .getRotation();
        numOfClass = Integer.valueOf(settings.getString(
                        NUMBER_OF_CLASSES, "4"));
        rowcolumnSize = (int) Math.ceil(numOfClass / 2);
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
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd");
        Date now = new Date();
        for (int i = 1; i < 9; i++) {
            editor.putString(CLASS_TITLE + i, "Class " + i);
            ArrayList<Assignment> a = new ArrayList<Assignment>();
            a.add(new Assignment("enter", formatter.format(now)));
            a.add(new Assignment("your", formatter.format(now)));
            a.add(new Assignment("work", formatter.format(now)));
            a.add(new Assignment("here!", formatter.format(now)));
            editor.putString(CLASS_BODY + i,
                            Interpreter.arrayListToString2(a));
            editor.putBoolean(CLASS_UNFINISHED + i, false);
        }

        editor.putBoolean(FIRST_RUN, false);
        editor.commit();
    }

    // Handle screen rotation.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onResume();
    }

    // ContextMenu for ListViews in Tiles.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.tilecontext, menu);
    }

    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
                        .getMenuInfo();
        int index = (Integer) info.targetView.getTag();
        switch (item.getItemId()) {
        case R.id.Edit:
            return true;
        case R.id.Delete:
            Log.d("in Delete...", "position = " + info.position
                            + ", index = " + index);
            new Tile(this, settings.getString(CLASS_TITLE + index,
                            "Null"), settings.getString(CLASS_BODY
                            + index, "Null"), index, MAIN_ID,
                            settings).delete(info.position);
            onResume();
            return true;
        default:
            return super.onContextItemSelected((android.view.MenuItem) item);
        }
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
