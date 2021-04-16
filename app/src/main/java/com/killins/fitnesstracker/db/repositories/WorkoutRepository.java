package com.killins.fitnesstracker.db.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.killins.fitnesstracker.db.AppDatabase;
import com.killins.fitnesstracker.db.daos.GoalDao;
import com.killins.fitnesstracker.db.daos.WorkoutDao;
import com.killins.fitnesstracker.db.entities.Goal;
import com.killins.fitnesstracker.db.entities.Workout;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class WorkoutRepository {

    private WorkoutDao workoutDao;
    private LiveData<List<Workout>> workouts;
    private static volatile WorkoutRepository instance;

    public WorkoutRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        workoutDao = db.workoutDao();
        String currentUser = application.getSharedPreferences("LOGINPREFERENCE", MODE_PRIVATE).getString("currentUser", "");
        workouts = workoutDao.loadUserWorkouts(currentUser);
    }

    public void insert(Workout workout) {
        AppDatabase.databaseWriteExecutor.execute(() -> workoutDao.insert(workout));
    }

    public void delete(Workout workout) {
        AppDatabase.databaseWriteExecutor.execute(()-> workoutDao.delete(workout));
    }

    public void update(Workout workout) {
        AppDatabase.databaseWriteExecutor.execute(()-> workoutDao.update(workout));
    }

    public LiveData<List<Workout>> loadUserWorkouts(String currentUserId) {return workouts;}
}
