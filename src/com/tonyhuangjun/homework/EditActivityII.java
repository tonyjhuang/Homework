package com.tonyhuangjun.homework;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

public class EditActivityII extends SherlockActivity {

    public static final String ID = "ID";

    // Settings & Editor & System resources.
    private SharedPreferences settings;
    private Editor editor;
    private Resources r;

    public String title;
    public ArrayList<String> body;

    // Colors to style the homework tiles.
    HashMap<String, Integer> colorScheme;

    // Index of Tile being edited.
    private int index;
    // Is the title of the tile being editted?
    private boolean edit;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        settings = getSharedPreferences("Default", MODE_PRIVATE);
        editor = settings.edit();

        r = getResources();
        // Get id of class from passed in Intent.
        Intent i = getIntent();
        index = i.getIntExtra(EditActivityII.ID, 1);
    }

    protected void onResume() {
        super.onResume();
        getColorScheme();
        title = settings.getString(MainActivity.CLASS_TITLE + index,
                        "Null");
        body = Interpreter.stringToArrayList(settings.getString(
                        MainActivity.CLASS_BODY + index, "Null|"));

    }

    // Get user preference on color scheme and apply to vars.
    private void getColorScheme() {
        colorScheme = Colors.colorScheme(r, Integer.parseInt(settings
                        .getString(MainActivityII.COLOR_SCHEME, "1")));

    }

    public void listViewClick(int index) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    /*
     * @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
     * switch (menuItem.getItemId()) { case R.id.menu_title: String title;
     * parent.removeView(classTitle); if (edit) { title =
     * String.valueOf(((EditText) classTitle) .getText()); // Replace EditText
     * with TextView. Flip boolean. classTitle = getLayoutInflater().inflate(
     * R.layout.view_title, parent, false); parent.addView(classTitle, index);
     * ((TextView) classTitle).setText(title); ((TextView)
     * classTitle).setSelected(true); } else { title =
     * String.valueOf(((TextView) classTitle) .getText()); // Replace TextView
     * with EditText classTitle = getLayoutInflater().inflate(
     * R.layout.edit_title, parent, false); parent.addView(classTitle, index);
     * ((EditText) classTitle).setText(title); } edit = !edit; break; case
     * R.id.menu_save: if (edit) editor.putString( MainActivity.CLASS_TITLE +
     * id, String.valueOf(((EditText) classTitle) .getText())); else
     * editor.putString( MainActivity.CLASS_TITLE + id,
     * String.valueOf(((TextView) classTitle) .getText()));
     * 
     * editor.putString(MainActivity.CLASS_BODY + id,
     * String.valueOf(classBody.getText())); editor.commit(); finish(); break; }
     * return true; }
     */
}
