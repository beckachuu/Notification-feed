package com.beckachu.androidfeed.misc;

import com.beckachu.androidfeed.BuildConfig;

public class Const {

    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static final Object LOCK_OBJECT = new Object();

    public static final int NEGATIVE = -1;
    public static final String ALL_NOTI = "";
    public static int PAGE_SIZE = 20;

    // Feature flags
    public static final boolean ENABLE_ACTIVITY_RECOGNITION = true;
    public static final boolean ENABLE_LOCATION_SERVICE = true;

    // Preferences not shown in the UI
    public static final String PREF_LAST_ACTIVITY = "pref_last_activity";
    public static final String PREF_LAST_LOCATION = "pref_last_location";

    // Intent actions
    public static final String UPDATE_NEWEST = "com.beckachu.androidfeed.UPDATE_NEWEST";

}