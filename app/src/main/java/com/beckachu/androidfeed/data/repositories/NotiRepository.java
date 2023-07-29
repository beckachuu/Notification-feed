package com.beckachu.androidfeed.data.repositories;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.beckachu.androidfeed.data.AppDatabase;
import com.beckachu.androidfeed.data.entities.MyAppEntity;
import com.beckachu.androidfeed.data.entities.NotiEntity;
import com.beckachu.androidfeed.data.local.dao.MyAppDao;
import com.beckachu.androidfeed.data.local.dao.NotiDao;
import com.beckachu.androidfeed.misc.Const;

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
    private final MyAppDao myAppDao;

    public NotiRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        notiDao = db.notiDao();
        myAppDao = db.myAppDao();
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
        Future<List<NotiEntity>> future = executor.submit(notiDao::getAllByIdAsc);

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public List<NotiEntity> getAllNotisOlderThanId(long id) {
        Future<List<NotiEntity>> future = executor.submit(() -> {
            if (id != Const.NEGATIVE)
                return notiDao.getAllOlderThanId(id, Const.PAGE_SIZE);
            else return notiDao.getNewest(Const.PAGE_SIZE);
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            if (Const.DEBUG) e.printStackTrace();
            return null;
        }
    }

    public NotiEntity getNotiById(String id) {
        int intId = Integer.parseInt(id);
        Future<NotiEntity> future = executor.submit(() -> notiDao.getById(intId));

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public void addNoti(Context context, NotiEntity notiEntity) {
        executor.execute(() -> {
            synchronized (Const.LOCK_OBJECT) {
                if (!notiEntity.isGroupSummary()) {
                    notiDao.insert(notiEntity);
                    myAppDao.insertApp(new MyAppEntity(context, notiEntity.getPackageName()));

                    new Handler(Looper.getMainLooper()).post(() -> {
                        Intent local = new Intent();
                        local.setAction(BROADCAST);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(local);
                    });
                }
            }
        });
    }

    public int deleteNoti(String id) {
        Future<Integer> future = executor.submit(() -> {
            synchronized (Const.LOCK_OBJECT) {
                int intId = Integer.parseInt(id);
                return notiDao.delete(intId);
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return 0;
        }
    }

    public int deleteNoti(int id) {
        Future<Integer> future = executor.submit(() -> {
            synchronized (Const.LOCK_OBJECT) {
                return notiDao.delete(id);
            }
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
