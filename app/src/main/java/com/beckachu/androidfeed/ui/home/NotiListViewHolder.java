package com.beckachu.androidfeed.ui.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.beckachu.androidfeed.R;

class NotiListViewHolder extends RecyclerView.ViewHolder {
    public TextView date;
    public LinearLayout item;
    public ImageView icon;
    public TextView title;
    public TextView text;
    public TextView preview;
    public boolean extended;

    NotiListViewHolder(View view) {
        super(view);
        date = view.findViewById(R.id.date);
        item = view.findViewById(R.id.item);
        icon = view.findViewById(R.id.icon);
        title = view.findViewById(R.id.noti_title);
        text = view.findViewById(R.id.text);
        preview = view.findViewById(R.id.preview);

        this.extended = false;
    }

}