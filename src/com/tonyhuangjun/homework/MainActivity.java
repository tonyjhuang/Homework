package com.tonyhuangjun.homework;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	private final static String CLASS_TITLE = "class_title_";
	private final static String CLASS_BODY = "class_body_";
	private final static String NUMBER_OF_CLASSES = "number_of_classes";
	// As of right now, the possible ints are 1, 2, and 4.
	private int numberOfClasses;
	private int layout;
	private final static String TAG = "MainActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Get preferences file and set number of classes to 4 for
		// debugging purposes.
		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putInt(NUMBER_OF_CLASSES, 4);
		editor.commit();
		
		// Select correct corresponding layout for number of 
		// classes selected
		switch(settings.getInt(NUMBER_OF_CLASSES, 1)){
		case 1:
			layout = R.layout.main_one;
			break;
		case 2:
			layout = R.layout.main_two;
			break;
		case 4:
			layout = R.layout.main_four;
			break;
		}
		setContentView(layout);
		
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
