package com.killins.fitnesstracker.ui.workout;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.killins.fitnesstracker.db.entities.Workout;
import com.killins.fitnesstracker.db.repositories.WorkoutRepository;

import static android.content.Context.MODE_PRIVATE;

public class WorkoutViewModel extends AndroidViewModel {

    private final String TYPE_RUN = "runWorkout";
    private final String TYPE_WEIGHT = "weightsWorkout";
    private final String TYPE_BODY = "body_weightWorkout";

    private WorkoutRepository workoutRepository;

    public WorkoutViewModel(@NonNull Application application) {
        super(application);
        workoutRepository = new WorkoutRepository(application);
    }

    public void insertRunWorkout(int duration, int distance){
        Workout runWorkout = new Workout(getCurrentUserId(), TYPE_RUN, distance, duration);
        workoutRepository.insert(runWorkout);
    }

    private String getCurrentUserId(){
        return getApplication().getSharedPreferences("LOGINPREFERENCE", MODE_PRIVATE).getString("currentUser", "");
    }
}