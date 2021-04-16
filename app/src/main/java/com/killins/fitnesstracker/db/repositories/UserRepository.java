package com.killins.fitnesstracker.db.repositories;


import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.killins.fitnesstracker.db.AppDatabase;
import com.killins.fitnesstracker.db.daos.UserDao;
import com.killins.fitnesstracker.db.entities.User;

public class UserRepository {

    private UserDao userDao;
    private static volatile UserRepository instance;
    private LiveData<User> user;

    public UserRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
    }
    //We need to not run the insert on the main thread, so we use the ExecutorService we created in the AppDatabase to perform the insert on a background thread.
    // You must call this on a non-UI thread or your app will throw an exception.
    // Room ensures that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(String username, String name, String email, String password) {
        User user = new User(username, name, email, password);
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.insert(user));
    }

    public void delete(User user) {
        AppDatabase.databaseWriteExecutor.execute(()-> userDao.delete(user));
    }

    public void update(User user) {
        AppDatabase.databaseWriteExecutor.execute(()-> userDao.update(user));
    }

    public boolean isValidAccount(String username, final String password)
    {
    // TODO: Take this off the main thread.
        // This ends up executing on the main thread.
        User user = userDao.validateUser(username, password);
        return user.getPassword().equals(password);
    }

    public LiveData<User> loadUserById(String username){
           return userDao.loadUserById(username);
    }
}
