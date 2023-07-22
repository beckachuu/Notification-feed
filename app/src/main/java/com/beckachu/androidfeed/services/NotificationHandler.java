package com.beckachu.androidfeed.services;


import android.content.Context;
import android.content.SharedPreferences;
import android.service.notification.StatusBarNotification;

import androidx.preference.PreferenceManager;

import com.beckachu.androidfeed.data.entities.NotiEntity;
import com.beckachu.androidfeed.data.repositories.NotiRepository;
import com.beckachu.androidfeed.misc.Const;

public class NotificationHandler {
    private Context context;
    private NotiRepository notiRepository;
    private SharedPreferences sharedPref;

    NotificationHandler(Context context) {
        this.context = context;
        this.notiRepository = new NotiRepository(context.getApplicationContext());
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    void handlePosted(StatusBarNotification sbn) {
        if (sbn.isOngoing() && !sharedPref.getBoolean(Const.PREF_ONGOING, false)) {
            if (Const.DEBUG) System.out.println("posted ongoing!");
            return;
        }
        boolean text = sharedPref.getBoolean(Const.PREF_TEXT, true);
        NotiEntity notiEntity = new NotiEntity(context, sbn, text);

        notiRepository.addNoti(context, notiEntity);
        if (Const.DEBUG) System.out.println("added noti with ID = " + notiEntity.getNid());
    }

    void handleRemoved(StatusBarNotification sbn, int reason) {
//        if (sbn.isOngoing() && !sharedPref.getBoolean(Const.PREF_ONGOING, false)) {
//            if (Const.DEBUG) System.out.println("removed ongoing!");
//            return;
//        }
//        NotiEntity no = new NotiEntity(context, sbn, false, reason);
//        log(DatabaseHelper.RemovedEntry.TABLE_NAME, DatabaseHelper.RemovedEntry.COLUMN_NAME_CONTENT, no.toString());
    }

}