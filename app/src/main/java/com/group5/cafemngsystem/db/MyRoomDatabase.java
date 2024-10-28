package com.group5.cafemngsystem.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.db.dao.DrinkDao;
import com.group5.cafemngsystem.db.dao.OrderDao;
import com.group5.cafemngsystem.db.dao.OrderDetailDao;
import com.group5.cafemngsystem.db.dao.UserDao;
import com.group5.cafemngsystem.db.entity.Drink;
import com.group5.cafemngsystem.db.entity.OrderDetail;
import com.group5.cafemngsystem.db.entity.Order;
import com.group5.cafemngsystem.db.entity.enum1.Category;
import com.group5.cafemngsystem.db.entity.enum1.Role;
import com.group5.cafemngsystem.db.entity.User;
import com.group5.cafemngsystem.db.helper.CategoryConverter;
import com.group5.cafemngsystem.db.helper.DateConverter;
import com.group5.cafemngsystem.db.helper.RoleConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Order.class, OrderDetail.class, Drink.class}, version = 1, exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract OrderDao orderDao();

    public abstract OrderDetailDao orderDetailDao();

    public abstract DrinkDao drinkDao();

    private static volatile MyRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MyRoomDatabase.class, "cafemngsystem.sqlite")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
