package com.group5.cafemngsystem.db.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.group5.cafemngsystem.db.MyRoomDatabase;
import com.group5.cafemngsystem.db.dao.OrderDao;
import com.group5.cafemngsystem.db.dto.OrderWithUserWithOrderDetail;
import com.group5.cafemngsystem.db.entity.Order;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class OrderRepository {
    private OrderDao mOrderDao;

    public OrderRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        mOrderDao = db.orderDao();
    }

    public LiveData<List<Order>> getAllOrders() {
        return mOrderDao.getAllOrders();
    }

    public long insert(Order order) {
        Callable<Long> insertCallable = () -> mOrderDao.insert(order);
        long rowId = -1;

        Future<Long> future = MyRoomDatabase.databaseWriteExecutor.submit(insertCallable);
        try {
            rowId = future.get();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return rowId;
    }

    public void update(Order order) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> mOrderDao.update(order));
    }

    public LiveData<List<OrderWithUserWithOrderDetail>> getOrderByUser(int userId) {
        return mOrderDao.getOrderByUser(userId);
    }
}
