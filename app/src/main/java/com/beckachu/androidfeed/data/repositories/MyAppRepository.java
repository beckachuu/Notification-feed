package com.beckachu.androidfeed.data.repositories;

import android.content.Context;

import com.beckachu.androidfeed.data.AppDatabase;
import com.beckachu.androidfeed.data.entities.MyAppEntity;
import com.beckachu.androidfeed.data.local.dao.MyAppDao;
import com.beckachu.androidfeed.misc.Const;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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


    public int countAllApps() {
        Future<Integer> future = executor.submit(myAppDao::countAll);

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return 0;
        }
    }

    public List<MyAppEntity> getAllAppByNameAsc() {
        Future<List<MyAppEntity>> future = executor.submit(myAppDao::getAllByNameAsc);

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            if (Const.DEBUG) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public Set<String> getPackageNamesFromNoti() {
        Future<Set<String>> future = executor.submit(() -> new HashSet<>(myAppDao.getPackageNamesFromNoti()));

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            if (Const.DEBUG) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void addApp(MyAppEntity myAppEntity) {
        executor.execute(() -> {
            synchronized (Const.LOCK_OBJECT) {
                myAppDao.insertApp(myAppEntity);
            }
        });
    }


    public boolean updateAppFavorite(String packageName, boolean pref) {
        Future<Boolean> future = executor.submit(() -> {
            synchronized (Const.LOCK_OBJECT) {
                return myAppDao.setFavorite(packageName, pref) != 0;
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            if (Const.DEBUG) {
                e.printStackTrace();
            }
            return false;
        }
    }


    public boolean updateReceiveNotiPref(String packageName, boolean pref) {
        Future<Boolean> future = executor.submit(() -> {
            synchronized (Const.LOCK_OBJECT) {
                return myAppDao.setReceiveNoti(packageName, pref) != 0;
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            if (Const.DEBUG) e.printStackTrace();
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
