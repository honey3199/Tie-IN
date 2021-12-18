package com.example.demo.repository;

import android.content.Context;
import android.telephony.SmsManager;

import com.example.demo.data.Status;
import com.example.demo.room.dao.UserDao;
import com.example.demo.room.model.User;

import java.util.Random;

public class UserRepository extends Repository {
    UserDao userDao;

    public UserRepository(Context context) {
        super(context);
        userDao = database.getUserDao();
    }

    public Status login(String phone, String password) {
        User user = userDao.getUser(phone);
        if (user != null && password.equals(user.getPassword()))
            return Status.SUCCESS;
        else
            return Status.ERROR;
    }

    public User getUser(String phone) {
        return userDao.getUser(phone);
    }

    public String register(String phone, String name, String email) {
        User user = userDao.getUser(phone);
        if (user != null)
            return Status.ERROR.getCode() + ": Number already Register!!";

        User newUser = new User(phone, name, null, email, null);
        userDao.insertUser(newUser);
        return Status.SUCCESS.getCode();
    }

    public void updatePassword(String password, String phone) {
        userDao.updatePassword(password, phone);
    }

    public void updateUserName(String name, String phone) {
        userDao.updateUserName(name, phone);
    }

    public void updateUserEmail(String email, String phone) {
        userDao.updateUserEmail(email, phone);
    }
}
