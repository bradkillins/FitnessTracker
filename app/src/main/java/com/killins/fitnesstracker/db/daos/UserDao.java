package com.killins.fitnesstracker.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.killins.fitnesstracker.db.entities.User;

import java.util.List;

//UserDao is an interface; DAOs must either be interfaces or abstract classes.
//The @Dao annotation identifies it as a DAO class for Room
@Dao
public interface UserDao {
    //The @Insert annotation is a special DAO method annotation where you don't have to provide any SQL!
    //There are also @Delete and @Update annotations for deleting and updating rows
    //The selected on conflict strategy ignores a new user if it's exactly the same as one already in the database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM users WHERE userId = :userId")
    LiveData<User> loadUserById(String userId);

    @Query("SELECT * FROM users WHERE userId = :userId AND password = :password")
    User validateUser(String userId, String password);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getUsers();
}
