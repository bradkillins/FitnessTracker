package com.killins.fitnesstracker.db.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.killins.fitnesstracker.db.AppDatabase;
import com.killins.fitnesstracker.db.daos.GoalDao;
import com.killins.fitnesstracker.db.entities.Goal;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class GoalRepository {
    private final GoalDao goalDao;
    private LiveData<List<Goal>> userGoals;

    public GoalRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        goalDao = db.goalDao();
        String userId = application.getSharedPreferences("LOGINPREFERENCE", MODE_PRIVATE).getString("currentUser", "");
        userGoals = goalDao.loadUserGoals(userId);
    }

    public void insert(Goal goal) {
        AppDatabase.databaseWriteExecutor.execute(() -> goalDao.insert(goal));
    }

    public void delete(Goal goal) {
        AppDatabase.databaseWriteExecutor.execute(()-> goalDao.delete(goal));
    }

    public void update(Goal goal) {
        AppDatabase.databaseWriteExecutor.execute(()-> goalDao.update(goal));
    }

    public LiveData<List<Goal>> getUserGoals(){
        return userGoals;
    }
}
