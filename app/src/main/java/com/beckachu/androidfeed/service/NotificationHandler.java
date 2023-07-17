package com.beckachu.androidfeed.service;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.service.notification.StatusBarNotification;

import com.beckachu.androidfeed.misc.Const;
import com.beckachu.androidfeed.misc.DatabaseHelper;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;

public class NotificationHandler {

    public static final String BROADCAST = "com.beckachu.androidfeed.update";
    public static final String LOCK = "lock";

    private Context context;
    private DatabaseHelper dbHelper;
    private SharedPreferences sharedPref;

    NotificationHandler(Context context) {
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    void handlePosted(StatusBarNotification sbn) {
        if (sbn.isOngoing() && !sharedPref.getBoolean(Const.PREF_ONGOING, false)) {
            if (Const.DEBUG) System.out.println("posted ongoing!");
            return;
        }
        boolean text = sharedPref.getBoolean(Const.PREF_TEXT, true);
        NotificationObject no = new NotificationObject(context, sbn, text, -1);
        log(DatabaseHelper.PostedEntry.TABLE_NAME, DatabaseHelper.PostedEntry.COLUMN_NAME_CONTENT, no.toString());
    }

    void handleRemoved(StatusBarNotification sbn, int reason) {
        if (sbn.isOngoing() && !sharedPref.getBoolean(Const.PREF_ONGOING, false)) {
            if (Const.DEBUG) System.out.println("removed ongoing!");
            return;
        }
        NotificationObject no = new NotificationObject(context, sbn, false, reason);
        log(DatabaseHelper.RemovedEntry.TABLE_NAME, DatabaseHelper.RemovedEntry.COLUMN_NAME_CONTENT, no.toString());
    }

    private void log(String tableName, String columnName, String content) {
        try {
            if (content != null) {
                synchronized (LOCK) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(columnName, content);
                    db.insert(tableName, "null", values);
                    db.close();
                    dbHelper.close();
                }

                Intent local = new Intent();
                local.setAction(BROADCAST);
                LocalBroadcastManager.getInstance(context).sendBroadcast(local);
            }
        } catch (Exception e) {
            if (Const.DEBUG) e.printStackTrace();
        }
    }


}