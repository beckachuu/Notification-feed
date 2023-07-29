package com.beckachu.androidfeed.data;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SharedPrefsManager {
    public static final String DEFAULT_NAME = "General";

    public static final String UNREAD_COUNT = "UNREAD_COUNT";
    public static final String LAST_NOTI_KEY = "LAST_KEY";
    public static final String LAST_NOTI_TITLE = "LAST_NOTI_TITLE";
    public static final String LAST_NOTI_TEXT = "LAST_NOTI_TEXT";

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

    public static List<String> getStringList(SharedPreferences prefs, String key) {
        Set<String> set = prefs.getStringSet(key, null);
        if (set != null) {
            return new ArrayList<>(set);
        }
        return null;
    }

    public static void putStringList(SharedPreferences prefs, String key, List<String> list) {
        Set<String> set = new HashSet<>(list);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(key, set);
        editor.apply();
    }


}
