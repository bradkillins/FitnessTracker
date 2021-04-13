package com.killins.fitnesstracker.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "goals", foreignKeys= {@ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userGoalCreatorId", onDelete = ForeignKey.CASCADE)}, indices = {@Index(value = "userGoalCreatorId")})
public class Goal {
    @PrimaryKey(autoGenerate = true)
    public long goalId;
    public long userGoalCreatorId;
    public String goalName;

    public long getGoalId() {
        return goalId;
    }
    public void setGoalId(long goalId) {
        this.goalId = goalId;
    }

    public long getUserGoalCreatorId() {
        return userGoalCreatorId;
    }
    public void setUserGoalCreatorId(long userGoalCreatorId) {
        this.userGoalCreatorId = userGoalCreatorId;
    }

    public String getGoalName() {
        return goalName;
    }
    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    @Ignore
    public Goal(long goalId, long userGoalCreatorId, String goalName) {
        this.goalId = goalId;
        this.userGoalCreatorId = userGoalCreatorId;
        this.goalName = goalName;
    }
    public Goal(){

    }
}
