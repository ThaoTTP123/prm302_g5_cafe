package com.group5.cafemngsystem.db.repository;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.group5.cafemngsystem.db.MyRoomDatabase;
import com.group5.cafemngsystem.db.dao.UserDao;
import com.group5.cafemngsystem.db.entity.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserRepository {

    private UserDao mUserDao;

    public UserRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
    }

    public LiveData<List<User>> getAll() {
        return mUserDao.getAll();
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(User user) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.insert(user);
        });
    }

    public LiveData<User> getByUsername(String username) {
        return mUserDao.getByUsername(username);
    }

    public void update(User user) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.update(user);
        });
    }
}
