package com.mako.heroslandidle.database;

import android.content.Context;
import android.database.Cursor;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mako.heroslandidle.Player;
import com.mako.heroslandidle.database.typeconverters.intArrayConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Player.class}, version = 2)
@TypeConverters({intArrayConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract PlayerDao playerDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDatabase getDatabase(final Context context){
        if (INSTANCE == null)
            synchronized (AppDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "player_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        return INSTANCE;
    }
}
