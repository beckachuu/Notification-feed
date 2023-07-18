package com.beckachu.androidfeed.data.local.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.beckachu.androidfeed.data.local.entities.Notification;

import java.util.List;

@Dao
public interface NotificationRepo {
    @Query("SELECT * FROM notification")
    List<Notification> getAll();

    @Query("SELECT * FROM notification WHERE nid IN (:userIds)")
    List<Notification> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM notification WHERE packageName LIKE :first")
    Notification getByApp(String first);

    @Insert
    void insertAll(Notification... users);

    @Delete
    void delete(Notification user);
}
