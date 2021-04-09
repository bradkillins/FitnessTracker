package com.killins.fitnesstracker.db.repositories;


import android.app.Application;
import androidx.lifecycle.LiveData;
import com.killins.fitnesstracker.db.AppDatabase;
import com.killins.fitnesstracker.db.daos.UserDao;
import com.killins.fitnesstracker.db.entities.User;

public class UserRepository {

    private final UserDao mUserDao;

    //The DAO is passed into the repository constructor as opposed to the whole database.
    // This is because you only need access to the DAO, since it contains all the read/write methods for the database.
    // There's no need to expose the entire database to the repository.
    UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mUserDao = db.userDao();
    }

    //We need to not run the insert on the main thread, so we use the ExecutorService we created in the AppDatabase to perform the insert on a background thread.
    // You must call this on a non-UI thread or your app will throw an exception.
    // Room ensures that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> mUserDao.insert(user));
    }

    public void delete(User user) {
        AppDatabase.databaseWriteExecutor.execute(()-> mUserDao.delete(user));
    }

    public void update(User user) {
        AppDatabase.databaseWriteExecutor.execute(()-> mUserDao.update(user));
    }

    public LiveData<User> loadUserById(String username){
           return mUserDao.loadUserById(username);
    }
}
