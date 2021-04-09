package com.killins.fitnesstracker.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "workouts", foreignKeys= {@ForeignKey(entity = Workout.class, parentColumns = "userId", childColumns = "userWorkoutCreatorId", onDelete = ForeignKey.CASCADE)}, indices = {@Index(value = "username")})
public class Workout {
    @PrimaryKey (autoGenerate = true)
    public long workoutId;
    public long userWorkoutCreatorId;
    public String workoutName;
    public String workoutType;

    public long getWorkoutId() {
        return workoutId;
    }
    public void setWorkoutId(long workoutId) {
        this.workoutId = workoutId;
    }

    public long getUserWorkoutCreatorId() {
        return userWorkoutCreatorId;
    }
    public void setUserWorkoutCreatorId(long userWorkoutCreatorId) {
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

    public Workout(long workoutId, long userWorkoutCreatorId, String workoutName, String workoutType) {
        this.workoutId = workoutId;
        this.userWorkoutCreatorId = userWorkoutCreatorId;
        this.workoutName = workoutName;
        this.workoutType = workoutType;
    }
}
