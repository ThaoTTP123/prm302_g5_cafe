package com.group5.cafemngsystem.db.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.group5.cafemngsystem.db.MyRoomDatabase;
import com.group5.cafemngsystem.db.dao.DrinkDao;
import com.group5.cafemngsystem.db.entity.Drink;

import java.util.List;

public class DrinkRepository {
    private DrinkDao mDrinkDao;

    public DrinkRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        mDrinkDao = db.drinkDao();
    }

    public LiveData<List<Drink>> getAllDrinks() {
        return mDrinkDao.getAllDrinks();
    }

    public void insert(Drink drink) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            mDrinkDao.insert(drink);
        });
    }

    public LiveData<Drink> getDrinkById(int drinkId) {
        return mDrinkDao.getDrinkById(drinkId);
    }

    public void delete(Drink drink) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            mDrinkDao.delete(drink);
        });
    }

    public void update(Drink drink) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            mDrinkDao.update(drink);
        });
    }
}
