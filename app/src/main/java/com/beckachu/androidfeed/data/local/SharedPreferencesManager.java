package com.beckachu.androidfeed.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String DEFAULT_PREFS_NAME = "MyPrefs";

    private static SharedPreferences prefs = null;

    /*
     * Pass the same Context object to this method,
     * otherwise multiple instances of the SharedPreferences object
     * may be created for the same preference file
     *
     * You can do it by calling the getApplicationContext()
     */
    public static SharedPreferences getSharedPrefs(Context context, String prefsName, int mode) {
        if (prefsName == null) {
            prefsName = DEFAULT_PREFS_NAME;
        }
        if (prefs == null) {
            prefs = context.getSharedPreferences(prefsName, mode);
        }
        return prefs;
    }
}
