package com.beckachu.androidfeed.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.beckachu.androidfeed.R;
import com.beckachu.androidfeed.data.entities.NotiEntity;
import com.beckachu.androidfeed.data.repositories.NotiRepository;
import com.beckachu.androidfeed.misc.Const;
import com.beckachu.androidfeed.misc.Util;
import com.beckachu.androidfeed.ui.noti_detail.DetailsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

class NotiListAdapter extends RecyclerView.Adapter<NotiListViewHolder> {

    private final static int LIMIT = Integer.MAX_VALUE;
    private final static String PAGE_SIZE = "20";

    private DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());

    private Activity context;
    private ArrayList<DataItem> data = new ArrayList<>();
    private HashMap<String, Drawable> iconCache = new HashMap<>();
    private Handler handler = new Handler();

    private String lastDate = "";
    private boolean shouldLoadMore = true;
    private NotiRepository notiRepository;

    NotiListAdapter(Activity context) {
        this.context = context;
        this.notiRepository = new NotiRepository(context.getApplicationContext());
        loadMore(Integer.MAX_VALUE);
    }

    @NonNull
    @Override
    public NotiListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
        Incorrect context: Make sure that the context variable passed to the Intent
        constructor is an instance of an Activity. Using a non-activity context such as
        the application context can cause issues when starting an activity or using startActivityForResult.

        Missing activity declaration: Make sure that the DetailsActivity is declared
        in your app’s AndroidManifest.xml file with the appropriate intent filters.

        Null or incorrect ID: Check if the id variable retrieved from the view’s tag
        is not null and contains a valid ID for your data. You can use logging or breakpoints to verify this.

        Unhandled exceptions: There may be unhandled exceptions occurring when you click
        on a RecyclerView item. Check your logcat output for any error messages or stack traces that could provide more information.

        */
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noti_item, parent, false);
        NotiListViewHolder viewHolder = new NotiListViewHolder(view);
        viewHolder.item.setOnClickListener(v -> {
            String id = (String) v.getTag();
            if (id != null) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_ID, id);
                Pair<View, String> p1 = Pair.create(viewHolder.icon, "icon");
                @SuppressWarnings("unchecked") ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, p1);
                context.startActivityForResult(intent, 1, options.toBundle());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotiListViewHolder vh, int position) {
        DataItem item = data.get(position);

        if (iconCache.containsKey(item.getPackageName()) && iconCache.get(item.getPackageName()) != null) {
            vh.icon.setImageDrawable(iconCache.get(item.getPackageName()));
        } else {
            vh.icon.setImageResource(R.mipmap.ic_launcher);
        }

        vh.item.setTag("" + item.getId());
        vh.name.setText(item.getAppName());

        if (item.getPreview().length() == 0) {
            vh.preview.setVisibility(View.GONE);
            vh.text.setVisibility(View.VISIBLE);
            vh.text.setText(item.getText());
        } else {
            vh.text.setVisibility(View.GONE);
            vh.preview.setVisibility(View.VISIBLE);
            vh.preview.setText(item.getPreview());
        }

        if (item.shouldShowDate()) {
            vh.date.setVisibility(View.VISIBLE);
            vh.date.setText(item.getDate());
        } else {
            vh.date.setVisibility(View.GONE);
        }

        if (position == getItemCount() - 1) {
            loadMore(item.getId());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void loadMore(long afterId) {
        if (!shouldLoadMore) {
            if (Const.DEBUG) System.out.println("not loading more items");
            return;
        }

        if (Const.DEBUG) System.out.println("loading more items");
        int before = getItemCount();
        try {
            List<NotiEntity> olderNotis = notiRepository.getAllNotisOlderThanId(afterId);

            for (int i = 0; i < olderNotis.size(); i++) {
                NotiEntity notiEntity = olderNotis.get(i);
                DataItem dataItem = new DataItem(context, notiEntity.getNid(), notiEntity.toString());

                String thisDate = dataItem.getDate();
                if (lastDate.equals(thisDate)) {
                    dataItem.setShowDate(false);
                }
                lastDate = thisDate;
                data.add(dataItem);
            }

        } catch (Exception e) {
            if (Const.DEBUG) e.printStackTrace();
        }
        int after = getItemCount();

        if (before == after) {
            if (Const.DEBUG) System.out.println("no new items loaded: " + getItemCount());
            shouldLoadMore = false;
        }

        if (getItemCount() > LIMIT) {
            if (Const.DEBUG)
                System.out.println("reached the limit, not loading more items: " + getItemCount());
            shouldLoadMore = false;
        }

        handler.post(() -> notifyDataSetChanged());
    }

    private class DataItem {

        private long id;
        private String packageName;
        private String appName;
        private String text;
        private String preview;
        private String date;
        private boolean showDate;

        DataItem(Context context, long id, String str) {
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

}
