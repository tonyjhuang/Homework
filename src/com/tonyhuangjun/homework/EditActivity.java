package com.tonyhuangjun.homework;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends Activity {
	private final static String TAG = "EditActivity";

	private SharedPreferences settings;
	private Editor editor;

	private String title;
	private String body;

	private TextView classTitle;
	private EditText classBody;
	
	private int id;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		Log.d(TAG, "EditActivity started.");

		// Get preferences file and set number of classes to 4 for
		// debugging purposes.
		settings = getSharedPreferences("Default", MODE_PRIVATE);
		editor = settings.edit();

		// Get id of class from passed in Intent.
		Log.d(TAG, "Attempting retrieval of intent");
		Intent i = getIntent();
		Log.d(TAG, "Intent retrieval successful.");
		id = i.getIntExtra("ID", 1);
		Log.d(TAG, "id = " + id);

		title = settings.getString(MainActivity.CLASS_TITLE + id, "Null");
		body = settings.getString(MainActivity.CLASS_BODY + id, "Null");

		// Handlers and text.
		classTitle = (TextView) findViewById(R.id.ClassTitle);
		classBody = (EditText) findViewById(R.id.ClassBody);

		classTitle.setText(title);
		classBody.setText(body);

		ActionBar ab = getActionBar();
		ab.setTitle(title);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case R.id.menu_save:
			editor.putString(MainActivity.CLASS_BODY + id, 
					String.valueOf(classBody.getText()));
			editor.commit();
			finish();
			break;
		}
		return true;
	}
}
