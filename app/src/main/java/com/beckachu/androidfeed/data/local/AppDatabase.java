package com.beckachu.androidfeed.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.beckachu.androidfeed.data.local.entities.Notification;
import com.beckachu.androidfeed.data.local.repositories.NotificationRepo;

@Database(
        entities = {
                Notification.class,
        },
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase db = null;

    public abstract NotificationRepo notiRepo();

    public static AppDatabase appDatabase(Context context, String dbName) {
        if (db == null) {
            db = Room.databaseBuilder(context, AppDatabase.class, dbName).build();
        }
        return db;
    }
}
