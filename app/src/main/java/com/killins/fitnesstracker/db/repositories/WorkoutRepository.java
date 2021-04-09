package com.killins.fitnesstracker.db.repositories;

import android.app.Application;

import com.killins.fitnesstracker.db.AppDatabase;
import com.killins.fitnesstracker.db.daos.WorkoutDao;
import com.killins.fitnesstracker.db.entities.Workout;

public class WorkoutRepository {

    private final WorkoutDao mWorkoutDao;

    WorkoutRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mWorkoutDao = db.workoutDao();
    }

    public void insert(Workout workout) {
        AppDatabase.databaseWriteExecutor.execute(() -> mWorkoutDao.insert(workout));
    }

    public void delete(Workout workout) {
        AppDatabase.databaseWriteExecutor.execute(()-> mWorkoutDao.delete(workout));
    }

    public void update(Workout workout) {
        AppDatabase.databaseWriteExecutor.execute(()-> mWorkoutDao.update(workout));
    }
}
