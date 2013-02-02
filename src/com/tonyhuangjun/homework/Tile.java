package com.tonyhuangjun.homework;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Tile extends LinearLayout implements
                View.OnClickListener {
    private String title;
    private ArrayList<String> body;
    private TextView titleView;
    private ListView bodyView;
    private View view;
    HashMap<String, Integer> colorScheme;
    private boolean unfinished;
    private Context context;
    private int index, parentID;

    public Tile(Context _context, String _title, String _body,
                    HashMap<String, Integer> _colorScheme,
                    boolean _unfinished, int _index, int _parentID) {
        super(_context);

        // Store constructor variables
        context = _context;
        unfinished = _unfinished;
        title = _title;
        body = Interpreter.stringToArrayList(_body);
        colorScheme = _colorScheme;
        index = _index;
        parentID = _parentID;

        // Inflate xml.
        view = ((LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(R.layout.tile, null);

        // Grab handlers to xml.
        titleView = (TextView) view.findViewById(R.id.Title);
        bodyView = (ListView) view.findViewById(R.id.Body);

        // Update fields.
        titleView.setText(title);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1, body);
        bodyView.setAdapter(arrayAdapter);

        // COLORS!!!
        style();

        // Listen to titleView and bodyView for clicks.
        titleView.setOnClickListener(this);
        bodyView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
                if(parentID == MainActivityII.MAIN_ID){
                    Intent intent = new Intent(context, EditActivityII.class);
                    intent.putExtra(EditActivityII.ID, index);
                    context.startActivity(intent);
                }
                else{
                    
                }
            }
        });
    }

    public void flip() {
        unfinished = !unfinished;
        style();
    }

    public void style() {
        if (unfinished) {
            titleView.setBackgroundColor(colorScheme
                            .get(Colors.TITLE_UNFINISHED));
            titleView.setTypeface(Typeface.DEFAULT_BOLD);
            bodyView.setBackgroundColor(colorScheme
                            .get(Colors.BODY_UNFINISHED));
        } else {
            titleView.setBackgroundColor(colorScheme
                            .get(Colors.TITLE_FINISHED));
            titleView.setTypeface(Typeface.DEFAULT);
            bodyView.setBackgroundColor(colorScheme
                            .get(Colors.BODY_FINISHED));
        }
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return Interpreter.arrayListToString(body);
    }

    public View getView() {
        return view;
    }

    public boolean equals(Tile t) {
        return title.equals(t.getTitle())
                        && Interpreter.arrayListToString(body)
                                        .equals(t.getBody());
    }

    // To implement onClick
    @Override
    public void onClick(View v) {
        flip();
    }
}
