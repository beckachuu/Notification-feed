package com.beckachu.androidfeed.data;

import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class SharedPrefsManager {
    public static final String DEFAULT_NAME = "General";

    public static final String UNREAD_COUNT = "UNREAD_COUNT";
    public static final String LAST_NOTI_KEY = "LAST_KEY";
    public static final String LAST_NOTI_TITLE = "LAST_NOTI_TITLE";
    public static final String LAST_NOTI_TEXT = "LAST_NOTI_TEXT";
    public static final String APP_LIST = "APP_LIST";
    public static final String RECORD_CHECKED = "RECORD_PREF";

    public static boolean getBool(SharedPreferences prefs, String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    public static void putBool(SharedPreferences prefs, String key, boolean value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getString(SharedPreferences prefs, String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public static void putString(SharedPreferences prefs, String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static int getInt(SharedPreferences prefs, String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    public static void putInt(SharedPreferences prefs, String key, int value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static HashSet<String> getStringSet(SharedPreferences prefs, String key) {
        Set<String> set = prefs.getStringSet(key, null);
        if (set != null) {
            return new HashSet<>(set);
        }
        return new HashSet<>();
    }

    public static void putStringSet(SharedPreferences prefs, String key, HashSet<String> list) {
        Set<String> set = new HashSet<>(list);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(key, set);
        editor.apply();
    }


}
