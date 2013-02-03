package com.tonyhuangjun.homework;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class Tile extends LinearLayout implements
                View.OnClickListener, View.OnLongClickListener {
    // Fields for displaying title and body. oldTitle and oldBody are
    // fields to keep track of unsaved changes.
    private String title, oldTitle;
    private ArrayList<Assignment> body, oldBody;
    // xml elements.
    LinearLayout background;
    private ViewSwitcher switcher;
    private TextView titleView;
    private EditText titleEdit;
    private ListView bodyView;
    // View of Tile, passed to Activities that wish to display Tiles.
    private View view;
    HashMap<String, Integer> colorScheme;
    private boolean unfinished;
    private Context context;
    private int index, parentID;
    // is the title a textview or editview?
    public boolean editting;

    // Settings & Editor & System resources.
    private SharedPreferences settings;
    private Editor editor;

    public Tile(Context _context, String _title, String _body,
                    int _index, int _parentID,
                    SharedPreferences _settings) {
        super(_context);

        settings = _settings;
        // Store constructor variables
        context = _context;
        unfinished = settings.getBoolean(
                        MainActivityII.CLASS_UNFINISHED + _index,
                        false);
        title = _title;
        body = Interpreter.stringToArrayList2(_body);
        colorScheme = Colors.colorScheme(context.getResources(),
                        Integer.parseInt(settings.getString(
                                        MainActivityII.COLOR_SCHEME,
                                        "1")));
        index = _index;
        parentID = _parentID;
        editting = false;

        // Inflate xml.
        view = ((LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(R.layout.alt_tile, null);

        // Grab handlers to xml.
        background = (LinearLayout) view.findViewById(R.id.Tile);
        switcher = (ViewSwitcher) view
                        .findViewById(R.id.ViewSwitcher);
        titleView = (TextView) switcher.findViewById(R.id.Title);
        titleEdit = (EditText) switcher.findViewById(R.id.TitleEdit);
        bodyView = (ListView) view.findViewById(R.id.Body);
        updateView();

        // Listen to titleView, bodyView, and background (Tile) for clicks.
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (parentID == MainActivityII.MAIN_ID) {
                    Intent intent = new Intent(context,
                                    EditActivityII.class);
                    intent.putExtra(EditActivityII.ID, index);
                    context.startActivity(intent);
                }
            }

        });
        titleView.setOnClickListener(this);
        titleView.setOnLongClickListener(this);
        bodyView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        bodyView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
                if (parentID == MainActivityII.MAIN_ID) {
                    Intent intent = new Intent(context,
                                    EditActivityII.class);
                    intent.putExtra(EditActivityII.ID, index);
                    context.startActivity(intent);
                }
            }
        });
    }

    // Can be called to save title and body directly to settings!
    public void save() {
        editor = settings.edit();
        editor.putString(MainActivityII.CLASS_TITLE + index,
                        titleEdit.getText().toString());
        editor.putString(MainActivityII.CLASS_BODY + index, getBody());
        editor.putBoolean(MainActivityII.CLASS_UNFINISHED + index,
                        unfinished);
        editor.commit();
    }

    public boolean hasChanged() {
        return ((oldBody != null && !oldBody.equals(body)) || (oldTitle != null && !oldTitle
                        .equals(title)));
    }

    // Replace textview with edittext or vice-versa
    public void editTitle() {

        editting = !editting;
        switcher.showNext();
        // If user changed name of class, store old title (in case it gets
        // canceled)
        // and update new title.
        if (titleEdit.getText().toString() != title) {
            oldTitle = title;
            title = titleEdit.getText().toString();
            titleView.setText(title);
            titleEdit.setText(title);
        }

    }

    // Apply fonts and colors to Tile.
    private void style() {

        if (unfinished) {
            titleView.setBackgroundColor(colorScheme
                            .get(Colors.TITLE_UNFINISHED));
            ((TextView) titleView).setTypeface(Typeface.DEFAULT_BOLD);
            bodyView.setBackgroundColor(colorScheme
                            .get(Colors.BODY_UNFINISHED));
            background.setBackgroundColor(colorScheme
                            .get(Colors.BODY_UNFINISHED));

        } else {
            titleView.setBackgroundColor(colorScheme
                            .get(Colors.TITLE_FINISHED));
            ((TextView) titleView).setTypeface(Typeface.DEFAULT);
            bodyView.setBackgroundColor(colorScheme
                            .get(Colors.BODY_FINISHED));
            background.setBackgroundColor(colorScheme
                            .get(Colors.BODY_FINISHED));
        }

    }

    // Adds string to front of body arraylist.
    public void addToTop(String s) {
        oldBody = body;
        body.add(0, Interpreter.stringToAssignment(s));
        updateBody();
    }

    // Adds string to back of body arraylist.
    public void addToBottom(String s) {
        oldBody = body;
        body.add(Interpreter.stringToAssignment(s));
        updateBody();
    }

    // Basic getters.
    public String getTitle() {
        return title;
    }

    public String getBody() {
        return Interpreter.arrayListToString2(body);
    }

    public View getView() {
        updateView();
        return view;
    }

    private void updateView() {
        titleView.setText(title);
        titleEdit.setText(title);
        updateBody();
        // COLORS!!!
        style();
    }

    private void updateBody() {
        Log.d("TILE", "helloworld!");
        TileAdapter ta = new TileAdapter(context, body);
        bodyView.setAdapter(ta);
    }

    // This Tile is equal to another if its title and body are identical.
    // Doesn't account for identical class names with identical assignments but
    // who does that? .... right?
    public boolean equals(Tile t) {
        return title.equals(t.getTitle())
                        && Interpreter.arrayListToString2(body)
                                        .equals(t.getBody());
    }

    // To implement onClick
    @Override
    public void onClick(View view) {
        editor = settings.edit();
        editor.putBoolean(MainActivityII.CLASS_UNFINISHED + index,
                        !unfinished);
        editor.commit();
        unfinished = !unfinished;
        style();
    }

    @Override
    public boolean onLongClick(View v) {
        editTitle();
        return true;
    }

    private class TileAdapter extends ArrayAdapter<Assignment> {
        private ArrayList<Assignment> assignments;
        private Context context;
        private LayoutInflater inflater;

        public TileAdapter(Context _context,
                        ArrayList<Assignment> _assignments) {
            super(_context, R.id.Assignment);
            context = _context;
            assignments = _assignments;
            inflater = (LayoutInflater) context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Log.d("TA:constructor", "helloworld!");
        }

        public View getView(int position, View convertView,
                        ViewGroup parent) {
            Log.d("TA:getView", "helloworld!");
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.assignment, null);
            }

            Assignment a = assignments.get(position);
            TextView name = (TextView) view.findViewById(R.id.Name);
            name.setText(a.getName());
            if (a.hasDate()) {
                TextView date = (TextView) view
                                .findViewById(R.id.Date);
                date.setText(a.getDate());
            }

            return view;
        }

    }

}
