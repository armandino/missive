package com.missive.app.context;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Provides access to application's life-cycle.
 */
public class MissiveApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Missive.initialise(this, sharedPrefs);
    }
}
