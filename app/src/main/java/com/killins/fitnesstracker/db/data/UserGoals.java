package com.killins.fitnesstracker.db.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.killins.fitnesstracker.db.entities.Goal;
import com.killins.fitnesstracker.db.entities.User;

import java.util.List;

public class UserGoals {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "userGoalsCreatorId"
    )
    public List<Goal> goals;
}
