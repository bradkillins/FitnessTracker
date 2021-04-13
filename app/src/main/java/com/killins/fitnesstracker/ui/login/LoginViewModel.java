package com.killins.fitnesstracker.ui.login;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.killins.fitnesstracker.db.AppDatabase;
import com.killins.fitnesstracker.db.entities.User;
import com.killins.fitnesstracker.db.repositories.UserRepository;

import java.util.List;

public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository;
    //private LiveData<User> user;

    public LoginViewModel(Context context) {
        userRepository = UserRepository.getInstance(AppDatabase.getDatabase(context).userDao());
    }

    public void createUser(String username, String name, String email, String password)
    {
        userRepository.insert(username, name, email, password);
    }

    public boolean checkValidLogin(String username, String password)
    {
        //ToDo: Look into a solution other than .allowMainThreadQueries()
        //This ends up executing on the main thread which throws an exception.  Maybe use LiveData somehow.
        // One hacky solution that currently works is to use .allowMainThreadQueries()
        // AppDatabase =  Room.databaseBuilder(this, AppDatabase::class.java, "MyDatabase").allowMainThreadQueries().build()
        //user = userRepository.loadUserById(username).;

        return userRepository.isValidAccount(username, password);
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final Context ctxt;

        Factory(Context ctxt) {
            this.ctxt=ctxt.getApplicationContext();
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

            return((T)new LoginViewModel(ctxt));
        }
    }
}
