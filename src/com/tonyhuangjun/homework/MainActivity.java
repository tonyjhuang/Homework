package com.tonyhuangjun.homework;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity {
	private final static String CLASS_TITLE = "class_title_";
	private final static String CLASS_BODY = "class_body_";
	// Class Status: true = unfinished(bold), false = finished(normal)
	private final static String CLASS_STATUS = "class_status_";
	private final static String NUMBER_OF_CLASSES = "number_of_classes";
	
	private final int TITLE_UNFINISHED = Color.parseColor("#99990000");
	private final int BODY_UNFINISHED = Color.parseColor("#75CC0000");
	private final int TITLE_FINISHED = Color.parseColor("#7566CC00");
	private final int BODY_FINISHED = Color.parseColor("#7599CC00");

	// As of right now, the possible ints are 1, 2, and 4.
	private int numberOfClasses;
	private final static String TAG = "MainActivity";

	private SharedPreferences settings;
	private Editor editor;

	private ViewGroup parent;

	private TextView classTitle1, classTitle2, classTitle3, classTitle4,
			classTitle5, classTitle6, classTitle7, classTitle8;
	private TextView classBody1, classBody2, classBody3, classBody4,
			classBody5, classBody6, classBody7, classBody8;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get preferences file and set number of classes to 4 for
		// debugging purposes.
		settings = getSharedPreferences("Default", MODE_PRIVATE);
		editor = settings.edit();

		// Create dummy titles in settings.
		populateDebug();

		parent = (ViewGroup) findViewById(R.id.TopLayout);

	}

	protected void onResume() {
		super.onResume();

		Log.d(TAG, "MainActivity resuming...");
		Log.d(TAG, "Previous number of classes = " + numberOfClasses);
		Log.d(TAG, "Refreshing number of classes....");
		refreshNumberOfClasses();
		Log.d(TAG, "Refresh completed. New number of classes = "
				+ numberOfClasses);
		refreshLayout();

		getHandlers();
		fillData();
	}

	private void refreshNumberOfClasses() {
		numberOfClasses = Integer.valueOf(settings.getString(NUMBER_OF_CLASSES,
				"1"));
	}

	// Applies the layout corresponding to the current number of classes.
	private void refreshLayout() {
		switch (numberOfClasses) {
		// 4 and 3 classes have the same layout, but different handlers.
		case 4:
		case 3:
			setContentView(R.layout.main_four);
			break;
		case 2:
			setContentView(R.layout.main_two);
			break;
		default:
			setContentView(R.layout.main_one);
			break;
		}

		Log.d(TAG, "Layout refreshed! Currenet number of classes = "
				+ numberOfClasses);
		getHandlers();
		fillData();
	}

	private void getHandlers() {
		switch (numberOfClasses) {
		case 4:
			// Get title and body handlers and set text.
			classTitle4 = (TextView) findViewById(R.id.ClassTitle4);
			classBody4 = (TextView) findViewById(R.id.ClassBody4);
		case 3:
			classTitle3 = (TextView) findViewById(R.id.ClassTitle3);
			classBody3 = (TextView) findViewById(R.id.ClassBody3);
		case 2:
			classTitle2 = (TextView) findViewById(R.id.ClassTitle2);
			classBody2 = (TextView) findViewById(R.id.ClassBody2);
		default:
			classTitle1 = (TextView) findViewById(R.id.ClassTitle1);
			classBody1 = (TextView) findViewById(R.id.ClassBody1);
		}
	}

	private void fillData() {
		// Initialize the handlers and populate the TextViews.
		switch (numberOfClasses) {
		case 4:
			classTitle4.setText(settings.getString(CLASS_TITLE + 4, "Null"));
			classBody4.setText(settings.getString(CLASS_BODY + 4, "Null"));
		case 3:
			classTitle3.setText(settings.getString(CLASS_TITLE + 3, "Null"));
			classBody3.setText(settings.getString(CLASS_BODY + 3, "Null"));

			if (settings.getBoolean(CLASS_STATUS + 3, true))
				classTitle3.setTypeface(Typeface.DEFAULT_BOLD);
		case 2:
			classTitle2.setText(settings.getString(CLASS_TITLE + 2, "Null"));
			classBody2.setText(settings.getString(CLASS_BODY + 2, "Null"));

			if (settings.getBoolean(CLASS_STATUS + 2, true))
				classTitle2.setTypeface(Typeface.DEFAULT_BOLD);
		default:
			classTitle1.setText(settings.getString(CLASS_TITLE + 1, "Null"));
			classBody1.setText(settings.getString(CLASS_BODY + 1, "Null"));

			if (settings.getBoolean(CLASS_STATUS + 1, true))
				classTitle1.setTypeface(Typeface.DEFAULT_BOLD);
			break;
		}
		style();
	}
	
	// Add colors and typefaces base on class status.
	private void style() {
		switch(numberOfClasses) {

		case 4:
			// UnFinished? If true, bold. If not, normal.
			// Then add color!
			if (settings.getBoolean(CLASS_STATUS + 4, true)){
				classTitle4.setTypeface(Typeface.DEFAULT_BOLD);
				classTitle4.setBackgroundColor(TITLE_UNFINISHED);
				classBody4.setBackgroundColor(BODY_UNFINISHED);
			} else {
				classTitle4.setBackgroundColor(TITLE_FINISHED);
				classBody4.setBackgroundColor(BODY_FINISHED);
			}
		case 3:
			if (settings.getBoolean(CLASS_STATUS + 3, true)){
				classTitle3.setTypeface(Typeface.DEFAULT_BOLD);
				classTitle3.setBackgroundColor(TITLE_UNFINISHED);
				classBody3.setBackgroundColor(BODY_UNFINISHED);
			} else {
				classTitle3.setBackgroundColor(TITLE_FINISHED);
				classBody3.setBackgroundColor(BODY_FINISHED);
			}
		case 2:
			if (settings.getBoolean(CLASS_STATUS + 2, true)){
				classTitle2.setTypeface(Typeface.DEFAULT_BOLD);
				classTitle2.setBackgroundColor(TITLE_UNFINISHED);
				classBody2.setBackgroundColor(BODY_UNFINISHED);
			} else {
				classTitle2.setBackgroundColor(TITLE_FINISHED);
				classBody2.setBackgroundColor(BODY_FINISHED);
			}
		default:
			if (settings.getBoolean(CLASS_STATUS + 1, true)){
				classTitle1.setTypeface(Typeface.DEFAULT_BOLD);
				classTitle1.setBackgroundColor(TITLE_UNFINISHED);
				classBody1.setBackgroundColor(BODY_UNFINISHED);
			} else {
				classTitle1.setBackgroundColor(TITLE_FINISHED);
				classBody1.setBackgroundColor(BODY_FINISHED);
			}
			break;
		}
	}

	// Populates preferences file to contain
	// Class titles, bodies, and statuses for debugging.
	private void populateDebug() {
		for (int i = 1; i < 9; i++) {
			editor.putString(CLASS_TITLE + i, "Class " + i);
		}
		for (int j = 1; j < 9; j++) {
			editor.putString(CLASS_BODY + j, "Description.");
		}
		for (int k = 1; k < 9; k++) {
			editor.putBoolean(CLASS_STATUS + k, true);
		}
		editor.commit();
	}

	public void classTitle4Click(View v) {
		flip(4);
	}

	public void classTitle3Click(View v) {
		flip(3);
	}

	public void classTitle2Click(View v) {
		flip(2);
	}

	public void classTitle1Click(View v) {
		flip(1);
	}

	public void flip(int i) {
		if (settings.getBoolean(CLASS_STATUS + i, true)) {
			editor.putBoolean(CLASS_STATUS + i, false);
			switch (i) {
			case 1:
				classTitle1.setTypeface(Typeface.DEFAULT);
				break;
			case 2:
				classTitle2.setTypeface(Typeface.DEFAULT);
				break;
			case 3:
				classTitle3.setTypeface(Typeface.DEFAULT);
				break;
			case 4:
				classTitle4.setTypeface(Typeface.DEFAULT);
				break;
			}
		} else {
			editor.putBoolean(CLASS_STATUS + i, true);
			switch (i) {
			case 1:
				classTitle1.setTypeface(Typeface.DEFAULT_BOLD);
				break;
			case 2:
				classTitle2.setTypeface(Typeface.DEFAULT_BOLD);
				break;
			case 3:
				classTitle3.setTypeface(Typeface.DEFAULT_BOLD);
				break;
			case 4:
				classTitle4.setTypeface(Typeface.DEFAULT_BOLD);
				break;
			}
		}

		editor.commit();
		style();
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
		case R.id.menu_settings:
			startActivity(new Intent(this, Preferences.class));
			break;
		}
		return true;
	}
}
