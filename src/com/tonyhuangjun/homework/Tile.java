package com.tonyhuangjun.homework;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Tile extends LinearLayout {
    private String title;
    private ArrayList<String> body = new ArrayList<String>();
    private TextView titleView;
    private ListView bodyView;
    private View view;

    public Tile(Context context, String _title, String _body,
                    HashMap<String, Integer> colorScheme) {
        super(context);

        title = _title;
        body = Interpreter.stringToArrayList(_body);

        view = ((LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(R.layout.tile, null);
        titleView = (TextView) view.findViewById(R.id.Title);
        bodyView = (ListView) view.findViewById(R.id.Body);

        titleView.setText(title);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1, body);
        bodyView.setAdapter(arrayAdapter);

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
}
