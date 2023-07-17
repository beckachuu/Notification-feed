package com.beckachu.androidfeed;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.beckachu.androidfeed.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.beckachu.androidfeed.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // MENU
        Menu menu = navigationView.getMenu();
        populateNavDrawer(menu);
    }

    private void populateNavDrawer(Menu menu) {
        List<String> listTitle = new ArrayList<>();
        listTitle.add("one");
        listTitle.add("two");
        listTitle.add("three");

        // String itemTitle = "new_item";
        for (String itemTitle : listTitle) {
            int itemIconID = R.drawable.ic_menu_gallery;

            // Generate a new unique ID based on a string
            int newId = generateIdFromString(itemTitle);

            // Add a new item to the menu using the generated ID
            MenuItem newItem = menu.add(Menu.NONE, newId, Menu.NONE, itemTitle);
            newItem.setIcon(itemIconID);
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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
