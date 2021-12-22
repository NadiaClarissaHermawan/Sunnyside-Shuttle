package com.example.tubesp3b_2.model.room_database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.tubesp3b_2.model.User;
import com.example.tubesp3b_2.view.interfaces.TaskDao;


@Database(entities = {User.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    //helper for User table
    public abstract TaskDao taskDao();
}
