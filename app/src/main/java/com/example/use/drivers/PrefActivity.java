package com.example.use.drivers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by use on 27.03.17.
 */
public class PrefActivity extends PreferenceActivity {

    private int mode = Activity.MODE_PRIVATE;
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_pref);
    }
}
