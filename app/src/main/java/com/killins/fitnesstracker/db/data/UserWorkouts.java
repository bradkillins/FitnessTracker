package com.killins.fitnesstracker.db.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.killins.fitnesstracker.db.entities.User;
import com.killins.fitnesstracker.db.entities.Workout;

import java.util.List;

public class UserWorkouts {
    @Embedded public User user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "userWorkoutCreatorId"
    )
    public List<Workout> workouts;
}
