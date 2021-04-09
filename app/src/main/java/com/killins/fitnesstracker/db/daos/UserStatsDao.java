package com.killins.fitnesstracker.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Query;
import androidx.room.Transaction;

import com.killins.fitnesstracker.db.data.UserStats;

import java.util.List;

public interface UserStatsDao {
    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    LiveData<UserStats> getUserStats(long userId);
}
