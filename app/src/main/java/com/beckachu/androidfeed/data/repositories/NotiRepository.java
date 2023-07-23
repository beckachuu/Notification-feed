package com.beckachu.androidfeed.data.repositories;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.beckachu.androidfeed.data.AppDatabase;
import com.beckachu.androidfeed.data.entities.NotiEntity;
import com.beckachu.androidfeed.data.local.dao.NotiDao;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This class handles mapping between the Entity and the UI model
 * (So basically the UI layer will use this class to get all the data it needs,
 * without directly interacting with the database)
 */
public class NotiRepository {
    public static final String BROADCAST = "com.beckachu.androidfeed.update";
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final NotiDao notiDao;

    public NotiRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        notiDao = db.notiDao();
    }

    /*
      Common naming conventions for Repository methods:
      - getAll: Retrieves all records of a particular type from the data source.
      - getById: Retrieves a single record of a particular type by its unique identifier.
      - add (insert): Inserts a new record into the data source.
      - update: Updates an existing record in the data source.
      - delete (remove): Deletes an existing record from the data source.
      - find: Retrieves records that meet certain criteria.
     */

    public List<NotiEntity> getAllNotisByIdAsc() {
        Future<List<NotiEntity>> future = executor.submit(() -> {
            return notiDao.getAllByIdAsc();
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public List<NotiEntity> getAllNotisOlderThanId(long id) {
        Future<List<NotiEntity>> future = executor.submit(() -> {
            return notiDao.getAllOlderThanId(id);
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public NotiEntity getNotiById(String id) {
        int intId = Integer.parseInt(id);
        Future<NotiEntity> future = executor.submit(() -> {
            return notiDao.getById(intId);
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public void addNoti(Context context, NotiEntity notiEntity) {
        executor.execute(() -> {
            notiDao.insert(notiEntity);
            new Handler(Looper.getMainLooper()).post(() -> {
                Intent local = new Intent();
                local.setAction(BROADCAST);
                LocalBroadcastManager.getInstance(context).sendBroadcast(local);
            });
        });
    }

    public int deleteNoti(String id) {
        Future<Integer> future = executor.submit(() -> {
            int intId = Integer.parseInt(id);
            return notiDao.delete(intId);
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return 0;
        }
    }

    public int deleteNoti(int id) {
        Future<Integer> future = executor.submit(() -> {
            return notiDao.delete(id);
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return 0;
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
