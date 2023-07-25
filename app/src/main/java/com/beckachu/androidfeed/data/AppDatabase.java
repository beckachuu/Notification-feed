package com.beckachu.androidfeed.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.beckachu.androidfeed.data.entities.MyAppEntity;
import com.beckachu.androidfeed.data.entities.NotiEntity;
import com.beckachu.androidfeed.data.local.dao.MyAppDao;
import com.beckachu.androidfeed.data.local.dao.NotiDao;

@Database(
        entities = {
                NotiEntity.class,
                MyAppEntity.class
        },
        exportSchema = false,
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase db = null;
    private static final String DATABASE_NAME = "notifeed.db";

    public abstract NotiDao notiDao();

    public abstract MyAppDao myAppDao();

    /**
     * @param context: you should use app context (getApplicationContext()) to avoid
     *                 the activity or service be killed by the system (which leads to
     *                 unexpected issues).
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                    .build();
        }
        return db;
    }
}
