package com.tonyhuangjun.homework;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private final static String CLASS_NAME_HEADER = "class_name_";
	private final static String EMPTY_CLASS = "empty";
	private final static String TAG = "MainActivity";

	ListView homeworkList;
	TextView alert;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		homeworkList = (ListView) findViewById(R.id.homework_list);
		alert = (TextView) findViewById(R.id.empty_list_alert);

		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString(CLASS_NAME_HEADER + 0, "Vagina");
		editor.commit();
		Log.d(TAG,
				CLASS_NAME_HEADER
						+ 0
						+ " = "
						+ settings
								.getString(CLASS_NAME_HEADER + 0, EMPTY_CLASS));

		Log.d(TAG, "SharedPreferences initialized");
		if (settings.contains(CLASS_NAME_HEADER + 0)) {
			Log.d(TAG, "At least one class exists.");
		} else {
			Log.d(TAG, "No classes exist");
			alert.setText("Add some classes!");
		}
		Log.d(TAG, "onCreate code finished.");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case R.id.menu_edit:

			break;
		}
		return true;
	}
}
