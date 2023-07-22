package com.beckachu.androidfeed.data.local;

import com.beckachu.androidfeed.data.entities.NotiEntity;

import java.util.List;

/**
 * This interface defines methods to be implement by DAO
 * <p>
 * Common naming conventions for DataSource methods:
 * query, insert, update, and delete
 */
public interface NotiLocalDataSource {

    List<NotiEntity> getAllNotifications();

    void addNotification(NotiEntity notification);

    void deleteNotification(NotiEntity notification);

}
