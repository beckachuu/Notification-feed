package com.beckachu.androidfeed.misc;

import com.beckachu.androidfeed.BuildConfig;

public class Const {

    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static final Object LOCK_OBJECT = new Object();

    // Shared preferences keys
    public static final String LAST_NOTI_KEY = "LAST_KEY";

    // Feature flags
    public static final boolean ENABLE_ACTIVITY_RECOGNITION = true;
    public static final boolean ENABLE_LOCATION_SERVICE = true;

    // Preferences not shown in the UI
    public static final String PREF_LAST_ACTIVITY = "pref_last_activity";
    public static final String PREF_LAST_LOCATION = "pref_last_location";

    // Intent actions
    public static final String UPDATE_NEWEST = "com.beckachu.androidfeed.UPDATE_NEWEST";

}