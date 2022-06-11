package com.example.cooklab;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Insert
    public void add(User user);

    @Query("SELECT * FROM users WHERE name=:name AND password=:password")
    public User login(String name, String password);

    @Query("SELECT EXISTS(SELECT * FROM users WHERE name=:name)")
    public boolean checkIfExist(String name);
}
