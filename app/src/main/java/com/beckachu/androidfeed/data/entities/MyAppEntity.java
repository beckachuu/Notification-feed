package com.beckachu.androidfeed.data.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.beckachu.androidfeed.misc.Util;

import java.io.ByteArrayOutputStream;


@Entity
public class MyAppEntity {
    @PrimaryKey
    @NonNull
    private String packageName;
    private String appName;

    @Ignore
    private Drawable icon;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] iconByte;

    private boolean isFavorite; // User get an "unswipable" notification if this app has new noti
    private boolean isReceivingNoti; // Record only, no "unswipable" stuff


    public MyAppEntity() {
        this.packageName = "";
        this.isFavorite = false;
        this.isReceivingNoti = true;
    }

    public MyAppEntity(Context context, String packageName) {
        this.packageName = packageName;
        this.appName = Util.getAppNameFromPackage(context, packageName, false);

        this.icon = Util.getAppIconFromPackage(context, packageName);
        Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        this.iconByte = stream.toByteArray();

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

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public byte[] getIconByte() {
        return iconByte;
    }

    public void setIconByte(byte[] iconByte) {
        this.iconByte = iconByte;
    }
}
