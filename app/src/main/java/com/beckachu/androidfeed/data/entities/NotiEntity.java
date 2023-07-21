package com.beckachu.androidfeed.data.entities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import androidx.core.app.NotificationCompat;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.beckachu.androidfeed.misc.Const;
import com.beckachu.androidfeed.misc.Util;
import com.beckachu.androidfeed.services.NotificationListener;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

/**
 * This class represents the Entity stored in the database
 */
@Entity
public class NotiEntity {
    @Ignore
    public final boolean LOG_TEXT;

    @Ignore
    public Context context;
    @Ignore
    public android.app.Notification noti;

    // General
    public String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

    public boolean isClearable() {
        return isClearable;
    }

    public void setClearable(boolean clearable) {
        isClearable = clearable;
    }

    public boolean isOngoing() {
        return isOngoing;
    }

    public void setOngoing(boolean ongoing) {
        isOngoing = ongoing;
    }

    public long getWhen() {
        return when;
    }

    public void setWhen(long when) {
        this.when = when;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getDefaults() {
        return defaults;
    }

    public void setDefaults(int defaults) {
        this.defaults = defaults;
    }

    public int getLedARGB() {
        return ledARGB;
    }

    public void setLedARGB(int ledARGB) {
        this.ledARGB = ledARGB;
    }

    public int getLedOff() {
        return ledOff;
    }

    public void setLedOff(int ledOff) {
        this.ledOff = ledOff;
    }

    public int getLedOn() {
        return ledOn;
    }

    public void setLedOn(int ledOn) {
        this.ledOn = ledOn;
    }

    public int getRingerMode() {
        return ringerMode;
    }

    public void setRingerMode(int ringerMode) {
        this.ringerMode = ringerMode;
    }

    public boolean isScreenOn() {
        return isScreenOn;
    }

    public void setScreenOn(boolean screenOn) {
        isScreenOn = screenOn;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public String getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(String lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isGroupSummary() {
        return isGroupSummary;
    }

    public void setGroupSummary(boolean groupSummary) {
        isGroupSummary = groupSummary;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getActionCount() {
        return actionCount;
    }

    public void setActionCount(int actionCount) {
        this.actionCount = actionCount;
    }

    public boolean isLocalOnly() {
        return isLocalOnly;
    }

    public void setLocalOnly(boolean localOnly) {
        isLocalOnly = localOnly;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getInterruptionFilter() {
        return interruptionFilter;
    }

    public void setInterruptionFilter(int interruptionFilter) {
        this.interruptionFilter = interruptionFilter;
    }

    public int getListenerHints() {
        return listenerHints;
    }

    public void setListenerHints(int listenerHints) {
        this.listenerHints = listenerHints;
    }

    public boolean isMatchesInterruptionFilter() {
        return matchesInterruptionFilter;
    }

    public void setMatchesInterruptionFilter(boolean matchesInterruptionFilter) {
        this.matchesInterruptionFilter = matchesInterruptionFilter;
    }

    public int getRemoveReason() {
        return removeReason;
    }

    public void setRemoveReason(int removeReason) {
        this.removeReason = removeReason;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTickerText() {
        return tickerText;
    }

    public void setTickerText(String tickerText) {
        this.tickerText = tickerText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleBig() {
        return titleBig;
    }

    public void setTitleBig(String titleBig) {
        this.titleBig = titleBig;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextBig() {
        return textBig;
    }

    public void setTextBig(String textBig) {
        this.textBig = textBig;
    }

    public String getTextInfo() {
        return textInfo;
    }

    public void setTextInfo(String textInfo) {
        this.textInfo = textInfo;
    }

    public String getTextSub() {
        return textSub;
    }

    public void setTextSub(String textSub) {
        this.textSub = textSub;
    }

    public String getTextSummary() {
        return textSummary;
    }

    public void setTextSummary(String textSummary) {
        this.textSummary = textSummary;
    }

    public String getTextLines() {
        return textLines;
    }

    public void setTextLines(String textLines) {
        this.textLines = textLines;
    }

    public long postTime;
    public long systemTime;

    public boolean isClearable;
    public boolean isOngoing;

    public long when;
    public int number;
    public int flags;
    public int defaults;
    public int ledARGB;
    public int ledOff;
    public int ledOn;

    // Device
    public int ringerMode;
    public boolean isScreenOn;
    public int batteryLevel;
    public String batteryStatus;
    //    public boolean isConnected;
//    public String connectionType;
    public String lastActivity;
    public String lastLocation;

    // Compat
    public String group;
    public boolean isGroupSummary;
    public String category;
    public int actionCount;
    public boolean isLocalOnly;

    @Ignore
    public List people;
    public String style;

    // 16
    public int priority;

    // 18
    @PrimaryKey
    public int nid;
    public String tag;

    // 20
    public String key;
    public String sortKey;

    // 21
    public int visibility;
    public int color;
    public int interruptionFilter;
    public int listenerHints;
    public boolean matchesInterruptionFilter;

    // 26
    public int removeReason;

    // Text
    public String appName;
    public String tickerText;
    public String title;
    public String titleBig;
    public String text;
    public String textBig;
    public String textInfo;
    public String textSub;
    public String textSummary;
    public String textLines;

    public NotiEntity() {
        this.LOG_TEXT = true;
    }

    public NotiEntity(Context context, StatusBarNotification sbn, final boolean LOG_TEXT, int reason) {
        this.context = context;
        this.LOG_TEXT = LOG_TEXT;

        noti = sbn.getNotification();
        packageName = sbn.getPackageName();
        postTime = sbn.getPostTime();
        systemTime = System.currentTimeMillis();

        isClearable = sbn.isClearable();
        isOngoing = sbn.isOngoing();

        nid = sbn.getId();
        tag = sbn.getTag();

        key = sbn.getKey();
        sortKey = noti.getSortKey();

        removeReason = reason;

        extract();

        if (Const.ENABLE_ACTIVITY_RECOGNITION || Const.ENABLE_LOCATION_SERVICE) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            lastActivity = sp.getString(Const.PREF_LAST_ACTIVITY, null);
            lastLocation = sp.getString(Const.PREF_LAST_LOCATION, null);
        }
    }

    public void extract() {
        // General
        when = noti.when;
        flags = noti.flags;
        defaults = noti.defaults;
        ledARGB = noti.ledARGB;
        ledOff = noti.ledOffMS;
        ledOn = noti.ledOnMS;

        number = -1;

        // Device
        ringerMode = Util.getRingerMode(context);
        isScreenOn = Util.isScreenOn(context);
        batteryLevel = Util.getBatteryLevel(context);
        batteryStatus = Util.getBatteryStatus(context);
//        isConnected = Util.isNetworkAvailable(context);
//        connectionType = Util.getConnectivityType(context);

        // 16
        priority = noti.priority;

        // 21
        visibility = noti.visibility;
        color = noti.color;

        listenerHints = NotificationListener.getListenerHints();
        interruptionFilter = NotificationListener.getInterruptionFilter();
        NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
        NotificationListenerService.RankingMap rankingMap = NotificationListener.getRanking();
        if (rankingMap != null && rankingMap.getRanking(key, ranking)) {
            matchesInterruptionFilter = ranking.matchesInterruptionFilter();
        }

        // Compat
        group = NotificationCompat.getGroup(noti);
        isGroupSummary = NotificationCompat.isGroupSummary(noti);
        category = NotificationCompat.getCategory(noti);
        actionCount = NotificationCompat.getActionCount(noti);
        isLocalOnly = NotificationCompat.getLocalOnly(noti);

        Bundle extras = NotificationCompat.getExtras(noti);
        if (extras != null) {
            String[] tmp = extras.getStringArray(NotificationCompat.EXTRA_PEOPLE);
            people = tmp != null ? Arrays.asList(tmp) : null;
            style = extras.getString(NotificationCompat.EXTRA_TEMPLATE);
        }

        // Text
        if (LOG_TEXT) {
            appName = Util.getAppNameFromPackage(context, packageName, false);
            tickerText = Util.nullToEmptyString(noti.tickerText);

            if (extras != null) {
                title = Util.nullToEmptyString(extras.getCharSequence(NotificationCompat.EXTRA_TITLE));
                titleBig = Util.nullToEmptyString(extras.getCharSequence(NotificationCompat.EXTRA_TITLE_BIG));
                text = Util.nullToEmptyString(extras.getCharSequence(NotificationCompat.EXTRA_TEXT));
                textBig = Util.nullToEmptyString(extras.getCharSequence(NotificationCompat.EXTRA_BIG_TEXT));
                textInfo = Util.nullToEmptyString(extras.getCharSequence(NotificationCompat.EXTRA_INFO_TEXT));
                textSub = Util.nullToEmptyString(extras.getCharSequence(NotificationCompat.EXTRA_SUB_TEXT));
                textSummary = Util.nullToEmptyString(extras.getCharSequence(NotificationCompat.EXTRA_SUMMARY_TEXT));

                CharSequence[] lines = extras.getCharSequenceArray(NotificationCompat.EXTRA_TEXT_LINES);
                if (lines != null) {
                    textLines = "";
                    for (CharSequence line : lines) {
                        textLines += line + "\n";
                    }
                    textLines = textLines.trim();
                }
            }
        }
    }

    @Override
    public String toString() {
        try {
            JSONObject json = new JSONObject();

            // General
            json.put("packageName", packageName);
            json.put("postTime", postTime);
            json.put("systemTime", systemTime);
            json.put("offset", TimeZone.getDefault().getOffset(systemTime));
//            json.put("version", BuildConfig.VERSION_CODE);
            json.put("sdk", android.os.Build.VERSION.SDK_INT);

            json.put("isOngoing", isOngoing);
            json.put("isClearable", isClearable);

            json.put("when", when);
            json.put("number", number);
            json.put("flags", flags);
            json.put("defaults", defaults);
            json.put("ledARGB", ledARGB);
            json.put("ledOn", ledOn);
            json.put("ledOff", ledOff);

            // Device
            json.put("ringerMode", ringerMode);
            json.put("isScreenOn", isScreenOn);
            json.put("batteryLevel", batteryLevel);
            json.put("batteryStatus", batteryStatus);
//            json.put("isConnected", isConnected);
//            json.put("connectionType", connectionType);

            // Compat
            json.put("group", group);
            json.put("isGroupSummary", isGroupSummary);
            json.put("category", category);
            json.put("actionCount", actionCount);
            json.put("isLocalOnly", isLocalOnly);

            json.put("people", people == null ? 0 : people.size());
            json.put("style", style);
            //json.put("displayName",    displayName);

            // Text
            if (LOG_TEXT) {
                json.put("tickerText", tickerText);
                json.put("title", title);
                json.put("titleBig", titleBig);
                json.put("text", text);
                json.put("textBig", textBig);
                json.put("textInfo", textInfo);
                json.put("textSub", textSub);
                json.put("textSummary", textSummary);
                json.put("textLines", textLines);
            }

            json.put("appName", appName);

            // 16
            json.put("priority", priority);

            // 18
            json.put("nid", nid);
            json.put("tag", tag);

            // 20
            json.put("key", key);
            json.put("sortKey", sortKey);

            // 21
            json.put("visibility", visibility);
            json.put("color", color);
            json.put("interruptionFilter", interruptionFilter);
            json.put("listenerHints", listenerHints);
            json.put("matchesInterruptionFilter", matchesInterruptionFilter);

            // 26
            if (removeReason != -1) {
                json.put("removeReason", removeReason);
            }

            // Activity
            if (Const.ENABLE_ACTIVITY_RECOGNITION && lastActivity != null) {
                json.put("lastActivity", new JSONObject(lastActivity));
            }

            // Location
            if (Const.ENABLE_LOCATION_SERVICE && lastLocation != null) {
                json.put("lastLocation", new JSONObject(lastLocation));
            }

            return json.toString();
        } catch (Exception e) {
            if (Const.DEBUG) e.printStackTrace();
            return null;
        }
    }

}
