package com.killins.fitnesstracker.db.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.killins.fitnesstracker.db.AppDatabase;
import com.killins.fitnesstracker.db.daos.GoalDao;
import com.killins.fitnesstracker.db.entities.Goal;

import java.util.List;

import static com.killins.fitnesstracker.db.AppDatabase.databaseWriteExecutor;

public class GoalRepository {
    private final GoalDao goalDao;
    private LiveData<List<Goal>> goals;
    private static volatile GoalRepository instance;

    GoalRepository(GoalDao goalDao) {
        this.goalDao = goalDao;
    }

    public static GoalRepository getInstance(GoalDao goalDao){
        if (instance == null) {
            instance = new GoalRepository(goalDao);
        }
        return instance;
    }

    public void insert(Goal goal) {
        AppDatabase.databaseWriteExecutor.execute(() -> goalDao.insert(goal));
    }

    public void delete(Goal goal) {
        AppDatabase.databaseWriteExecutor.execute(()-> goalDao.delete(goal));
    }

    public void update(Goal goal) {
        AppDatabase.databaseWriteExecutor.execute(()->goalDao.update(goal));
    }

    public void populateGoals (Goal initialGoal){
        AppDatabase.databaseWriteExecutor.execute(() -> goalDao.insert(initialGoal));
    }

    public LiveData<List<Goal>> loadUserGoals(String currentUserId) {return goals;}
}
