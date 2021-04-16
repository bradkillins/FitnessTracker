package com.killins.fitnesstracker.ui.goals;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.killins.fitnesstracker.db.entities.Goal;
import com.killins.fitnesstracker.db.repositories.GoalRepository;

import java.util.List;

public class GoalsViewModel extends AndroidViewModel {

    private GoalRepository goalRepository;
    private LiveData<List<Goal>> userGoals;

    public GoalsViewModel(@NonNull Application application) {
        super(application);
        goalRepository = new GoalRepository(application);
        userGoals = goalRepository.getUserGoals();
    }

    public void insert(Goal goal){
        goalRepository.insert(goal);
    }

    public void update(Goal goal){
        goalRepository.update(goal);
    }

    public void delete(Goal goal){
        goalRepository.delete(goal);
    }

    public LiveData<List<Goal>> getUserGoals() {
        return userGoals;
    }
}