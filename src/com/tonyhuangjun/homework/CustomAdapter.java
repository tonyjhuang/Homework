package com.tonyhuangjun.homework;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	private static final String TAG = "AddArrayAdapter";

	private ArrayList<String> list;
	private LayoutInflater mInflater;
	private TextView itemText;
	private int layout;
	
	public CustomAdapter(Context context,
			ArrayList<String> _list) {
		super();

		mInflater = LayoutInflater.from(context);
		list = _list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(this.layout, null);
		itemText = (TextView) view.findViewById(R.id.list_item);

		String s = list.get(position).toString();
		itemText.setText(s);


		return view;
	}

	// Add new String to end of ArrayList.
	public void add(String s) {
		list.add(0, s);
		this.notifyDataSetChanged();
	}

	// Remove index from ArrayList.
	public void remove(int pos) {
		list.remove(pos);
		this.notifyDataSetChanged();
	}

	public ArrayList<String> getList() {
		return list;
	}

	// ## Overriden methods ##
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int pos) {
		return list.get(pos);
	}

	public long getItemId(int pos) {
		return pos;
	}
}
