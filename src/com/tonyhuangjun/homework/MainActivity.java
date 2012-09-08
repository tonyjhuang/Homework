package com.tonyhuangjun.homework;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	private final static String CLASS_TITLE = "class_title_";
	private final static String CLASS_BODY = "class_body_";
	private final static String NUMBER_OF_CLASSES = "number_of_classes";
	// As of right now, the possible ints are 1, 2, and 4.
	private int numberOfClasses;
	private int layout;
	private final static String TAG = "MainActivity";
	
	private TextView classTitle1, classTitle2, classTitle3,
		classTitle4, classTitle5, classTitle6, classTitle7,
		classTitle8;
	private TextView classBody1, classBody2, classBody3,
		classBody4, classBody5, classBody6, classBody7,
		classBody8;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Get preferences file and set number of classes to 4 for
		// debugging purposes.
		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putInt(NUMBER_OF_CLASSES, 4);
		for(int i = 1; i < 9; i++){
			editor.putString(CLASS_TITLE + i, "Class " + i);
		}
		for(int j = 1; j < 9; j++){
			editor.putString(CLASS_BODY + j, "Description.");
		}
		editor.commit();
		numberOfClasses = settings.getInt(NUMBER_OF_CLASSES, 1);
		
		// Inflate the appropriate layout
		switch(numberOfClasses){
		case 4:
			layout = R.layout.main_four;	
			break;
		case 2:
			layout = R.layout.main_four;
			break;
		case 1:
			layout = R.layout.main_four;
			break;
		}
		
		setContentView(layout);
		
		// Initialize the handlers and populate the TextViews.
		switch(numberOfClasses){
		case 4:
			classTitle4 = (TextView) findViewById(R.id.ClassTitle4);
			classTitle3 = (TextView) findViewById(R.id.ClassTitle3);
			
			classTitle4.setText(settings.getString(CLASS_TITLE + 4, "Null"));
			classTitle3.setText(settings.getString(CLASS_TITLE + 3, "Null"));
			
			classBody4 = (TextView) findViewById(R.id.ClassBody4);
			classBody3 = (TextView) findViewById(R.id.ClassBody3);
			
			classBody4.setText(settings.getString(CLASS_BODY + 4, "Null"));
			classBody3.setText(settings.getString(CLASS_BODY + 3, "Null"));
		case 2:
			classTitle2 = (TextView) findViewById(R.id.ClassTitle2);
			classTitle2.setText(settings.getString(CLASS_TITLE + 2, "Null"));
			
			classBody2 = (TextView) findViewById(R.id.ClassBody2);
			classBody2.setText(settings.getString(CLASS_BODY + 2, "Null"));
		case 1:
			classTitle1 = (TextView) findViewById(R.id.ClassTitle1);
			classTitle1.setText(settings.getString(CLASS_TITLE + 1, "Null"));
			
			classBody1 = (TextView) findViewById(R.id.ClassBody1);
			classBody1.setText(settings.getString(CLASS_BODY + 1, "Null"));
			break;
		}
		
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
