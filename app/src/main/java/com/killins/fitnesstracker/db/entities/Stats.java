package com.killins.fitnesstracker.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "stats", foreignKeys= {@ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userStatCreatorId", onDelete = ForeignKey.CASCADE)}, indices = {@Index(value = "userStatCreatorId")})
public class Stats {
    @PrimaryKey(autoGenerate = true)
    public long statId;
    public long userStatCreatorId;
    public String StatName;

    public long getStatId() {
        return statId;
    }
    public void setStatId(long statId) {
        this.statId = statId;
    }

    public long getUserStatCreatorId() {
        return userStatCreatorId;
    }
    public void setUserStatCreatorId(long userStatCreatorId) {
        this.userStatCreatorId = userStatCreatorId;
    }

    public String getStatName() {
        return StatName;
    }
    public void setStatName(String statName) {
        StatName = statName;
    }

    @Ignore
    public Stats(long statId, long userStatCreatorId, String statName) {
        this.statId = statId;
        this.userStatCreatorId = userStatCreatorId;
        StatName = statName;
    }
    public Stats(){

    }
}
