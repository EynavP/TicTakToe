package com.example.myapplication.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {User.class},version = 1)
public abstract class DataBase extends RoomDatabase
{
    public abstract UserDao userDao();

    public static DataBase INSTANCE;

    public static DataBase getDbInstance(Context context){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),DataBase.class,"DB_NAME")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
