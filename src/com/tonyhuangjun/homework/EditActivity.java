package com.tonyhuangjun.homework;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends SherlockActivity {

    private SharedPreferences settings;
    private Editor editor;

    private String title;
    private String body;

    private View classTitle;
    private EditText classBody;
    private TextView classStatus;

    private int bodyLength;

    private Resources r;

    // Colors to style the homework tiles.
    int tu;
    int bu;
    int tf;
    int bf;

    private ViewGroup parent;
    private int index;

    private int id;
    private boolean edit;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        settings = getSharedPreferences("Default", MODE_PRIVATE);
        editor = settings.edit();

        r = getResources();

        getColorScheme();

        parent = (ViewGroup) findViewById(R.id.TopLayout);

        // Get id of class from passed in Intent.
        Intent i = getIntent();
        id = i.getIntExtra("ID", 1);

        title = settings.getString(MainActivity.CLASS_TITLE + id, "Null");
        body = settings.getString(MainActivity.CLASS_BODY + id, "Null");

        // Handlers and text.
        classTitle = (TextView) findViewById(R.id.ClassTitle);
        classBody = (EditText) findViewById(R.id.ClassBody);
        classStatus = (TextView) findViewById(R.id.EditTitleStatus);

        ((TextView) classTitle).setText(title);
        classBody.setText(body);

        // onKeyListener.
        listenToBody();

        // Color classStatus
        style();

        index = parent.indexOfChild(classTitle);

        edit = false;

        ActionBar ab = getSupportActionBar();
        ab.setTitle(title);

        refreshLength();
    }

    // Get user preference on color scheme and apply to vars.

    // Get user preference on color scheme and apply to vars.
    private void getColorScheme() {
        switch (Integer.parseInt(settings.getString(MainActivity.COLOR_SCHEME,
                "1"))) {
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

    // Apply onKeyListener to body so tile can be styled as soon as
    // there are no more assignments or an assignment is added.
    private void listenToBody() {
        classBody.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            }

            // If the tile is finished...
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                // finished
                if (!settings.getBoolean(MainActivity.CLASS_STATUS + id, false)) {
                    if (getLength() > bodyLength)
                        flipStatus((TextView) findViewById(R.id.EditTitleStatus));

                } else { // unfinished
                    if (getLength() == 0)
                        flipStatus((TextView) findViewById(R.id.EditTitleStatus));
                }

                refreshLength();
            }
        });
    }

    private void refreshLength() {
        bodyLength = getLength();
    }

    private int getLength() {
        return classBody.getText().toString().length();
    }

    // Style tile based on status.
    private void style() {
        if (settings.getBoolean(MainActivity.CLASS_STATUS + id, true)) {
            ((TextView) classTitle).setTypeface(Typeface.DEFAULT_BOLD);
            classStatus.setBackgroundColor(tu);
            classBody.setBackgroundColor(bu);
        } else {
            ((TextView) classTitle).setTypeface(Typeface.DEFAULT);
            classStatus.setBackgroundColor(tf);
            classBody.setBackgroundColor(bf);
        }
    }

    // Flips class status (Finished <-> Unfinished).
    public void flipStatus(View v) {
        editor.putBoolean(MainActivity.CLASS_STATUS + id,
                !settings.getBoolean(MainActivity.CLASS_STATUS + id, true));
        editor.commit();
        style();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {/*
        case R.id.menu_title:
            String title;
            parent.removeView(classTitle);
            if (edit) {
                title = String.valueOf(((EditText) classTitle).getText());
                // Replace EditText with TextView. Flip boolean.
                classTitle = getLayoutInflater().inflate(R.layout.view_title,
                        parent, false);
                parent.addView(classTitle, index);
                ((TextView) classTitle).setText(title);
                ((TextView) classTitle).setSelected(true);
            } else {
                title = String.valueOf(((TextView) classTitle).getText());
                // Replace TextView with EditText
                classTitle = getLayoutInflater().inflate(R.layout.edit_title,
                        parent, false);
                parent.addView(classTitle, index);
                ((EditText) classTitle).setText(title);
            }
            edit = !edit;
            break;
            */
        case R.id.menu_save:
            if (edit)
                editor.putString(MainActivity.CLASS_TITLE + id,
                        String.valueOf(((EditText) classTitle).getText()));
            else
                editor.putString(MainActivity.CLASS_TITLE + id,
                        String.valueOf(((TextView) classTitle).getText()));

            editor.putString(MainActivity.CLASS_BODY + id,
                    String.valueOf(classBody.getText()));
            editor.commit();
            finish();
            break;
        }
        return true;
    }
}
