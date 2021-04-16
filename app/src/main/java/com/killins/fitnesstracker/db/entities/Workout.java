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
    public String workoutType;
    public long duration;
    public int distance;
    public int sets;
    public int reps;
    public int weight;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

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

    public String getWorkoutType() {
        return workoutType;
    }
    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    //run constructor
    @Ignore
    public Workout(String userWorkoutCreatorId, String workoutType, int distance, int duration) {
        this.userWorkoutCreatorId = userWorkoutCreatorId;
        this.workoutType = workoutType;
        this.distance = distance;
        this.duration = duration;
    }

    //weights constructor
    @Ignore
    public Workout(String userWorkoutCreatorId, String workoutType, int weight, int duration, int sets, int reps) {
        this.userWorkoutCreatorId = userWorkoutCreatorId;
        this.workoutType = workoutType;
        this.weight = weight;
        this.duration = duration;
        this.sets = sets;
        this.reps = reps;
    }

    //body-weight constructor
    @Ignore
    public Workout(String userWorkoutCreatorId, String workoutType, long duration, int sets, int reps) {
        this.userWorkoutCreatorId = userWorkoutCreatorId;
        this.workoutType = workoutType;
        this.duration = duration;
        this.sets = sets;
        this.reps = reps;
    }

    public Workout() {}
}
