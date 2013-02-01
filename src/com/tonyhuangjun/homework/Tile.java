package com.tonyhuangjun.homework;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Tile extends LinearLayout{
    private String title;
    private ArrayList<String> body = new ArrayList<String>(); 
    private TextView titleView;
    private ListView bodyView;
    
    public Tile(Context context, AttributeSet attrs, String _title, String _body) {
        super(context, attrs);
        
        title = _title;
        body = Interpreter.stringToArrayList(_body);
        
        LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater.inflate(R.layout.tile, this, true);

       titleView = (TextView) findViewById(R.id.Title);
       bodyView = (ListView) findViewById(R.id.Body);
       
       titleView.setText(title);
       ArrayAdapter<String> arrayAdapter =      
                       new ArrayAdapter<String>(getContext(),
                       android.R.layout.simple_list_item_1, body);
       bodyView.setAdapter(arrayAdapter);
        
    }
}
