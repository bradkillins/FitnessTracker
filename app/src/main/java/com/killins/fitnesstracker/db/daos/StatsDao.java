package com.killins.fitnesstracker.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.killins.fitnesstracker.db.entities.Stats;

@Dao
public interface StatsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Stats stats);

    @Delete
    void delete(Stats stats);

    @Update
    void update(Stats stats);

    @Query("SELECT * FROM stats WHERE userStatCreatorId = :userStatCreatorId")
    LiveData<Stats> loadUserStats(String userStatCreatorId);
}
