package com.beckachu.androidfeed;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.beckachu.androidfeed.data.entities.MyAppEntity;
import com.beckachu.androidfeed.data.entities.NotiEntity;
import com.beckachu.androidfeed.data.repositories.MyAppRepository;
import com.beckachu.androidfeed.data.repositories.NotiRepository;
import com.beckachu.androidfeed.databinding.ActivityMainBinding;
import com.beckachu.androidfeed.misc.Util;
import com.beckachu.androidfeed.services.NotificationListener;
import com.beckachu.androidfeed.ui.noti_detail.DetailsActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NotiRepository notiRepository;
    private MyAppRepository myAppRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notiRepository = new NotiRepository(this);
        myAppRepository = new MyAppRepository(this);

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
        startService(new Intent(this, NotificationListener.class));

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

        NavController navController = Navigation.findNavController(this, R.id.noti_list_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        // MENU
        Menu menu = navigationView.getMenu();
        populateNavDrawer(menu);
    }

    private void populateNavDrawer(Menu menu) {
        List<MyAppEntity> appList = myAppRepository.getAllAppByNameAsc();

        if (appList.size() == 0) {
            Set<String> packageNames = myAppRepository.getPackageNamesFromNoti();
            for (String packageName : packageNames) {
                myAppRepository.addApp(new MyAppEntity(this, packageName));
            }
        }

        appList = myAppRepository.getAllAppByNameAsc();
        if (appList.size() > 0) {
            for (MyAppEntity myApp : appList) {
                String appName = myApp.getAppName();
                String packageName = myApp.getPackageName();

                int newId = generateIdFromString(packageName);
                MenuItem newItem = menu.add(Menu.NONE, newId, Menu.NONE, appName);
                newItem.setIcon(Util.getAppIconFromByteArray(this, myApp.getIconByte()));
            }
        }
    }


    Map<String, Integer> idMap = new HashMap<>();

    private int generateIdFromString(String string) {
        int id = idMap.getOrDefault(string, 0);
        if (idMap.containsKey(string)) {
            id = id + 1;
        }
        idMap.put(string, id);
        return id;
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
