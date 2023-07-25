package com.beckachu.androidfeed.data.entities;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.beckachu.androidfeed.misc.Util;


@Entity
public class MyAppEntity {
    @PrimaryKey
    private String packageName;
    private String appName;

    @Ignore
    private Drawable icon;

    private boolean isFavorite; // User get an "unswipable" notification if this app has new noti
    private boolean isReceivingNoti; // Record only, no "unswipable" stuff


    public MyAppEntity() {
        this.isFavorite = false;
        this.isReceivingNoti = true;
    }

    public MyAppEntity(Context context, String packageName) {
        this.packageName = packageName;
        this.appName = Util.getAppNameFromPackage(context, packageName, false);
        this.icon = Util.getAppIconFromPackage(context, packageName);
        this.isFavorite = false;
        this.isReceivingNoti = true;
    }


    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isReceivingNoti() {
        return isReceivingNoti;
    }

    public void setReceivingNoti(boolean receivingNoti) {
        isReceivingNoti = receivingNoti;
    }
}
