package com.killins.fitnesstracker.ui.goals;

import android.content.Context;

import androidx.annotation.NonNull;
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

public class GoalsViewModel extends ViewModel {
    private final GoalRepository goalRepository;
    private final LiveData<List<Goal>> goals;

    public GoalsViewModel(Context context) {
        goalRepository = GoalRepository.getInstance(AppDatabase.getDatabase(context).goalDao());
        goals = goalRepository.loadUserGoals(context.getSharedPreferences("LOGINPREFERENCE", MODE_PRIVATE).getString("currentUser", ""));
    }
    public void insert(Goal goal){
        goalRepository.insert(goal);
    }
    LiveData<List<Goal>> loadUserGoals(String currentUserId){return goals;}

    public static class Factory implements ViewModelProvider.Factory {
        private final Context ctxt;

        Factory(Context ctxt) {
            this.ctxt=ctxt.getApplicationContext();
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

            return((T)new GoalsViewModel(ctxt));
        }
    }
}