package com.beckachu.androidfeed.services.broadcast;

import static com.beckachu.androidfeed.ui.home.NotiListAdapter.iconCache;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;

import com.beckachu.androidfeed.R;
import com.beckachu.androidfeed.data.models.NotiModel;
import com.beckachu.androidfeed.misc.Util;
import com.beckachu.androidfeed.ui.home.NotiListAdapter;

public class AppReceiver extends BroadcastReceiver {
    private final Menu menu;

    public AppReceiver(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Update menu
        NotiModel newNoti = NotiListAdapter.getNewestNoti();
        String packageName = newNoti.getPackageName();
        if (!iconCache.containsKey(packageName)) {
            Drawable appIcon = Util.getAppIconFromPackage(context, packageName);
            iconCache.put(packageName, appIcon);
            MenuItem newItem = menu.add(R.id.apps_group, packageName.hashCode(), 0, newNoti.getAppName());
            newItem.setIcon(appIcon);
        }
    }
}
