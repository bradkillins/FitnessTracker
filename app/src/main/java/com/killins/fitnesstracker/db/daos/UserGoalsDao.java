package com.killins.fitnesstracker.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Query;
import androidx.room.Transaction;

import com.killins.fitnesstracker.db.data.UserGoals;


public interface UserGoalsDao {
    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    LiveData<UserGoals> getUsersGoals(long userId);
}
