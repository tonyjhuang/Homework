package com.tonyhuangjun.homework;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends SherlockActivity {
	private final static String TAG = "EditActivity";

	private SharedPreferences settings;
	private Editor editor;

	private String title;
	private String body;

	private View classTitle;
	private EditText classBody;
	private TextView classStatus;

	private ViewGroup parent;
	private int index;

	private int id;
	private boolean edit;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		Log.d(TAG, "EditActivity started.");

		parent = (ViewGroup) findViewById(R.id.TopLayout);

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
		classStatus = (TextView) findViewById(R.id.EditTitleStatus);

		((TextView) classTitle).setText(title);
		classBody.setText(body);
		
		// Color classStatus
		classStatus.setBackgroundColor(MainActivity.TITLE_FINISHED);

		index = parent.indexOfChild(classTitle);

		edit = false;

		ActionBar ab = getSupportActionBar();
		ab.setTitle(title);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case R.id.menu_title:
			String title;
			parent.removeView(classTitle);
			if (edit) {
				title = String.valueOf(((EditText) classTitle).getText());
				// Replace EditText with TextView. Flip boolean.
				classTitle = getLayoutInflater().inflate(R.layout.view_title,
						parent, false);
				parent.addView(classTitle, index);
				((TextView) classTitle).setText(title);
			} else {
				title = String.valueOf(((TextView) classTitle).getText());
				// Replace TextView with EditText
				classTitle = getLayoutInflater().inflate(R.layout.edit_title,
						parent, false);
				parent.addView(classTitle, index);
				((EditText) classTitle).setText(title);
			}
			edit = !edit;
			break;
		case R.id.menu_save:
			if (edit)
				editor.putString(MainActivity.CLASS_TITLE + id,
						String.valueOf(((EditText) classTitle).getText()));
			else
				editor.putString(MainActivity.CLASS_TITLE + id,
						String.valueOf(((TextView) classTitle).getText()));

			editor.putString(MainActivity.CLASS_BODY + id,
					String.valueOf(classBody.getText()));
			editor.commit();
			finish();
			break;
		}
		return true;
	}
}
