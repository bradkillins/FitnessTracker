package com.killins.fitnesstracker.db.repositories;

import android.app.Application;

import com.killins.fitnesstracker.db.AppDatabase;
import com.killins.fitnesstracker.db.daos.StatsDao;
import com.killins.fitnesstracker.db.entities.Stats;

public class StatsRepository {
    private final StatsDao mStatsDao;

    StatsRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mStatsDao = db.statsDao();
    }

    public void insert(Stats stats) {
        AppDatabase.databaseWriteExecutor.execute(() -> mStatsDao.insert(stats));
    }

    public void delete(Stats stats) {
        AppDatabase.databaseWriteExecutor.execute(()-> mStatsDao.delete(stats));
    }

    public void update(Stats stats) {
        AppDatabase.databaseWriteExecutor.execute(()-> mStatsDao.update(stats));
    }
}
