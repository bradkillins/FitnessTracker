package com.killins.fitnesstracker.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "goals")
public class Goal {
    @PrimaryKey(autoGenerate = true)
    public long goalId;
    public String userGoalCreatorId;
    public String goalName;
    public String goalValue;

    public long getGoalId() {
        return goalId;
    }
    public void setGoalId(long goalId) {
        this.goalId = goalId;
    }

    public String getUserGoalCreatorId() {
        return userGoalCreatorId;
    }
    public void setUserGoalCreatorId(String userGoalCreatorId) {
        this.userGoalCreatorId = userGoalCreatorId;
    }

    public String getGoalValue() {
        return goalValue;
    }
    public void setGoalValue(String goalValue) {
        this.goalValue = goalValue;
    }

    public String getGoalName() {
        return goalName;
    }
    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    @Ignore
    public Goal(String userGoalCreatorId, @NonNull String goalName, @NonNull String goalValue) {
        this.userGoalCreatorId = userGoalCreatorId;
        this.goalName = goalName;
        this.goalValue = goalValue;
    }

    public Goal(){

    }
}
