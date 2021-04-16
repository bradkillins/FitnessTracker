package com.killins.fitnesstracker.ui.login;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.killins.fitnesstracker.db.AppDatabase;
import com.killins.fitnesstracker.db.entities.Goal;
import com.killins.fitnesstracker.db.repositories.GoalRepository;
import com.killins.fitnesstracker.db.repositories.UserRepository;

import static android.content.Context.MODE_PRIVATE;
import static com.killins.fitnesstracker.db.AppDatabase.databaseWriteExecutor;

public class LoginViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final GoalRepository goalRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        goalRepository = new GoalRepository(application);
    }

    public void createUser(String username, String name, String email, String password)
    {
        userRepository.insert(username, name, email, password);
    }

    public void populateGoals (String currentUser){
        String goalName = "No goals:";
        String goalValue = "Try adding your first goal!";
        String currentUserId = currentUser;
        Goal initialGoal = new Goal(currentUserId, goalName, goalValue);
        goalRepository.populateGoals(initialGoal);
    }

    public boolean checkValidLogin(String username, String password)
    {
        //ToDo: Look into a solution other than .allowMainThreadQueries()
        return userRepository.isValidAccount(username, password);
    }
}
