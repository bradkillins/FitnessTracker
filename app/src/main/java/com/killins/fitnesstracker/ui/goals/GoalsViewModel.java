package com.killins.fitnesstracker.ui.goals;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.killins.fitnesstracker.db.AppDatabase;
import com.killins.fitnesstracker.db.entities.Goal;
import com.killins.fitnesstracker.db.entities.User;
import com.killins.fitnesstracker.db.repositories.GoalRepository;
import com.killins.fitnesstracker.db.repositories.UserRepository;
import com.killins.fitnesstracker.ui.login.LoginViewModel;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class GoalsViewModel extends AndroidViewModel {
    private final GoalRepository goalRepository;
    private LiveData<List<Goal>> goals;

    public GoalsViewModel(@NonNull Application application){
        super(application);
        goalRepository = new GoalRepository(application);
        goals = goalRepository.loadUserGoals(application.getSharedPreferences("LOGINPREFERENCE", MODE_PRIVATE).getString("currentUser", ""));
    }
    public void insert(Goal goal){
        goalRepository.insert(goal);
    }

    public LiveData<List<Goal>> loadUserGoals(String currentUserId){return goals;}
}