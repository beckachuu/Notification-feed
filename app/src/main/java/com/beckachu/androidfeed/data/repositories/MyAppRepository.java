package com.beckachu.androidfeed.data.repositories;

import android.content.Context;

import com.beckachu.androidfeed.data.AppDatabase;
import com.beckachu.androidfeed.data.entities.MyAppEntity;
import com.beckachu.androidfeed.data.local.dao.MyAppDao;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyAppRepository {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final MyAppDao myAppDao;

    public MyAppRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        myAppDao = db.myAppDao();
    }

    public List<MyAppEntity> getAllAppByNameAsc() {
        Future<List<MyAppEntity>> future = executor.submit(myAppDao::getAllByNameAsc);

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public void addApp(MyAppEntity myAppEntity) {
        executor.execute(() -> {
            myAppDao.insertApp(myAppEntity);
        });
    }

    public boolean updateAppFavorite(String packageName, boolean pref) {
        Future<Boolean> future = executor.submit(() -> {
            return myAppDao.setFavorite(packageName, pref);
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }

    public boolean updateReceiveNotiPref(String packageName, boolean pref) {
        Future<Boolean> future = executor.submit(() -> {
            return myAppDao.setReceiveNoti(packageName, pref);
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }

    /**
     * For synchronizing remote and local data
     * <p>
     * TODO: when remote is implemented
     */
    public void synchronize() {
//        val userData = networkDataSource.fetchUserData()
//        localDataSource.saveUserData(userData)
    }
}
