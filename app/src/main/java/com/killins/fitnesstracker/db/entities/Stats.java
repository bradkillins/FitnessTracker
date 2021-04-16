package com.killins.fitnesstracker.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "stats")
public class Stats {
    @PrimaryKey(autoGenerate = true)
    public long statId;
    public String userStatCreatorId;
    public String StatName;
    public long runTime;

    public long getStatId() {
        return statId;
    }
    public void setStatId(long statId) {
        this.statId = statId;
    }

    public String getUserStatCreatorId() {
        return userStatCreatorId;
    }
    public void setUserStatCreatorId(String userStatCreatorId) {
        this.userStatCreatorId = userStatCreatorId;
    }

    public String getStatName() {
        return StatName;
    }
    public void setStatName(String statName) {
        StatName = statName;
    }

    @Ignore
    public Stats(String userStatCreatorId, String statName) {
        this.userStatCreatorId = userStatCreatorId;
        this.StatName = statName;
    }
    public Stats(){

    }
}
