package com.killins.fitnesstracker.db.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.killins.fitnesstracker.db.AppDatabase;
import com.killins.fitnesstracker.db.daos.GoalDao;
import com.killins.fitnesstracker.db.daos.StatsDao;
import com.killins.fitnesstracker.db.entities.Goal;
import com.killins.fitnesstracker.db.entities.Stats;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class StatsRepository {
    private StatsDao statsDao;
    private LiveData<List<Stats>> stats;
    private static volatile StatsRepository instance;

    public StatsRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        statsDao = db.statsDao();
        String currentUser = application.getSharedPreferences("LOGINPREFERENCE", MODE_PRIVATE).getString("currentUser", "");
        stats = statsDao.loadUserStats(currentUser);
    }

    public void insert(Stats stats) {
        AppDatabase.databaseWriteExecutor.execute(() -> statsDao.insert(stats));
    }

    public void delete(Stats stats) {
        AppDatabase.databaseWriteExecutor.execute(()-> statsDao.delete(stats));
    }

    public void update(Stats stats) {
        AppDatabase.databaseWriteExecutor.execute(()-> statsDao.update(stats));
    }

    public LiveData<List<Stats>> loadUserStats(String currentUserId) {return stats;}
}
