package com.example.demo.storage;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LocalStorage {
    SharedPreferences sharedPreferences;
    private final String PHONE = "phone";
    private final String PASSWORD = "password";
    public LocalStorage(Application application) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
    }

    private void setString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void setPhone(String phone) {
        setString(PHONE, phone);
    }

    public String getPhone() {
        return getString(PHONE);
    }

    public void setPassword(String password) {
        setString(PASSWORD, password);
    }

    public String getPassword() {
        return getString(PASSWORD);
    }

}
