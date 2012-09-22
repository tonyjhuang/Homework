package com.tonyhuangjun.homework;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class Preferences extends PreferenceActivity {
	private static final String TAG = "Preferences";

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PreferenceManager prefMgr = getPreferenceManager();
		prefMgr.setSharedPreferencesName("Default");
		prefMgr.setSharedPreferencesMode(MODE_PRIVATE);
		addPreferencesFromResource(R.xml.preferences);
	}

	@Override
	protected void onStart() {
		getPrefs();
		super.onStart();
	}

	private void getPrefs() {
	    Log.d(TAG, "yo.");
	}

}
