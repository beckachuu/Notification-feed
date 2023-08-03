package com.beckachu.androidfeed;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.beckachu.androidfeed.data.entities.MyAppEntity;
import com.beckachu.androidfeed.data.repositories.MyAppRepository;
import com.beckachu.androidfeed.databinding.ActivityMainBinding;
import com.beckachu.androidfeed.misc.Const;
import com.beckachu.androidfeed.misc.Util;
import com.beckachu.androidfeed.services.broadcast.AppReceiver;
import com.beckachu.androidfeed.services.NotificationListener;
import com.beckachu.androidfeed.ui.settings.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private MyAppRepository myAppRepository;
    private HashMap<String, Drawable> iconCache = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myAppRepository = new MyAppRepository(this);
        startService(new Intent(this, NotificationListener.class));

        /*
         * Show "NotiEntity access" setting screen (in case the app didn't have this permission)
         */
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        ComponentName componentName = new ComponentName(this, NotificationListener.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            if (!notificationManager.isNotificationListenerAccessGranted(componentName)) {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);
            }
        }

        /*
         * Show "Autostart" setting screen (in case the app didn't have this permission)
         */
//        String manufacturer = "xiaomi";
//        if (manufacturer.equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
//            // open auto start screen where user can enable permission for your app
//            Intent intent = new Intent();
//            intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
//            startActivity(intent);
//        }


        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setItemIconTintList(null);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // MENU
        Menu menu = navigationView.getMenu();
        initNavDrawer(menu);

        // Auto add new app when new noti posted
        AppReceiver receiver = new AppReceiver(menu);
        IntentFilter filter = new IntentFilter(Const.UPDATE_NEWEST);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    private void initNavDrawer(Menu menu) {
        List<MyAppEntity> appList = myAppRepository.getAllAppByNameAsc();

        if (appList.size() == 0) {
            Set<String> packageNames = myAppRepository.getPackageNamesFromNoti();
            for (String packageName : packageNames) {
                myAppRepository.addApp(new MyAppEntity(this, packageName));
            }
        }

        populateNavDrawer(menu);
    }

    private void populateNavDrawer(Menu menu) {
        List<MyAppEntity> appList = myAppRepository.getAllAppByNameAsc();
        if (appList.size() > 0) {
            for (MyAppEntity myApp : appList) {
                String packageName = myApp.getPackageName();
                Drawable appIcon = Util.getAppIconFromByteArray(this, myApp.getIconByte());
                iconCache.put(packageName, appIcon);
                MenuItem newItem = menu.add(R.id.apps_group, packageName.hashCode(), Menu.NONE, myApp.getAppName());
                newItem.setIcon(appIcon);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.noti_list);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.noti_list_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

//        int id = item.getItemId();
//        Intent intent = new Intent(this, DetailsActivity.class);
//        intent.putExtra(DetailsActivity.EXTRA_ID, id);
//        startActivity(intent);
//
//        return super.onOptionsItemSelected(item);
    }

}
