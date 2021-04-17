package com.killins.fitnesstracker.ui.workout;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.killins.fitnesstracker.db.entities.Workout;
import com.killins.fitnesstracker.db.repositories.WorkoutRepository;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class WorkoutViewModel extends AndroidViewModel {

    private final String TYPE_RUN = "runWorkout";
    private final String TYPE_WEIGHT = "weightsWorkout";
    private final String TYPE_BODY = "body_weightWorkout";

    private WorkoutRepository workoutRepository;
    private LiveData<List<Workout>> workouts;

    public LiveData<List<Workout>> loadUserWorkouts(String currentUserId){return workouts;}

    public WorkoutViewModel(@NonNull Application application) {
        super(application);
        workoutRepository = new WorkoutRepository(application);
        workouts = workoutRepository.loadUserWorkouts(application.getSharedPreferences("LOGINPREFERENCE", MODE_PRIVATE).getString("currentUser", ""));
    }

    public void insertRunWorkout(int duration, int distance){
        Workout runWorkout = new Workout(getCurrentUserId(), TYPE_RUN, distance, duration);
        workoutRepository.insert(runWorkout);
    }

    private String getCurrentUserId(){
        return getApplication().getSharedPreferences("LOGINPREFERENCE", MODE_PRIVATE).getString("currentUser", "");
    }
}