package com.tonyhuangjun.homework;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

	// As of right now, the possible ints are 1, 2, and 4.
	private int numberOfClasses;
	private int layout;
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
		settings = getPreferences(MODE_PRIVATE);
		editor = settings.edit();
		editor.putInt(NUMBER_OF_CLASSES, 1);
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
		numberOfClasses = settings.getInt(NUMBER_OF_CLASSES, 1);

		// Inflate the appropriate layout
		switch (numberOfClasses) {
		case 4:
			layout = R.layout.main_four;
			break;
		case 2:
			layout = R.layout.main_two;
			break;
		case 1:
			layout = R.layout.main_one;
			break;
		}

		setContentView(layout);

		// Initialize the handlers and populate the TextViews.
		switch (numberOfClasses) {
		case 4:
			// Get title handlers and set text.
			classTitle4 = (TextView) findViewById(R.id.ClassTitle4);
			classTitle3 = (TextView) findViewById(R.id.ClassTitle3);
			classTitle4.setText(settings.getString(CLASS_TITLE + 4, "Null"));
			classTitle3.setText(settings.getString(CLASS_TITLE + 3, "Null"));

			// UnFinished? If true, bold. If not, normal.
			if (settings.getBoolean(CLASS_STATUS + 4, true))
				classTitle4.setTypeface(Typeface.DEFAULT_BOLD);
			if (settings.getBoolean(CLASS_STATUS + 3, true))
				classTitle3.setTypeface(Typeface.DEFAULT_BOLD);

			Log.d(TAG, "" + settings.getBoolean(CLASS_STATUS + 4, false));

			// Get body handlers and set text.
			classBody4 = (TextView) findViewById(R.id.ClassBody4);
			classBody3 = (TextView) findViewById(R.id.ClassBody3);
			classBody4.setText(settings.getString(CLASS_BODY + 4, "Null"));
			classBody3.setText(settings.getString(CLASS_BODY + 3, "Null"));
		case 2:
			classTitle2 = (TextView) findViewById(R.id.ClassTitle2);
			classTitle2.setText(settings.getString(CLASS_TITLE + 2, "Null"));

			if (settings.getBoolean(CLASS_STATUS + 2, true))
				classTitle2.setTypeface(Typeface.DEFAULT_BOLD);

			classBody2 = (TextView) findViewById(R.id.ClassBody2);
			classBody2.setText(settings.getString(CLASS_BODY + 2, "Null"));
		case 1:
			classTitle1 = (TextView) findViewById(R.id.ClassTitle1);
			classTitle1.setText(settings.getString(CLASS_TITLE + 1, "Null"));

			if (settings.getBoolean(CLASS_STATUS + 1, true))
				classTitle1.setTypeface(Typeface.DEFAULT_BOLD);

			classBody1 = (TextView) findViewById(R.id.ClassBody1);
			classBody1.setText(settings.getString(CLASS_BODY + 1, "Null"));
			break;
		}

		parent = (ViewGroup) findViewById(R.id.TopLayout);

	}

	public void classTitle4Click(View v) { flip(4); }
	public void classTitle3Click(View v) { flip(3); }
	public void classTitle2Click(View v) { flip(2); }
	public void classTitle1Click(View v) { flip(1); }

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