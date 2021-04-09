package com.killins.fitnesstracker.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Query;
import androidx.room.Transaction;

import com.killins.fitnesstracker.db.data.UserWorkouts;

import java.util.List;

public interface UserWorkoutsDao {
    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    LiveData<UserWorkouts> getUsersWorkouts(long userId);
}
