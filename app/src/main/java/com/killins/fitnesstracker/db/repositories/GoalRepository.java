package com.killins.fitnesstracker.db.repositories;

import android.app.Application;

import com.killins.fitnesstracker.db.AppDatabase;
import com.killins.fitnesstracker.db.daos.GoalDao;
import com.killins.fitnesstracker.db.entities.Goal;

public class GoalRepository {
    private final GoalDao mGoalDao;

    GoalRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mGoalDao = db.goalDao();
    }

    public void insert(Goal goal) {
        AppDatabase.databaseWriteExecutor.execute(() -> mGoalDao.insert(goal));
    }

    public void delete(Goal goal) {
        AppDatabase.databaseWriteExecutor.execute(()-> mGoalDao.delete(goal));
    }

    public void update(Goal goal) {
        AppDatabase.databaseWriteExecutor.execute(()->mGoalDao.update(goal));
    }
}
