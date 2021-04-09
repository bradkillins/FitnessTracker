package com.killins.fitnesstracker.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.killins.fitnesstracker.db.entities.User;

//UserDao is an interface; DAOs must either be interfaces or abstract classes.
//The @Dao annotation identifies it as a DAO class for Room
@Dao
public interface UserDao {
    //The @Insert annotation is a special DAO method annotation where you don't have to provide any SQL!
    //(There are also @Delete and @Update annotations for deleting and updating rows, but you are not using them in this app.)
    //The selected on conflict strategy ignores a new word if it's exactly the same as one already in the list.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM users WHERE userId = userId")
    LiveData<User> loadUserById(String userId);
    // Todo: Look into distintUntilChanged() and MediatorLiveData that is part of LiveData.
    //  May be a better way to implement this so that if this LiveData is being observed this query is not rerun whenever any row in the table is updated.
    //  But probably not an issue for this size of app.
}
