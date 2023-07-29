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
    private final long id;
    private String packageName;
    private String appName;
    private String title;
    private String text;
    private String preview;
    private String date;
    private boolean showDate;

    public NotiModel(Context context, long id, HashMap<String, Drawable> iconCache, String str,
                     DateFormat format, String lastDate) {
        this.id = id;
        try {
            JSONObject json = new JSONObject(str);
            this.packageName = json.getString("packageName");
            this.appName = Util.getAppNameFromPackage(context, this.packageName, false);

            this.title = json.optString("title");
            this.text = json.optString("text");
            this.preview = (this.title + "\n" + this.text).trim();

            if (!iconCache.containsKey(this.packageName)) {
                iconCache.put(this.packageName, Util.getAppIconFromPackage(context, this.packageName));
            }

            this.date = format.format(json.optLong("systemTime"));
            this.showDate = !lastDate.equals(this.date);

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

    public String getTitle() {
        return title;
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
