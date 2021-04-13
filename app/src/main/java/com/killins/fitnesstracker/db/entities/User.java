package com.killins.fitnesstracker.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

//Each @Entity class represents a SQLite table. Annotate your class declaration to indicate that it's an entity.
// You can specify the name of the table if you want it to be different from the name of the class.
//When a class is marked as an Entity, all of its fields are persisted. If you would like to exclude some of its fields, you can mark them with Ignore.
@Entity(tableName = "users")
public class User {
    //@PrimaryKey Every entity needs a primary key.
    //You can autogenerate unique keys by annotating the primary key with @PrimaryKey(autoGenerate = true)
    //Every field that's stored in the database needs to be either public or have a "getter" method.
    //Specify the name of the column in the table if you want it to be different from the name of the member variable.
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "userId")
    private String username;
    private String name;
    private String email;
    private String password;

    //@NonNull Denotes that a parameter, field, or method return value can never be null.
    @NonNull
    public String getUsername() {
        return username;
    }
    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    @NonNull
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public User(@NotNull String username, String name, String email, String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
