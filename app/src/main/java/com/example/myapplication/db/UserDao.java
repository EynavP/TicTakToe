package com.example.myapplication.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    @Query("SELECT * FROM User ORDER BY cast(time as int) LIMIT 10")
    List<User> getTopTen();

    @Query("SELECT uid FROM User ORDER BY uid desc LIMIT 1")
    int getLastUid();

    @Insert
    void insertUser(User...users);

    @Delete
    void delete(User user);

}
