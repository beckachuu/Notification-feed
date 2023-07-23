package com.beckachu.androidfeed.data.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.beckachu.androidfeed.misc.Const;
import com.beckachu.androidfeed.misc.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.HashMap;

/**
 * This class represents the UI model
 * (which is kinda similar to the Entity, except
 * this doesn't need all the Entity's data fields)
 */
public class NotiModel {
    private long id;
    private String packageName;
    private String appName;
    private String text;
    private String preview;
    private String date;
    private boolean showDate;

    public NotiModel(Context context, long id, HashMap<String, Drawable> iconCache, String str, DateFormat format) {
        this.id = id;
        try {
            JSONObject json = new JSONObject(str);
            packageName = json.getString("packageName");
            appName = Util.getAppNameFromPackage(context, packageName, false);
            text = str;

            String title = json.optString("title");
            String text = json.optString("text");
            preview = (title + "\n" + text).trim();

            if (!iconCache.containsKey(packageName)) {
                iconCache.put(packageName, Util.getAppIconFromPackage(context, packageName));
            }

            date = format.format(json.optLong("systemTime"));
            showDate = true;
        } catch (JSONException e) {
            if (Const.DEBUG) e.printStackTrace();
        }
    }

    public long getId() {
        return id;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getAppName() {
        return appName;
    }

    public String getText() {
        return text;
    }

    public String getPreview() {
        return preview;
    }

    public String getDate() {
        return date;
    }

    public boolean shouldShowDate() {
        return showDate;
    }

    public void setShowDate(boolean showDate) {
        this.showDate = showDate;
    }

}
