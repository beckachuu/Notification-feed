package com.beckachu.androidfeed.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
        version = 2
)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase db = null;
    private static final String DATABASE_NAME = "notifeed.db";

    public abstract NotiDao notiDao();

    public abstract MyAppDao myAppDao();

    /**
     * @param context: You should use app context (getApplicationContext()) to avoid
     *                 the activity or service be killed by the system (which leads to
     *                 unexpected issues).
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return db;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Create the new table
//            database.execSQL("DROP TABLE IF EXISTS myappentity");
            database.execSQL("CREATE TABLE IF NOT EXISTS myappentity " +
                    "(packageName TEXT PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "appName TEXT, isFavorite BOOLEAN, isReceivingNoti BOOLEAN)");

            // Copy the data
//            database.execSQL("INSERT INTO users_new (userid, username, last_update) SELECT userid, username, last_update FROM users");

            // Remove the old table
//            database.execSQL("DROP TABLE users");

            // Change the table name to the correct one
            // database.execSQL("ALTER TABLE users_new RENAME TO users");
        }
    };
}
