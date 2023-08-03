package com.beckachu.androidfeed.services.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.beckachu.androidfeed.ui.home.NotiListAdapter;

public class NotificationReceiver extends BroadcastReceiver {
    private final NotiListAdapter adapter;

    public NotificationReceiver(NotiListAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Update adapter
        adapter.addNewestNotiToAdapter();
        adapter.notifyItemInserted(0);
    }
}
