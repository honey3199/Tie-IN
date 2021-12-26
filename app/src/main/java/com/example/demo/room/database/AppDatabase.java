package com.example.demo.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.demo.room.dao.UserDao;
import com.example.demo.room.dao.VendorDao;
import com.example.demo.room.model.User;
import com.example.demo.room.model.Vendor;

@Database(entities = {User.class, Vendor.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    abstract public UserDao getUserDao();

    abstract public VendorDao getVendorDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "tie-in")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return appDatabase;
    }

    public static void destroyAppDatabase() {
        appDatabase = null;
    }
}
