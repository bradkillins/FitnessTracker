package com.killins.fitnesstracker.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.killins.fitnesstracker.db.daos.GoalDao;
import com.killins.fitnesstracker.db.daos.StatsDao;
import com.killins.fitnesstracker.db.daos.UserDao;
import com.killins.fitnesstracker.db.daos.WorkoutDao;
import com.killins.fitnesstracker.db.entities.Goal;
import com.killins.fitnesstracker.db.entities.Stats;
import com.killins.fitnesstracker.db.entities.User;
import com.killins.fitnesstracker.db.entities.Workout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Workout.class, Goal.class, Stats.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    // marking the instance as volatile to ensure atomic access to the variable
    // Define a singleton, AppDatabase, to prevent having multiple instances of the database opened at the same time
    private static volatile AppDatabase INSTANCE;
    // The ExecutorService with a fixed thread pool will be used to run database operations asynchronously on a background thread.
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static final String DATABASE_NAME = "fitness-tracker-db";

    //The database exposes DAOs through an abstract "getter" method for each @Dao.
    public abstract UserDao userDao();

    public abstract WorkoutDao workoutDao();

    public abstract GoalDao goalDao();

    public abstract StatsDao statsDao();

    // getDatabase returns a singleton.
    // It'll create the database the first time it's accessed,
    // using Room's database builder to create a RoomDatabase object in the application context from the AppDatabase class and names it "fitness-track-db".
    public static synchronized AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
            //If we want to prepopulate the database we can insert .createFromAsset() or .createFromFile() here
        }
        return INSTANCE;
    }

//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//        }
//    };
}

