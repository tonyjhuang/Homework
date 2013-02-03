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
    public void addToTop(Assignment a) {
        oldBody = body;
        body.add(0, a);
        updateBody();
    }

    // Adds string to back of body arraylist.
    public void addToBottom(Assignment a) {
        oldBody = body;
        body.add(a);
        updateBody();
    }

    private void updateView() {
        titleView.setText(title);
        titleEdit.setText(title);
        updateBody();
        // COLORS!!!
        style();
    }

    private void updateBody() {
        Adapttter2 atttt = new Adapttter2(getContext(), body);
        bodyView.setAdapter(atttt);
    }

    public static void edit(Assignment assignment, int index,
                    int position, SharedPreferences settings) {
        String body = settings.getString(MainActivityII.CLASS_BODY
                        + index, Interpreter.NULL);
        ArrayList<Assignment> list = Interpreter.stringToArrayList2(body);
        list.set(position, assignment);
        body = Interpreter.arrayListToString2(list);
        Editor editor = settings.edit();
        editor.putString(MainActivityII.CLASS_BODY + index, body);
        editor.commit();
    }

    public void delete(int position) {
        if (parentID == MainActivityII.MAIN_ID) {
            body.remove(position);
            editor = settings.edit();
            editor.putString(MainActivityII.CLASS_BODY + index,
                            Interpreter.arrayListToString2(body));
            editor.commit();
        }
    }

    // This Tile is equal to another if its title and body are identical.
    // Doesn't account for identical class names with identical assignments but
    // who does that? .... right?
    public boolean equals(Tile t) {
        return title.equals(t.getTitle())
                        && Interpreter.arrayListToString2(body)
                                        .equals(t.getBody());
    }

    // Basic getters.
    public String getTitle() {
        return title;
    }

    public String getBody() {
        return Interpreter.arrayListToString2(body);
    }

    public ListView getListView() {
        return bodyView;
    }

    public View getView() {
        updateView();
        return view;
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

    private class Adapttter2 extends ArrayAdapter<Assignment> {
        Context c;
        ArrayList<Assignment> list;

        public Adapttter2(Context c, ArrayList<Assignment> list) {
            super(c, android.R.layout.simple_list_item_1, list);
            this.c = c;
            this.list = list;
        }

        public View getView(int position, View convertView,
                        ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) c
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.assignment, null);
            }

            Assignment item = list.get(position);
            if (item != null) {
                // My layout has only one TextView
                TextView itemView = (TextView) view
                                .findViewById(R.id.Name);
                TextView dateView = (TextView) view
                                .findViewById(R.id.Date);
                if (itemView != null) {
                    // do whatever you want with your string and long
                    itemView.setText(list.get(position).getName());
                    dateView.setText(list.get(position).getDate());
                }
            }
            view.setTag(index);
            return view;
        }
    }

}
