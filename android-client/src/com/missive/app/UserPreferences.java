package com.missive.app;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

/**
 * Manages user's application settings.
 */
public final class UserPreferences implements OnSharedPreferenceChangeListener {

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sp, final String key) {
        // TODO handle updated prefs
    }

    public void initialiseUsing(final SharedPreferences sp) {
        sp.registerOnSharedPreferenceChangeListener(this);
        // TODO init prefs
    }

}