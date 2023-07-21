package com.beckachu.androidfeed.data.local;

import com.beckachu.androidfeed.data.entities.NotiEntity;

import java.util.List;

/**
 * This interface defines methods to be implement by DAO
 */
public interface NotiLocalDataSource {

    List<NotiEntity> getAllNotifications();

    void addNotification(NotiEntity notification);

    void removeNotification(NotiEntity notification);

    List<NotiEntity> getAll();

    List<NotiEntity> loadAllByIds(int[] userIds);

    NotiEntity getByApp(String first);

    void insertAll(NotiEntity... users);

    void delete(NotiEntity user);
}
