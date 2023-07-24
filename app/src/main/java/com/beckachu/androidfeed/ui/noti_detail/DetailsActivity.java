package com.beckachu.androidfeed.ui.noti_detail;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.beckachu.androidfeed.R;
import com.beckachu.androidfeed.data.entities.NotiEntity;
import com.beckachu.androidfeed.data.repositories.NotiRepository;
import com.beckachu.androidfeed.misc.Const;
import com.beckachu.androidfeed.misc.Util;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_ACTION = "action";
    public static final String ACTION_REFRESH = "refresh";

    private static final boolean SHOW_RELATIVE_DATE_TIME = true;
    private NotiRepository notiRepository;

    private String id;
    private String packageName;
    private int appUid;
    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        this.notiRepository = new NotiRepository(getApplicationContext());

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra(EXTRA_ID);
            if (id != null) {
                loadDetails(id);
            } else {
                finishWithToast();
            }
        } else {
            finishWithToast();
        }

//        NavController navController = Navigation.findNavController(this, R.id.noti_list);
//
//        // Simplified toolbar setup
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        NavigationUI.setupWithNavController(toolbar, navController);
    }

    @Override
    protected void onPause() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
        super.onPause();
    }

    private void loadDetails(String id) {
        JSONObject json = null;
        String str = "error";
        try {
            NotiEntity notiEntity = notiRepository.getNotiById(id);

            if (notiEntity != null) {
                str = notiEntity.toString();
                json = new JSONObject(str);
            }
        } catch (Exception e) {
            if (Const.DEBUG) e.printStackTrace();
        }
        TextView tvJSON = findViewById(R.id.json);
        tvJSON.setText(str);

        CardView card = findViewById(R.id.card);
        CardView buttons = findViewById(R.id.buttons);
        if (json != null) {
            packageName = json.optString("packageName", "???");
            String titleText = json.optString("title");
            String contentText = json.optString("text");
            String text = (titleText + "\n" + contentText).trim();
            if (!"".equals(text)) {
                card.setVisibility(View.VISIBLE);
                ImageView icon = findViewById(R.id.icon);
                icon.setImageDrawable(Util.getAppIconFromPackage(this, packageName));
                TextView tvName = findViewById(R.id.noti_title);
                tvName.setText(Util.getAppNameFromPackage(this, packageName, false));
                TextView tvText = findViewById(R.id.text);
                tvText.setText(text);
                TextView tvDate = findViewById(R.id.date);
                if (SHOW_RELATIVE_DATE_TIME) {
                    tvDate.setText(DateUtils.getRelativeDateTimeString(
                            this,
                            json.optLong("systemTime"),
                            DateUtils.MINUTE_IN_MILLIS,
                            DateUtils.WEEK_IN_MILLIS,
                            0));
                } else {
                    DateFormat format = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT, Locale.getDefault());
                    tvDate.setText(format.format(json.optLong("systemTime")));
                }

                try {
                    ApplicationInfo app = this.getPackageManager().getApplicationInfo(packageName, 0);
                    buttons.setVisibility(View.VISIBLE);
                    appUid = app.uid;
                } catch (PackageManager.NameNotFoundException e) {
                    if (Const.DEBUG) e.printStackTrace();
                    buttons.setVisibility(View.GONE);
                }
            } else {
                card.setVisibility(View.GONE);

            }
        } else {
            card.setVisibility(View.GONE);
            buttons.setVisibility(View.GONE);
        }
    }

    private void finishWithToast() {
        Toast.makeText(this, R.string.details_error, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void confirmDelete() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.delete_dialog_text)
                .setPositiveButton(R.string.delete_dialog_yes, doDelete)
                .setNegativeButton(R.string.delete_dialog_no, null)
                .show();
    }

    private DialogInterface.OnClickListener doDelete = (dialog, which) -> {
        int affectedRows = 0;
        try {
            affectedRows = notiRepository.deleteNoti(id);
        } catch (Exception e) {
            if (Const.DEBUG) e.printStackTrace();
        }

        if (affectedRows > 0) {
            Intent data = new Intent();
            data.putExtra(EXTRA_ACTION, ACTION_REFRESH);
            setResult(RESULT_OK, data);
            finish();
        }
    };

    public void openNotificationSettings(View v) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", packageName);
            startActivity(intent);
        } catch (Exception e) {
            if (Const.DEBUG) e.printStackTrace();
        }
    }

}