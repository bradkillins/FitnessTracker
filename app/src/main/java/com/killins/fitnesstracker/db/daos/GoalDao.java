package com.killins.fitnesstracker.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.killins.fitnesstracker.db.entities.Goal;

import java.util.List;

@Dao
public interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Goal goal);

    @Delete
    void delete(Goal goal);

    @Update
    void update(Goal goal);

    @Query("SELECT * FROM goals WHERE userGoalCreatorId = :userGoalCreatorId")
    LiveData<List<Goal>> loadUserGoals(String userGoalCreatorId);
}
