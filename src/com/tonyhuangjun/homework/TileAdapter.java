package com.tonyhuangjun.homework;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TileAdapter extends ArrayAdapter<Assignment> {
    private ArrayList<Assignment> assignments;
    private Context context;
    private LayoutInflater inflater;
    
    public TileAdapter(Context _context, ArrayList<Assignment> _assignments){
        super(_context, R.layout.assignment);
        context = _context;
        assignments = _assignments;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = inflater.inflate(R.layout.assignment, null);
        }
        
        Assignment a = assignments.get(position);
        TextView name = (TextView) view.findViewById(R.id.Name);
        name.setText(a.getName());
        if (a.hasDate()){
            TextView date = (TextView) view.findViewById(R.id.Date);
            date.setText(a.getDate());
        }
        
        return view;
    }
    
}
