package com.group5.cafemngsystem.db.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.group5.cafemngsystem.db.MyRoomDatabase;
import com.group5.cafemngsystem.db.dao.OrderDetailDao;
import com.group5.cafemngsystem.db.dto.OrderDetailWithDrink;
import com.group5.cafemngsystem.db.entity.OrderDetail;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class OrderDetailRepository {
    private OrderDetailDao mOrderDetailDao;

    public OrderDetailRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        mOrderDetailDao = db.orderDetailDao();
    }

    public LiveData<List<OrderDetailWithDrink>> getAllOrderDetails() {
        return mOrderDetailDao.getAllOrderDetails();
    }

    public long insert(OrderDetail orderDetail) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> mOrderDetailDao.insert(orderDetail));
        return -1;
    }

    public void update(OrderDetail orderDetail) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> mOrderDetailDao.update(orderDetail));
    }

    public void delete(OrderDetail orderDetail) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> mOrderDetailDao.delete(orderDetail));
    }

    public LiveData<OrderDetailWithDrink> getOrderDetailById(int detailId) {
        return mOrderDetailDao.getOrderDetailById(detailId);
    }

    public LiveData<List<OrderDetailWithDrink>> getOrderDetailListByOrderId(int orderId) {
        return mOrderDetailDao.getOrderDetailListByOrderId(orderId);
    }
}
