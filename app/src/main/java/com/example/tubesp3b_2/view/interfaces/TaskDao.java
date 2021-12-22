package com.example.tubesp3b_2.view.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tubesp3b_2.model.User;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM user")
    List<User> getUser();

    @Query("DELETE FROM user")
    void delete();

    @Insert
    void insert(User user);

    @Update
    void update(User user);
}
