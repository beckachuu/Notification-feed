package com.beckachu.androidfeed.services;


import android.content.Context;
import android.content.SharedPreferences;
import android.service.notification.StatusBarNotification;

import androidx.preference.PreferenceManager;

import com.beckachu.androidfeed.data.SharedPrefsManager;
import com.beckachu.androidfeed.data.entities.NotiEntity;
import com.beckachu.androidfeed.data.repositories.NotiRepository;
import com.beckachu.androidfeed.misc.Const;

public class NotificationHandler {
    private final Context context;
    private final NotiRepository notiRepository;
    private final SharedPreferences sharedPrefs;

    NotificationHandler(Context context) {
        this.context = context;
        this.notiRepository = new NotiRepository(context.getApplicationContext());
        this.sharedPrefs = context.getApplicationContext().getSharedPreferences(SharedPrefsManager.DEFAULT_NAME, Context.MODE_PRIVATE);
    }

    void handlePosted(StatusBarNotification sbn) {
        NotiEntity notiEntity = new NotiEntity(context, sbn);
        final String lastKey = SharedPrefsManager.getString(sharedPrefs, SharedPrefsManager.LAST_NOTI_KEY, "");
        final String currentKey = sbn.getKey();
        final String lastTitle = SharedPrefsManager.getString(sharedPrefs, SharedPrefsManager.LAST_NOTI_TITLE, "");
        final String currentTitle = notiEntity.getTitle();
        final String lastText = SharedPrefsManager.getString(sharedPrefs, SharedPrefsManager.LAST_NOTI_TEXT, "");
        final String currentText = notiEntity.getText();
        if (lastKey.equals(currentKey) && lastTitle.equals(currentTitle) && lastText.equals(currentText)) {
            if (Const.DEBUG)
                System.out.println("DUPLICATED: [[[" + lastKey + "]]] (last key) vs [[[" + currentKey + "]]] (current key)");
            return;
        }

        // Now it's good to add new notification
        if (Const.DEBUG)
            System.out.println("NOT duplicated keys: [[[" + lastKey + "]]] (last key) vs [[[" + sbn.getKey() + "]]] (current key)");
        SharedPrefsManager.putString(sharedPrefs, SharedPrefsManager.LAST_NOTI_KEY, currentKey);
        SharedPrefsManager.putString(sharedPrefs, SharedPrefsManager.LAST_NOTI_TITLE, currentTitle);
        SharedPrefsManager.putString(sharedPrefs, SharedPrefsManager.LAST_NOTI_TEXT, currentText);
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