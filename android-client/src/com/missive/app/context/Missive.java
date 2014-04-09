package com.missive.app.context;

import java.util.Locale;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.missive.app.UserPreferences;

/**
 * Provides access to application context, preferences and other application-wide settings.
 */
public final class Missive {
    private static final Missive INSTANCE = new Missive();
    private static boolean initialised = false;

    private Context mApplicationContext;
    private UserPreferences mUserPreferences;
    private Locale mLocale;

    public static Missive getInstance() {
        if (initialised) {
            return INSTANCE;
        }
        throw new IllegalStateException("Missive has not been initialised!");
    }

    static void initialise(final Application app, final SharedPreferences sharedPreferences) {
        INSTANCE.setApplicationContext(app.getApplicationContext());
        INSTANCE.setLocale(Locale.getDefault());

        final UserPreferences userPreferences = new UserPreferences();
        userPreferences.initialiseUsing(sharedPreferences);
        INSTANCE.setUserPreferences(userPreferences);
        initialised = true;
    }

    /**
     * Return the context of the single, global Application object of the current process
     * This is different from the context of an Activity. 
     * 
     * See: http://stackoverflow.com/questions/7298731
     */
    public Context getApplicationContext() {
        return mApplicationContext;
    }

    private void setApplicationContext(final Context applicationContext) {
        mApplicationContext = applicationContext;
    }

    public UserPreferences getUserPreferences() {
        return mUserPreferences;
    }

    private void setUserPreferences(final UserPreferences userPreferences) {
        mUserPreferences = userPreferences;
    }

    public Locale getLocale() {
        return mLocale;
    }

    private void setLocale(final Locale locale) {
        mLocale = locale;
    }
}