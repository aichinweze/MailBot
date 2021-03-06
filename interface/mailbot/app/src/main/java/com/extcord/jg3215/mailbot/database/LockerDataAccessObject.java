package com.extcord.jg3215.mailbot.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * NAME:        LockerItem.java
 * PURPOSE:     This script defines the functions that can be applied to the database.
 *
 * AUTHORS:     Ifeanyi Chinweze, Javi Geis
 * NOTES:
 * REVISION:    13/12/2018
 */

@Dao
public interface LockerDataAccessObject {
    // This annotation tells the room that the function allows you to add entries to the database
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    public void addUser(LockerItem lockerItem);

    // select all columns from [insert tableName]
    @Query("Select * from lockers")
    public List<LockerItem> readLockerItem();

    @Query("Select * from lockers WHERE delivery_location = :deliveryLocation")
    public List<LockerItem> findLockerByLocation(String deliveryLocation);

    @Query("Select * from lockers WHERE locker_no = :lockerNo")
    public LockerItem findLockerByID(int lockerNo);

    @Delete
    public void deleteLockerItem(LockerItem lockerItem);

    // Deletes all items in the database
    @Query("DELETE FROM lockers")
    public void clearDatabase();
}
