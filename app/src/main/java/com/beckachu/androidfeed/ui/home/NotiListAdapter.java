package com.beckachu.androidfeed.ui.home;

import android.app.Activity;
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
import com.beckachu.androidfeed.data.models.NotiModel;
import com.beckachu.androidfeed.data.repositories.NotiRepository;
import com.beckachu.androidfeed.misc.Const;
import com.beckachu.androidfeed.ui.noti_detail.DetailsActivity;

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
    private ArrayList<NotiModel> data = new ArrayList<>();
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
    public void onBindViewHolder(@NonNull NotiListViewHolder viewHolder, int position) {
        NotiModel notiModel = data.get(position);

        if (iconCache.containsKey(notiModel.getPackageName()) && iconCache.get(notiModel.getPackageName()) != null) {
            viewHolder.icon.setImageDrawable(iconCache.get(notiModel.getPackageName()));
        } else {
            viewHolder.icon.setImageResource(R.mipmap.ic_launcher);
        }

        viewHolder.item.setTag("" + notiModel.getId());
        viewHolder.title.setText(notiModel.getTitle());

        viewHolder.preview.setVisibility(View.GONE);
        viewHolder.text.setVisibility(View.VISIBLE);
        viewHolder.text.setText(notiModel.getText());

//        if (notiModel.getPreview().length() == 0) {
//        } else {
//            viewHolder.text.setVisibility(View.GONE);
//            viewHolder.preview.setVisibility(View.VISIBLE);
//            viewHolder.preview.setText(notiModel.getPreview());
//        }

        if (notiModel.shouldShowDate()) {
            viewHolder.date.setVisibility(View.VISIBLE);
            viewHolder.date.setText(notiModel.getDate());
        } else {
            viewHolder.date.setVisibility(View.GONE);
        }

        if (position == getItemCount() - 1) {
            loadMore(notiModel.getId());
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
                NotiModel notiModel = new NotiModel(context, notiEntity.getNid(), iconCache, notiEntity.toString(), format);

                String thisDate = notiModel.getDate();
                if (lastDate.equals(thisDate)) {
                    notiModel.setShowDate(false);
                }
                lastDate = thisDate;
                data.add(notiModel);
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

}
