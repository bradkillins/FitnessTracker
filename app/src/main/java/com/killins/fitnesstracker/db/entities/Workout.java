package com.killins.fitnesstracker.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "workouts")
public class Workout {
    @PrimaryKey (autoGenerate = true)
    public long workoutId;
    public String userWorkoutCreatorId;
    public String workoutName;
    public String workoutType;
    public long runTime;
    public long distance;

    public long getWorkoutId() {
        return workoutId;
    }
    public void setWorkoutId(long workoutId) {
        this.workoutId = workoutId;
    }

    public String getUserWorkoutCreatorId() {
        return userWorkoutCreatorId;
    }
    public void setUserWorkoutCreatorId(String userWorkoutCreatorId) {
        this.userWorkoutCreatorId = userWorkoutCreatorId;
    }

    public String getWorkoutName() {
        return workoutName;
    }
    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutType() {
        return workoutType;
    }
    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    @Ignore
    public Workout(String userWorkoutCreatorId, String workoutName, String workoutType) {
        this.workoutId = workoutId;
        this.userWorkoutCreatorId = userWorkoutCreatorId;
        this.workoutName = workoutName;
        this.workoutType = workoutType;
    }

    public Workout() {
    }
}
