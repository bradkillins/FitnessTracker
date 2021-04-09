package com.killins.fitnesstracker.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.killins.fitnesstracker.db.entities.Workout;

@Dao
public interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Workout workout);

    @Delete
    void delete(Workout workout);

    @Update
    void update(Workout workout);

    @Query("SELECT * FROM workouts WHERE userWorkoutCreatorId = userWorkoutCreatorId")
    LiveData<Workout> loadUserById(String userWorkoutCreatorId);
}
