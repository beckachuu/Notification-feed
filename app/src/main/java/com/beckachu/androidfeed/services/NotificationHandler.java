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

    NotificationHandler(Context context) {
        this.context = context;
        this.notiRepository = new NotiRepository(context.getApplicationContext());
    }

    void handlePosted(StatusBarNotification sbn) {
        if (sbn.isOngoing()) {
            if (Const.DEBUG) System.out.println("got ongoing noti!");
        }
        NotiEntity notiEntity = new NotiEntity(context, sbn);
        notiRepository.addNoti(context, notiEntity);
        if (Const.DEBUG)
            System.out.println("added noti " + notiEntity.getNid() + ": " + notiEntity.getText());
    }

    /**
     * TODO: implement when needed
     *
     * @param sbn StatusBarNotification object
     */
    void handleRemoved(StatusBarNotification sbn) {
//        if (sbn.isOngoing() && !sharedPref.getBoolean(Const.PREF_ONGOING, false)) {
//            if (Const.DEBUG) System.out.println("removed ongoing!");
//            return;
//        }
//        NotiEntity no = new NotiEntity(context, sbn, false);
//        log(DatabaseHelper.RemovedEntry.TABLE_NAME, DatabaseHelper.RemovedEntry.COLUMN_NAME_CONTENT, no.toString());
    }

}