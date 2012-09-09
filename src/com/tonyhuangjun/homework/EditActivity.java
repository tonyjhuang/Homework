package com.tonyhuangjun.homework;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

public class EditActivity extends Activity{
	private SharedPreferences settings;
	private Editor editor;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get preferences file and set number of classes to 4 for
		// debugging purposes.
		settings = getSharedPreferences("Default", MODE_PRIVATE);
		editor = settings.edit();
	}
}
