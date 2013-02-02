package com.tonyhuangjun.homework;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class EditActivityII extends SherlockActivity {

    public static final String ID = "ID";

    // Settings & Editor & System resources.
    private SharedPreferences settings;
    private Editor editor;

    // Information to be displayed.
    private String title;
    private String body;
    private Tile tile;
    private View viewTile;
    private LinearLayout layout;

    // Index of Tile being edited.
    private int index;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // initialize settings and editor.
        settings = getSharedPreferences("Default", MODE_PRIVATE);
        editor = settings.edit();

        // Get index of class from passed in Intent.
        index = getIntent().getIntExtra(EditActivityII.ID, 1);

        // Grab title information from settings.
        title = settings.getString(MainActivity.CLASS_TITLE + index,
                        "Null");
        body = settings.getString(MainActivity.CLASS_BODY + index,
                        "Null|");

        // Get handler for top layout.
        layout = (LinearLayout) findViewById(R.id.Layout);

        // Instantiate a new tile with title and body information.
        tile = new Tile(getBaseContext(), title, body, index,
                        MainActivityII.EDIT_ID, settings);
    }

    // Called after onResume, orientation change.
    // Will also call after data changes (deletions and insertions) to refresh
    // view.
    protected void onResume() {
        super.onResume();
        // Clear top layout.
        layout.removeAllViews();
        viewTile = tile.getView();
        // Apply layoutparams to viewTile.
        viewTile.setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT, 1f));
        layout.addView(viewTile);
    }

    // Intercept back button call with dialog. Ask user if he wants to
    // delete unsaved changes.

    // Handle screen rotation.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
        case R.id.menu_title:
            Log.d("EDIT", (tile == null) + "");
            tile.editTitle();
            break;
        case R.id.menu_save:
            tile.save();
            finish();
            break;
        }
        return true;
    }
}
