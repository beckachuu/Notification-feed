package com.beckachu.androidfeed.service;


import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.beckachu.androidfeed.misc.Const;


public class NotificationListener extends NotificationListenerService {

    private static NotificationListener instance = null;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        instance = this;
    }

    @Override
    public void onListenerDisconnected() {
        instance = null;
        super.onListenerDisconnected();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        try {
            NotificationHandler notificationHandler = new NotificationHandler(this);
            notificationHandler.handlePosted(sbn);

            Log.v("GOT NOTI", sbn.getNotification().toString());
            System.out.println(sbn.getNotification().toString());

        } catch (Exception e) {
            if (Const.DEBUG) e.printStackTrace();
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        try {
            NotificationHandler notificationHandler = new NotificationHandler(this);
            notificationHandler.handleRemoved(sbn, -1);

            Log.v("REMOVED NOTI", sbn.getNotification().toString());
            System.out.println(sbn.getNotification().toString());
        } catch (Exception e) {
            if (Const.DEBUG) e.printStackTrace();
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap, int reason) {
        try {
            NotificationHandler notificationHandler = new NotificationHandler(this);
            notificationHandler.handleRemoved(sbn, reason);
        } catch (Exception e) {
            if (Const.DEBUG) e.printStackTrace();
        }
    }

    public static StatusBarNotification[] getAllActiveNotifications() {
        if (instance != null) {
            try {
                return instance.getActiveNotifications();
            } catch (Exception e) {
                if (Const.DEBUG) e.printStackTrace();
            }
        }
        return null;
    }

    public static StatusBarNotification[] getAllSnoozedNotifications() {
        if (instance != null) {
            try {
                return instance.getSnoozedNotifications();
            } catch (Exception e) {
                if (Const.DEBUG) e.printStackTrace();
            }
        }
        return null;
    }

    public static int getInterruptionFilter() {
        if (instance != null) {
            try {
                return instance.getCurrentInterruptionFilter();
            } catch (Exception e) {
                if (Const.DEBUG) e.printStackTrace();
            }
        }
        return -1;
    }

    public static int getListenerHints() {
        if (instance != null) {
            try {
                return instance.getCurrentListenerHints();
            } catch (Exception e) {
                if (Const.DEBUG) e.printStackTrace();
            }
        }
        return -1;
    }

    public static NotificationListenerService.RankingMap getRanking() {
        if (instance != null) {
            try {
                return instance.getCurrentRanking();
            } catch (Exception e) {
                if (Const.DEBUG) e.printStackTrace();
            }
        }
        return null;
    }

}
