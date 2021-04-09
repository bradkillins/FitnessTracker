package com.killins.fitnesstracker.db.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.killins.fitnesstracker.db.entities.Stats;
import com.killins.fitnesstracker.db.entities.User;

import java.util.List;

public class UserStats {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "userStatsCreatorId"
    )
    public List<Stats> stats;
}
