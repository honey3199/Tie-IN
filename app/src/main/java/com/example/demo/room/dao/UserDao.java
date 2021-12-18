package com.example.demo.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.demo.room.model.User;

@Dao
public interface UserDao {
    @Insert
    Long insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user WHERE phone = :phone")
    User getUser(String phone);

    @Query("UPDATE user SET name=:name where phone = :phone")
    void updateUserName(String name, String phone);

    @Query("UPDATE user SET email=:email where phone = :phone")
    void updateUserEmail(String email, String phone);

    @Query("UPDATE user SET password=:password where phone = :phone")
    void updatePassword(String password, String phone);
}
